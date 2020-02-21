package com.example.lol_ban_pick_manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PopupTeamActivity extends Activity {

    TextView textView_title;
    TextView textView_X;
    ImageView imageView_ok;
    RecyclerView recyclerView_teams;
    static Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup_team);
        context = this;

        final Intent intent = getIntent();
        final boolean isOurTeam = intent.getExtras().getBoolean("isOurTeam");


        textView_title = findViewById(R.id.game_textView_title);
        textView_X = findViewById(R.id.game_textView_X);
        imageView_ok = findViewById(R.id.team_imageView_ok);
        recyclerView_teams = findViewById(R.id.game_recyclerView_games);


        if(isOurTeam){
            textView_title.setText("아군팀");
        }else{
            textView_title.setText("상대팀");
        }

        recyclerView_teams.setHasFixedSize(true);
        recyclerView_teams.setLayoutManager(new LinearLayoutManager(this));

        final TeamAdapter adapter = new TeamAdapter(ApplicationClass.teams);
        recyclerView_teams.setAdapter(adapter);


        textView_X.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("teamIndex", 1);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        imageView_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int teamIndex = adapter.getmOnlyItemPosition();
                if(teamIndex == -1){
                    return;
                }
                int teamLogo = adapter.getTeamLogo();
                String teamName = adapter.getTeamName();
                PopupMakeGameActivity popupMakeGameActivityContext = (PopupMakeGameActivity)PopupMakeGameActivity.context;
                if(isOurTeam){

                    popupMakeGameActivityContext.imageView_team0_logo
                            .setImageResource(teamLogo);
                    popupMakeGameActivityContext.textView_team0_name
                            .setText(teamName);
                }else{
                    popupMakeGameActivityContext.imageView_team1_logo
                            .setImageResource(teamLogo);
                    popupMakeGameActivityContext.textView_team1_name
                            .setText(teamName);
                }
                intent.putExtra("teamIndex", teamIndex);
                setResult(RESULT_OK, intent);
                finish();
            }
        });




    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()== MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }

}
