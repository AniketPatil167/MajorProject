package com.example.miniprojectv3;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList todo_id, todo_title, todo_desc, due_date, todo_time;
    String todoTitle;

    public TodoAdapter(Activity activity, Context context, ArrayList todo_id, ArrayList todo_title, ArrayList todo_desc, ArrayList due_date) {
        this.activity = activity;
        this.context = context;
        this.todo_id = todo_id;
        this.todo_title = todo_title;
        this.todo_desc = todo_desc;
        this.due_date = due_date;
    }

    @NonNull
    @Override
    public TodoAdapter.TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.todo_recyclerview_layout, parent, false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoAdapter.TodoViewHolder holder, int position) {

        holder.todoTitle.setText(String.valueOf(todo_title.get(position)));
        todoTitle = String.valueOf(todo_title.get(position));
        holder.todoDesc.setText(String.valueOf(todo_desc.get(position)));
        String due = "Due: " + due_date.get(position);
        holder.dueDate.setText(due);
        holder.ivCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                holder.todoTitle.setPaintFlags(holder.todoTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//                holder.todoDesc.setPaintFlags(holder.todoDesc.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//                String completedTask = "Task Completed";
//                holder.dueDate.setText(completedTask);
                confirmDialog();
            }
        });


        holder.todo_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateTodo.class);
                intent.putExtra("id", String.valueOf(todo_id.get(position)));
                intent.putExtra("title", String.valueOf(todo_title.get(position)));
                intent.putExtra("desc", String.valueOf(todo_desc.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return todo_title.size();
    }

    public class TodoViewHolder extends RecyclerView.ViewHolder {

        TextView todoTitle, todoDesc, dueDate;
        ImageView ivCheck;
        LinearLayout todo_layout;

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);

            todoTitle = itemView.findViewById(R.id.tvTodoTitle);
            todoDesc = itemView.findViewById(R.id.tvTodoDesc);
            dueDate = itemView.findViewById(R.id.tvDueDate);
            ivCheck = itemView.findViewById(R.id.ivCheck);
            todo_layout = itemView.findViewById(R.id.todoListRecyclerViewLayout);
        }
    }


    public void confirmDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
        builder.setTitle("Task Completed");
        builder.setMessage("Successfully Completed task!");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DBHelper dbHelper = new DBHelper(context);
                dbHelper.deleteOneTodo(todoTitle);

                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
        });
        builder.create().show();
    }

}