package com.example.miniprojectv3;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;

public class NotificationPage extends AppCompatActivity {
    private Switch mySwitch;
    DBHelper dbHelper = new DBHelper(NotificationPage.this);
    ArrayList<String> contacts;
    EmergencyContactsAdapter emergencyContactsAdapter;
    EditText editTextName, editTextAge, editTextAddress, editTextPhone, editTextCaretakerName, editTextCaretakerPhone, editTextRelativeName, editTextRelativePhone;
    Button buttonSave;
    Button buttonChooseImage;
    ImageView imageView;
    Uri uri;
    SharedPreferences sharedPreferences2;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notificationpage);
        createNotificationChannel();
        SharedPreferences sharedPreferences = getSharedPreferences("isChecked", 0);
        boolean value = sharedPreferences.getBoolean("isChecked", true); // retrieve the value of your key
        mySwitch = (Switch) findViewById(R.id.switch1);
        mySwitch.setChecked(sharedPreferences.getBoolean("isChecked", true));

        SharedPreferences finalSharedPreferences = sharedPreferences;
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    finalSharedPreferences.edit().putBoolean("isChecked", true).apply();
                    Calendar calendar = Calendar.getInstance();

                    calendar.set(Calendar.HOUR_OF_DAY, 9);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);


                    Intent intent = new Intent(getApplicationContext(), Notification_reciever.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_HOUR
                            , pendingIntent);

                } else {
                    finalSharedPreferences.edit().putBoolean("isChecked", false).apply();
                }
            }
        });

        imageView = findViewById(R.id.imageView);
        buttonChooseImage = findViewById(R.id.buttonChooseImage);
        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextCaretakerName = findViewById(R.id.editTextCaretakerName);
        editTextCaretakerPhone = findViewById(R.id.editTextCaretakerPhone);
        editTextRelativeName = findViewById(R.id.editTextRelativeName);
        editTextRelativePhone = findViewById(R.id.editTextRelativePhone);
        buttonSave = findViewById(R.id.buttonSave);

        sharedPreferences2 = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        String name = sharedPreferences2.getString("name", "");
        String age = sharedPreferences2.getString("age", "");
        String address = sharedPreferences2.getString("address", "");
        String phone = sharedPreferences2.getString("phone", "");
        String caretakername = sharedPreferences2.getString("caretakername", "");
        String caretakerphone = sharedPreferences2.getString("caretakerphone", "");
        String relativename = sharedPreferences2.getString("relativename", "");
        String relativephone = sharedPreferences2.getString("relativephone", "");

        editTextName.setText(name);
        editTextAge.setText(age);
        editTextAddress.setText(address);
        editTextPhone.setText(phone);
        editTextCaretakerName.setText(caretakername);
        editTextCaretakerPhone.setText(caretakerphone);
        editTextRelativeName.setText(relativename);
        editTextRelativePhone.setText(relativephone);
        buttonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                String age = editTextAge.getText().toString().trim();
                String address = editTextAddress.getText().toString().trim();
                String phone = editTextPhone.getText().toString().trim();
                String caretakername = editTextCaretakerName.getText().toString().trim();
                String caretakerphone = editTextCaretakerPhone.getText().toString().trim();
                String relativename = editTextRelativeName.getText().toString().trim();
                String relativephone = editTextRelativePhone.getText().toString().trim();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(age) || TextUtils.isEmpty(address) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(caretakername) || TextUtils.isEmpty(caretakerphone) ||
                        TextUtils.isEmpty(relativename) || TextUtils.isEmpty(relativephone)) {
                    Toast.makeText(NotificationPage.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences.Editor editor = sharedPreferences2.edit();
                    editor.putString("name", name);
                    editor.putString("age", age);
                    editor.putString("address", address);
                    editor.putString("phone", phone);
                    editor.putString("caretakername", caretakername);
                    editor.putString("caretakerphone", caretakerphone);
                    editor.putString("relativename", relativename);
                    editor.putString("relativephone", relativephone);
                    editor.apply();


                    Toast.makeText(getApplicationContext(), "Details saved", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(NotificationPage.this, EditProfilePage.class);
                    intent.putExtra("img", uri);
                    startActivity(intent);
                }
            }
        });
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "regular";
            String description = "channel for regulars";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("regular", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && data!= null) {
            uri = data.getData();
            imageView.setImageURI(uri);
        }
    }



}
