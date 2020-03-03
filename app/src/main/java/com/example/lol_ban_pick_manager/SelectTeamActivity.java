package com.example.lol_ban_pick_manager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SelectTeamActivity extends Activity {

    private TextView textView;
    private RecyclerView recyclerView;
    private ImageView imageView_search;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_team);
        
        textView = findViewById(R.id.team_textView_title);
        imageView_search = findViewById(R.id.team_imageView_search);
        recyclerView = findViewById(R.id.team_recyclerView);


        final boolean isOurTeam = getIntent().getExtras().getBoolean("isOurTeam");
        if(isOurTeam){
            textView.setText("아군팀");
        }else{
            textView.setText("상대팀");
        }
        imageView_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo
            }
        });


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        TeamAdapter adapter = new TeamAdapter(ApplicationClass.teams);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new TeamAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Intent intent = getIntent();
                if(ApplicationClass.teams.get(pos).type == 0){
                    //todo
                }else{
                    int teamIndex = pos;
                    Bitmap teamLogo = ApplicationClass.teams.get(pos).logo;
                    String teamName = ApplicationClass.teams.get(pos).name;
                    PopupMakeMatchActivity popupMakeMatchActivityContext = (PopupMakeMatchActivity)PopupMakeMatchActivity.context;
                    if(isOurTeam){
                        popupMakeMatchActivityContext.imageView_team0_logo
                                .setImageBitmap(teamLogo);
                        popupMakeMatchActivityContext.textView_team0_name
                                .setText(teamName);
                    }else{
                        popupMakeMatchActivityContext.imageView_team1_logo
                                .setImageBitmap(teamLogo);
                        popupMakeMatchActivityContext.textView_team1_name
                                .setText(teamName);
                    }
                    intent.putExtra("teamIndex", teamIndex);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
