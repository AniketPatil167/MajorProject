package com.example.miniprojectv3;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.MyViewHolder> {

    private Context context;
//    private Activity activity;
    private ArrayList activityName, activityDate, activityTime;

    public ActivityAdapter(Context context, ArrayList activityName, ArrayList activityDate, ArrayList activityTime) {
//        this.activity = activity;
        this.context = context;
        this.activityName = activityName;
        this.activityDate = activityDate;
        this.activityTime = activityTime;
    }

    @NonNull
    @Override
    public ActivityAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        holder.itemView.setTag(position);

        holder.activityName.setText(String.valueOf(activityName.get(position)));
        holder.activityDate.setText(String.valueOf(activityDate.get(position)));
        holder.activityTime.setText(String.valueOf(activityTime.get(position)));
    }

    @Override
    public int getItemCount() {
        return activityName.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView activityName, activityDate, activityTime;
        LinearLayout recyclerview_layout;

//        @SuppressLint("ResourceType")
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            activityName = itemView.findViewById(R.id.tvActivityName);
            activityDate = itemView.findViewById(R.id.tvActivityDate);
            activityTime = itemView.findViewById(R.id.tvActivityTime);
            recyclerview_layout = itemView.findViewById(R.id.recyclerViewLayout);
        }
    }
}
