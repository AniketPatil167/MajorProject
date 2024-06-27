package com.example.miniprojectv3;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MedRemindSection extends Fragment {

    FloatingActionButton btnMed;
    RecyclerView medremind_recyclerview;

    MedAdapter medAdapter;
    ArrayList<String> medTitle, medTime;

    DBHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.medremind_section, container, false);

        medremind_recyclerview = rootView.findViewById(R.id.medremind_recyclerview);
        FragmentActivity medFrag = getActivity();
        medremind_recyclerview.setLayoutManager(new GridLayoutManager(medFrag, 2));
        btnMed = rootView.findViewById(R.id.btnMed);

        btnMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddMed.class);
                startActivity(intent);
            }
        });

        dbHelper = new DBHelper(getContext());
        medTitle = new ArrayList<>();
        medTime = new ArrayList<>();

        StoreMedicineDataInArray();

        medAdapter = new MedAdapter(getContext(), medTitle, medTime);
        medremind_recyclerview.setAdapter(medAdapter);

        return rootView;
    }

    private void StoreMedicineDataInArray() {
        Cursor cursor = dbHelper.getMedData();
        if (cursor.getCount() == 0) {
            Toast.makeText(getActivity(), "No data present in the database", Toast.LENGTH_SHORT).show();
        }else {
            while(cursor.moveToNext()) {
                medTitle.add(cursor.getString(0));
                medTime.add(cursor.getString(1));
            }
        }
    }
}
