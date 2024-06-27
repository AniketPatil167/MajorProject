package com.example.miniprojectv3;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MedAdapter extends RecyclerView.Adapter<MedAdapter.MyViewHolder> {
    private Context context;
    private ArrayList medTitle, medTime;

    public MedAdapter(Context context, ArrayList medTitle, ArrayList medTime) {
        this.context = context;
        this.medTitle = medTitle;
        this.medTime = medTime;
    }

    @NonNull
    @Override
    public MedAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.medremind_recyclerview, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedAdapter.MyViewHolder holder, int position) {

        holder.tvMedTitle.setText(String.valueOf(medTitle.get(position)));
        holder.tvMedTime.setText(String.valueOf(medTime.get(position)));
        holder.deleteMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(context);
                db.deleteMed(String.valueOf(medTitle.get(position)));
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                Intent intent2 = new Intent(context, AlertReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent2, 0);

                alarmManager.cancel(pendingIntent);
                Toast.makeText(context, "ALARM CANCELLED", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return medTitle.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvMedTitle, tvMedTime;
        Button deleteMed;
        GridLayout medremind_recyclerview_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMedTitle = itemView.findViewById(R.id.tvMedName);
            tvMedTime = itemView.findViewById(R.id.tvMedTime);
            deleteMed = itemView.findViewById(R.id.deleteMed);
            medremind_recyclerview_layout = itemView.findViewById(R.id.medremind_recyclerview_layout);
        }
    }
}
