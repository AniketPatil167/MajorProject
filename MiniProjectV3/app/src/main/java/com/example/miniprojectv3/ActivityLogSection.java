package com.example.miniprojectv3;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ActivityLogSection extends Fragment {

    //COPY THE SAME CODE FOR MAKING TODO LIST AND MEDICINE FRAGMENT

//    FloatingActionButton fabAdd;
    Button addActivity;
    DBHelper dbHelper;

    ActivityAdapter activityAdapter;
    ArrayList<String> activityName, activityDate, activityTime;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.activity_log_section, container, false);
        RecyclerView recyclerView =  rootView.findViewById(R.id.recyclerView);
        FragmentActivity ac = getActivity();
        recyclerView.setLayoutManager(new LinearLayoutManager(ac));
        addActivity = rootView.findViewById(R.id.addAct);
//        fabAdd = rootView.findViewById(R.id.fabAdd);
        addActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddActivity.class);
                startActivity(intent);
            }
        });


        dbHelper = new DBHelper(getContext());
        activityName = new ArrayList<>();
        activityDate = new ArrayList<>();
        activityTime = new ArrayList<>();

        storeActivityDataInArray();

//        recyclerView.setHasFixedSize(true);
        activityAdapter = new ActivityAdapter(getContext(), activityName, activityDate, activityTime);
        recyclerView.setAdapter(activityAdapter);


        return rootView;

    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 0) {
//            recreate();
//        }
//    }

    public void storeActivityDataInArray() {
        Cursor cursor = dbHelper.getActivityData();
        if (cursor.getCount() == 0) {
            Toast.makeText(getActivity(), "No data present in the database", Toast.LENGTH_SHORT).show();
        }else {
            while(cursor.moveToNext()) {
                activityName.add(cursor.getString(0));
                activityDate.add(cursor.getString(1));
                activityTime.add(cursor.getString(2));
            }
        }
    }
}
