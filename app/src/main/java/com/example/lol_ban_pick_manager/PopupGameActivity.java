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

public class PopupGameActivity extends Activity {

    TextView textView_title;
    TextView textView_X;
    ImageView imageView_ok;
    RecyclerView recyclerView_teams;
    static Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup_game);
        context = this;

        final Intent intent = getIntent();
        final int matchIndex = intent.getExtras().getInt("matchIndex");

        textView_title = findViewById(R.id.game_textView_title);
        textView_X = findViewById(R.id.game_textView_X);
        imageView_ok = findViewById(R.id.game_imageView_ok);
        recyclerView_teams = findViewById(R.id.game_recyclerView_games);



        recyclerView_teams.setHasFixedSize(true);
        recyclerView_teams.setLayoutManager(new LinearLayoutManager(this));

        final GameAdapter adapter = new GameAdapter(ApplicationClass.matches.get(matchIndex).games);
        recyclerView_teams.setAdapter(adapter);


        textView_X.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        imageView_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int gameIndex = adapter.getmOnlyItemPosition();
                if(gameIndex == -1){
                    return;
                }
                finish();
                Intent intent2 = new Intent(getApplicationContext(), BanPickActivity.class);
                intent2.putExtra("matchIndex", matchIndex);
                intent2.putExtra("gameIndex", adapter.getmOnlyItemPosition());
                startActivity(intent);
            }
        });

        adapter.setOnItemClickListener(new GameAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v) {

                Intent intent1 = new Intent(getApplicationContext(), BanPickActivity.class);
                intent1.putExtra("matchIndex", matchIndex);
                intent1.putExtra("gameIndex", 0);
                startActivity(intent1);
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
