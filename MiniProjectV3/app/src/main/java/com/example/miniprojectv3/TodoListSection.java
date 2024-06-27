package com.example.miniprojectv3;

import android.annotation.SuppressLint;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class TodoListSection extends Fragment {

    RecyclerView recyclerViewTodo;
    FloatingActionButton fabAddTodo;

    DBHelper dbHelper;
    ArrayList<String> todo_id, todo_title, todo_desc, due_date;

    TodoAdapter todoAdapter;

    @SuppressLint("ResourceType")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.todo_list_section, container, false);
        recyclerViewTodo = rootView.findViewById(R.id.recyclerViewTodo);
//        setRetainInstance(true);
        recyclerViewTodo.setLayoutManager(new LinearLayoutManager(getActivity()));


        fabAddTodo = rootView.findViewById(R.id.fabAddTodo);

        fabAddTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddTodo.class);
                startActivity(intent);
            }
        });

        dbHelper = new DBHelper(getActivity());
        todo_id = new ArrayList<>();
        todo_title = new ArrayList<>();
        todo_desc = new ArrayList<>();
        due_date = new ArrayList<>();

        storeTodoDataInArray();

        todoAdapter = new TodoAdapter(getActivity(), getContext(), todo_id, todo_title, todo_desc, due_date);
        recyclerViewTodo.setAdapter(todoAdapter);

        return rootView;
        
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 0) {
//
//        }
//    }

    public void storeTodoDataInArray(){
        Cursor cursor = dbHelper.readTodoData();
        if(cursor.getCount() == 0) {
            Toast.makeText(getContext(), "NO DATA IN TODO LIST", Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()) {
                todo_id.add(cursor.getString(0));
                todo_title.add(cursor.getString(1));
                todo_desc.add(cursor.getString(2));
                due_date.add(cursor.getString(3));
            }
        }
    }
}
