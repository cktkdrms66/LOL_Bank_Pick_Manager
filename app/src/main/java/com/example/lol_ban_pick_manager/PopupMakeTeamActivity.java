package com.example.lol_ban_pick_manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.util.ArrayList;

public class PopupMakeTeamActivity extends Activity {

    String teamName;
    Bitmap teamLogo;
    ArrayList<Champion> most;
    Team.Player[] players;
    
    EditText editText_teamName;
    ImageView imageView_teamLogo;
    ImageView[] imageViews = new ImageView[5];
    TextView[] textViews = new TextView[5];
    RecyclerView recyclerView;
    ImageView imageView_ok;
    TextView textView_X;
    TextView textView_imageX;

    static Context context;
    int playerIndexForChange;
    int[] playerIndexes = new int[5];
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup_make_team);

        context = this;
        for(int i = 0; i < 5; i++){
            playerIndexes[i] = -1;
        }
        teamLogo = ((MainActivity)MainActivity.context).setBitmap(R.drawable.no);
        players = new Team.Player[5];
        for(int i = 0; i < 5; i++){
            players[i] = ApplicationClass.players.get(1);
        }
        editText_teamName = findViewById(R.id.make_team_editText_input);
        imageView_teamLogo = findViewById(R.id.make_team_team_logo);
        imageView_ok = findViewById(R.id.make_team_ok);
        recyclerView = findViewById(R.id.make_team_recycler);
        imageViews[0] = findViewById(R.id.make_team_player_image0);
        imageViews[1] = findViewById(R.id.make_team_player_image1);
        imageViews[2] = findViewById(R.id.make_team_player_image2);
        imageViews[3] = findViewById(R.id.make_team_player_image3);
        imageViews[4] = findViewById(R.id.make_team_player_image4);
        textViews[0] = findViewById(R.id.make_team_player_name0);
        textViews[1] = findViewById(R.id.make_team_player_name1);
        textViews[2] = findViewById(R.id.make_team_player_name2);
        textViews[3] = findViewById(R.id.make_team_player_name3);
        textViews[4] = findViewById(R.id.make_team_player_name4);
        textView_X = findViewById(R.id.make_team_textView_X);
        textView_imageX = findViewById(R.id.make_team_imageX);


        imageView_teamLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 0);
            }
        });

        textView_imageX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teamLogo = ((MainActivity)MainActivity.context).setBitmap(R.drawable.no);
                imageView_teamLogo.setImageBitmap(teamLogo);
            }
        });


        textView_X.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.putExtra("isMake", 0);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        imageView_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teamName = editText_teamName.getText().toString();
                if(teamName.length() < 2){
                    ApplicationClass.showToast(getApplicationContext(), "이름을 2글자 이상으로 설정해주세요.");
                    return;
                }else if(teamName.length() > 10){
                    ApplicationClass.showToast(getApplicationContext(), "이름을 10글자 이하로 설정해주세요.");
                    return;
                }
                for(int i = 1; i < ApplicationClass.teams.size(); i++){
                    if(ApplicationClass.teams.get(i).name.equals(teamName)){
                        ApplicationClass.showToast(getApplicationContext(), "중복된 이름입니다.");
                        return;
                    }
                }
                CustomDialog customDialog = new CustomDialog(PopupMakeTeamActivity.this);
                customDialog.callFunction("이대로 팀을 만드시겠습니까?");
                customDialog.setOnOkClickListener(new CustomDialog.OnOkClickListener() {
                    @Override
                    public void onClick(View v) {
                        for(int i = 0; i < 5; i++){
                            if(playerIndexes[i] == -1){
                                players[i] = ApplicationClass.players.get(1);
                            }else{
                                players[i] = ApplicationClass.players.get(playerIndexes[i]);
                            }
                        }
                        most = new ArrayList<>();
                        Team.makeTeam(teamName, teamLogo, players, most);
                        Intent intent = new Intent();
                        intent.putExtra("isMake", 1);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });

            }
        });


        for(int i = 0; i < 5; i ++){
            imageViews[i].setImageResource(R.drawable.randomchampion);
            textViews[i].setText("이름없음");
            imageViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int i = 0;
                    ImageView nowView = (ImageView)view;
                    for(ImageView imageView : imageViews){
                        if(nowView == imageView ){
                            break;
                        }
                        i++;
                    }
                    Intent intent = new Intent(getApplicationContext(), SelectPlayerActivity.class);
                    playerIndexForChange = i;
                    startActivityForResult(intent, 1);
                }
            });
        }


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==0){
            if(resultCode==RESULT_OK){
                //데이터 받기
                try {
                    // 선택한 이미지에서 비트맵 생성
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    // 이미지 표시
                    imageView_teamLogo.setImageBitmap(img);
                    teamLogo = img;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(resultCode == RESULT_CANCELED){
                teamLogo = ((MainActivity)MainActivity.context).setBitmap(R.drawable.no);
            }
        }else if(requestCode == 1){
            if(resultCode == RESULT_OK){
                int playerIndex = data.getExtras().getInt("playerIndex");
                if(playerIndex == -1){
                    return;
                }else{
                    Team.Player player = ApplicationClass.players.get(playerIndex);
                    if(player.most.size() == 0){
                        imageViews[playerIndexForChange].setImageResource(R.drawable.randomchampion);
                    }else{
                        imageViews[playerIndexForChange].setImageResource(player.most.get(0).image);
                    }
                    String name = player.name;
                    if(player.name.length() > 5){
                        name = name.substring(0,4);
                        name+= "..";
                    }
                    textViews[playerIndexForChange].setText(name);

                    playerIndexes[playerIndexForChange] = playerIndex;

                }

            }
        }
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