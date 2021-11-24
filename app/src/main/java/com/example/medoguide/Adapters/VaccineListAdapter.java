package com.example.medoguide.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medoguide.Model.VaccineListData;
import com.example.medoguide.R;

import java.util.ArrayList;

public class VaccineListAdapter extends RecyclerView.Adapter<VaccineListAdapter.VaxViewHolder> {
    private final VaccineListData[] listData;
    private Context mContext;

    public VaccineListAdapter(Context mContext, ArrayList<VaccineListData> listData) {
        //this.mContext = mContext;
        this.listData = listData.toArray(new VaccineListData[0]);
    }

    @NonNull
    @Override
    public VaxViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.vaccine_slot_list_layout, parent, false);
        return new VaxViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull VaxViewHolder holder, int position) {
        final VaccineListData myListData = listData[position];
        holder.center.setText(myListData.getCentreName());
        holder.cent_add.setText(myListData.getCentreAddress());
        holder.minAge.setText(myListData.getMin_age_limit());
        holder.vacType.setText(myListData.getVaccine_type());
        holder.fee.setText(myListData.getFee_type());
        holder.firstDose.setText(myListData.getDose1());
        holder.secondDose.setText(myListData.getDose2());
        if (myListData.getDose1().equals("0")) {
            holder.firstDose.setBackgroundColor(0xBEFF0A0A);
        } else {
            holder.firstDose.setBackgroundColor(0xCE59F605);
            holder.firstDose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://selfregistration.cowin.gov.in/"));
                    mContext.startActivity(browserIntent);
                }
            });
        }
        if (listData[position].getDose2().equals("0")) {
            holder.secondDose.setBackgroundColor(0xBEFF0A0A);
        } else {
            holder.secondDose.setBackgroundColor(0xCE59F605);
            holder.secondDose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://selfregistration.cowin.gov.in/"));
                    mContext.startActivity(browserIntent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listData.length;
    }

    public static class VaxViewHolder extends RecyclerView.ViewHolder {
        public TextView center;
        public TextView cent_add;
        public TextView minAge;
        public TextView vacType;
        public TextView fee;
        public TextView firstDose;
        public TextView secondDose;

        public VaxViewHolder(View itemView) {
            super(itemView);
            this.center = itemView.findViewById(R.id.center_name);
            this.cent_add = itemView.findViewById(R.id.center_address);
            this.minAge = itemView.findViewById(R.id.min_age_limit);
            this.vacType = itemView.findViewById(R.id.vaccine_type);
            this.fee = itemView.findViewById(R.id.fee_type);
            this.firstDose = itemView.findViewById(R.id.dose1);
            this.secondDose = itemView.findViewById(R.id.dose2);

        }
    }
}

