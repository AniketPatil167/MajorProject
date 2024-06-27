package com.example.miniprojectv3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class AddMed extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    EditText etMedTitle;
    Button btnMedTime, addMed;
    TextView tvMedTime;
    String medTime;

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//        medTime = hourOfDay + " hours " + minute + " minutes";
//        tvMedTime.setText(medTime);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);

        updateTimeText(c);
        startAlarm(c);
    }

    private void updateTimeText(Calendar c) {
        medTime = "Alarm set for : ";
        medTime += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());

        tvMedTime.setText(medTime);
    }

    private void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_med);

        etMedTitle = findViewById(R.id.etMedTitle);
        btnMedTime = findViewById(R.id.btnMedTime);
        addMed = findViewById(R.id.addMed);
        tvMedTime = findViewById(R.id.tvMed_Time);


        btnMedTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "Time Picker");
            }
        });

        addMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DBHelper dbHelper = new DBHelper(AddMed.this);

                String medTitle = etMedTitle.getText().toString().trim();
                dbHelper.addMedicine(medTitle, medTime);
                Intent intent = new Intent(AddMed.this, MainActivity.class);
                startActivity(intent);

            }
        });

    }


}