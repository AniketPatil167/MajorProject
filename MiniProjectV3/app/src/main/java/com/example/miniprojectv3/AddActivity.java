package com.example.miniprojectv3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.DataInput;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    EditText etActivityName;
    Button addBtn, btnDate, btnTime;
    TextView tvDate, tvTime;
    String currentDate, currentTime;


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        currentTime = hourOfDay + " hours " + minute + " minutes";
        tvTime.setText(currentTime);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        tvDate.setText(currentDate);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etActivityName = findViewById(R.id.etActivityName);
        addBtn = findViewById(R.id.addBtn);
        btnDate = findViewById(R.id.btnDate);
        btnTime = findViewById(R.id.btnTime);
        tvDate = findViewById(R.id.tvDate);
        tvTime = findViewById(R.id.tvTime);
        String staticDate = DateFormat.getDateInstance(DateFormat.FULL).format(Calendar.getInstance().getTime());
        int staticHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int staticMin = Calendar.getInstance().get(Calendar.MINUTE);
        String staticTime = staticHour + " hours " + staticMin + " minutes";

        tvDate.setText(staticDate);
        tvTime.setText(staticTime);

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "Date Picker");
            }
        });

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "Time Picker");
            }
        });

        if (currentDate == null) {
            currentDate = staticDate;
        }

        if (currentTime == null) {
            currentTime = staticTime;
        }

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DBHelper dbHelper = new DBHelper(AddActivity.this);

                String activityName = etActivityName.getText().toString().trim();
                dbHelper.addActivity(activityName, currentDate, currentTime);

                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);


            }
        });
    }

}