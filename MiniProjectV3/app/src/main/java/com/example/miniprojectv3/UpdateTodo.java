package com.example.miniprojectv3;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateTodo extends AppCompatActivity {

    EditText etTodoTitleUpdate, etTodoDescUpdate;
    Button updateTodoBtn, deleteTodoBtn;
    String id, todoTitle, todoDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_todo);

        etTodoTitleUpdate = findViewById(R.id.etTodoTitleUpdate);
        etTodoDescUpdate = findViewById(R.id.etTodoDescUpdate);
        updateTodoBtn = findViewById(R.id.updateTodoBtn);
        deleteTodoBtn = findViewById(R.id.deleteTodoBtn);


        getAndSetIntentData();

        ActionBar ab = getSupportActionBar();
        if(ab != null) {

            ab.setTitle(todoTitle);

        }
        updateTodoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todoTitle = etTodoTitleUpdate.getText().toString().trim();
                todoDesc = etTodoDescUpdate.getText().toString().trim();

                DBHelper dbHelper = new DBHelper(UpdateTodo.this);
                dbHelper.updateTodo(id, todoTitle, todoDesc);

                Intent intent = new Intent(UpdateTodo.this, MainActivity.class);
                startActivity(intent);
            }
        });

        deleteTodoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });
    }

    public void getAndSetIntentData() {
        if(getIntent().hasExtra("id") && getIntent().hasExtra("title") && getIntent().hasExtra("desc")) {
            //GETTING DATA FROM INTENT
            id = getIntent().getStringExtra("id");
            todoTitle = getIntent().getStringExtra("title");
            todoDesc = getIntent().getStringExtra("desc");
            //SETTING DATA FROM INTENT TO THE EDITEXT
            etTodoTitleUpdate.setText(todoTitle);
            etTodoDescUpdate.setText(todoDesc);
        }else {
            Toast.makeText(this, "No Data For TODO", Toast.LENGTH_SHORT).show();
        }
    }

    public void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + todoTitle + "?");
        builder.setMessage("Are you sure you want to delete " + todoTitle + "?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DBHelper dbHelper = new DBHelper(UpdateTodo.this);
                dbHelper.deleteOneTodo(todoTitle);

                Intent intent = new Intent(UpdateTodo.this, MainActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}