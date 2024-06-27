package com.example.miniprojectv3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class AddTodo extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    EditText etTodoTitle, etTodoDesc;
    TextView tvSelectDue;
    Button addTodoBtn, btnDue;
    String currentDate;
    Calendar calendar;




    private void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        tvSelectDue.setText(currentDate);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        etTodoTitle = findViewById(R.id.etTodoTitle);
        etTodoDesc = findViewById(R.id.etTodoDesc);
        addTodoBtn = findViewById(R.id.addTodoBtn);
        tvSelectDue = findViewById(R.id.tvSelectDue);
        btnDue = findViewById(R.id.btnDue);
        String staticDate = DateFormat.getDateInstance(DateFormat.FULL).format(Calendar.getInstance().getTime());
        tvSelectDue.setText(staticDate);
//        currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());


        btnDue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "Date Picker");
            }
        });

        if (currentDate == null) {
            currentDate = staticDate;
        }

        if (currentDate.equals(staticDate)) {
            currentDate = "Today";
        }


        addTodoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbHelper = new DBHelper(AddTodo.this);

                String todoTitle = etTodoTitle.getText().toString().trim();
                String todoDesc = etTodoDesc.getText().toString().trim();


                dbHelper.addTodo(todoTitle, todoDesc, currentDate);
                Intent intent = new Intent(AddTodo.this, MainActivity.class);
                startActivity(intent);

            }

        });
    }
}