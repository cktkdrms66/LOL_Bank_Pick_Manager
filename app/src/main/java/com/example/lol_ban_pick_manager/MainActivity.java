package com.example.lol_ban_pick_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button buttonBanPick;
    Button buttonWithFriend;
    Button buttonTeamData;
    Button buttonBanPickData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonBanPick = findViewById(R.id.main_button_banPick);
        buttonWithFriend = findViewById(R.id.main_button_with_friend);
        buttonTeamData = findViewById(R.id.main_button_team_data);
        buttonBanPickData = findViewById(R.id.main_button_banpick_data);

        buttonBanPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PopupMakeGameActivity.class);
                startActivity(intent);
            }
        });

        buttonWithFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo
            }
        });

        buttonTeamData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo
            }
        });

        buttonBanPickData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo
            }
        });

    }
}
