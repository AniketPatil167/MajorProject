package com.example.miniprojectv3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class EditProfilePage extends AppCompatActivity {
    TextView textViewName, textViewAge, textViewAddress, textViewPhone, textViewCaretakerName, textViewCaretakerPhone, textViewRelativeName, textViewRelativePhone;
    Button buttonEdit;
    ImageView receiveImage;
    Intent intent;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_page);

        receiveImage = findViewById(R.id.receiveImage);
        textViewName = findViewById(R.id.textViewName);
        textViewAge = findViewById(R.id.textViewAge);
        textViewAddress = findViewById(R.id.textViewAddress);
        textViewPhone = findViewById(R.id.textViewPhone);
        textViewCaretakerName = findViewById(R.id.textViewCaretakerName);
        textViewCaretakerPhone = findViewById(R.id.textViewCaretakerPhone);
        textViewRelativeName = findViewById(R.id.textViewRelativeName);
        textViewRelativePhone = findViewById(R.id.textViewRelativePhone);
        buttonEdit = findViewById(R.id.buttonEdit);

        intent=getIntent();
        Uri uri=(Uri) intent.getParcelableExtra("img");
        receiveImage.setImageURI(uri);

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        String name = sharedPreferences.getString("name", "");
        String age = sharedPreferences.getString("age", "");
        String address = sharedPreferences.getString("address", "");
        String phone = sharedPreferences.getString("phone", "");
        String caretakername = sharedPreferences.getString("caretakername", "");
        String caretakerphone = sharedPreferences.getString("caretakerphone", "");
        String relativename = sharedPreferences.getString("relativename", "");
        String relativephone = sharedPreferences.getString("relativephone", "");

        textViewName.setText("Name:  " + name);
        textViewAge.setText("Age:  " + age);
        textViewAddress.setText("Address:  " + address);
        textViewPhone.setText("Phone:  " + phone);
        textViewCaretakerName.setText("Caretaker's Name:  " + caretakername);
        textViewCaretakerPhone.setText("Caretaker's Phone:  " + caretakerphone);
        textViewRelativeName.setText("Relative's Name:  " + relativename);
        textViewRelativePhone.setText("Relative's Phone:  " + relativephone);

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfilePage.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}

