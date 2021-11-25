package com.example.medoguide.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medoguide.Model.HistoryData;
import com.example.medoguide.R;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private final List<HistoryData> takenList;
    private final Context mContext;

    public HistoryAdapter( Context mContext, List<HistoryData> takenList) {
        this.mContext = mContext;
        this.takenList = takenList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.history_card_layout, parent,false);
        return new HistoryViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        HistoryData data = takenList.get(position);
        holder.dateOfTaken.setText(data.getConsumeDate());
        holder.timeOfTaken.setText(data.getConsumeTime());
        holder.medName.setText(data.getMedicine());
        holder.medQuant.setText(String.format("%s %s", data.getNo_of_dose(), data.getDoseType()));

    }

    @Override
    public int getItemCount() {
        return takenList.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder{

        TextView dateOfTaken, timeOfTaken, medName, medQuant;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            dateOfTaken = itemView.findViewById(R.id.hist_med_date);
            timeOfTaken = itemView.findViewById(R.id.hist_med_time);
            medName = itemView.findViewById(R.id.hist_medicine_name);
            medQuant = itemView.findViewById(R.id.hist_dose_details);
        }
    }
}
