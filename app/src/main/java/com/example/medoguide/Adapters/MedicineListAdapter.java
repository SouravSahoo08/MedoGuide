package com.example.medoguide.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medoguide.Model.MedicineDataFB;
import com.example.medoguide.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MedicineListAdapter extends  RecyclerView.Adapter<MedicineListAdapter.ViewHolder>{

    private final Context mContext;
    private final List<MedicineDataFB> medData;

    public MedicineListAdapter(Context mContext, List<MedicineDataFB> medData) {
        this.mContext = mContext;
        this.medData = medData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.medicine_card, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       MedicineDataFB data = medData.get(position);
       holder.time.setText(data.getTime());
       holder.medicineName.setText(data.getMedicineName());
       holder.doseDetails.setText(String.format("%s %s", data.getNo_of_doses(), data.getDoseType()));

       holder.deleteMed.setOnClickListener(v->{
            new AlertDialog.Builder(mContext)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Delete Comment")
                    .setMessage("Are you sure want to delete the comment?")
                    .setNegativeButton("No", null)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FirebaseDatabase.getInstance().getReference().child("medicineList").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .child(data.getMedicineName()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(mContext, "medicine deleted", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).show();
       });
    }

    @Override
    public int getItemCount() {
        return medData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView time, medicineName, doseDetails;
        public ImageView deleteMed, takeMed;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            time = itemView.findViewById(R.id.med_time);
            medicineName = itemView.findViewById(R.id.medicine_name);
            doseDetails = itemView.findViewById(R.id.dose_details);
            deleteMed = itemView.findViewById(R.id.delete_med);
            takeMed = itemView.findViewById(R.id.take_med);

        }
    }
}
