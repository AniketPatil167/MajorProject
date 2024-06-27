package com.example.miniprojectv3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Switch;

import com.example.miniprojectv3.chatbot.VoiceAssistantSection;
import com.example.miniprojectv3.location.LocationModule;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private Switch mySwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mySwitch = (Switch)findViewById(R.id.switch1);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navi_bar);
        bottomNav.setOnItemSelectedListener(navListener);
        if(savedInstanceState == null) {
            bottomNav.setSelectedItemId(R.id.nav_activity);
        }

    }

    private NavigationBarView.OnItemSelectedListener navListener =
            new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                    Fragment selectedFragment = null;


                    switch (item.getItemId()) {
                        case R.id.nav_activity:
                            switchToActivityLog();
                            break;
                        case R.id.nav_todo:
                            switchToTodoSection();
                            break;
                        case R.id.nav_med:
                            switchToMedRemindSection();
                            break;
                        case R.id.nav_voice:
                            switchToVoiceAssistantSection();
                            break;
                    }
                    return true;
                }
            };

    public void switchToActivityLog() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, new ActivityLogSection()).commit();
    }

    public void switchToTodoSection() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, new TodoListSection()).commit();
    }

    public void switchToMedRemindSection() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, new MedRemindSection()).commit();
    }

    public void switchToVoiceAssistantSection() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, new VoiceAssistantSection()).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.setting){
            Intent i = new Intent(getApplicationContext(), NotificationPage.class);
            startActivity(i);
        }
        if(item.getItemId() == R.id.location) {
            Intent i = new Intent(getApplicationContext(), LocationModule.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

}



