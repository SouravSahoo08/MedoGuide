package com.example.medoguide.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medoguide.Model.TrackListData;
import com.example.medoguide.R;

import java.util.ArrayList;

public class TrackListAdapter extends RecyclerView.Adapter<TrackListAdapter.ViewHolder> {

    private final Context mContext;
    private final TrackListData[] listData;
    private Dialog dialogBox;

    public TrackListAdapter(Context mContext, ArrayList<TrackListData> listData) {
        this.mContext = mContext;
        this.listData = listData.toArray(new TrackListData[0]);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View listItem = layoutInflater.inflate(R.layout.track_record_list, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TrackListData listOfData = listData[position];
        holder.typeName.setText(listOfData.getCountryName());
        holder.stats.setText(listOfData.getNo_of_cases());
        dialogBox = new Dialog(mContext);
        dialogBox.setContentView(R.layout.custom_dialogbox);

        holder.cardView.setOnClickListener(v->{
            TextView heading = dialogBox.findViewById(R.id.typename);
            TextView population = dialogBox.findViewById(R.id.populationTxt);
            TextView cases = dialogBox.findViewById(R.id.casesText);
            TextView active = dialogBox.findViewById(R.id.activeCasesText);
            TextView recovered = dialogBox.findViewById(R.id.recoveredText);
            TextView critical = dialogBox.findViewById(R.id.criticaltext);
            TextView death = dialogBox.findViewById(R.id.deathText);
            LinearLayout affectedCountLL =dialogBox.findViewById(R.id.affect_countriesLL);
            TextView affectedCount = dialogBox.findViewById(R.id.affect_countriesText);

            heading.setText(listOfData.getCountryName());
            population.setText(listOfData.getPopulation());
            cases.setText(listOfData.getNo_of_cases());
            active.setText(listOfData.getNo_of_active_cases());
            recovered.setText(listOfData.getRecovered_cases());
            critical.setText(listOfData.getCritical_cases());
            death.setText(listOfData.getDeaths());
            if (listOfData.getCountryName().equals("World")) {
                affectedCountLL.setVisibility(View.VISIBLE);
                affectedCount.setText(listOfData.getAffected_countries());
            } else {
                affectedCountLL.setVisibility(View.GONE);
            }
            dialogBox.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialogBox.show();
        });
    }

    @Override
    public int getItemCount() {
        return listData.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CardView cardView;
        public TextView typeName;
        public TextView stats;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            typeName = itemView.findViewById(R.id.type);
            stats = itemView.findViewById(R.id.stat);
        }
    }
}
