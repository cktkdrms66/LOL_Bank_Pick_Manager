package com.example.lol_ban_pick_manager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class PopupSaveGameActivity extends Activity {

    TextView textView_X;
    ImageView imageView_ok;
    ImageView imageView_team_logo;
    TextView textView_team_name;
    ImageView[] imageViews = new ImageView[5];
    EditText editText;

    int star = 1;
    Match match;
    String gameName;
    Bitmap team0Image, team1Image;
    String team0Name, team1Name;
    int matchIndex;
    int victoryType = 2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup_save_game);

        final Intent intent = getIntent();
        matchIndex = intent.getExtras().getInt("matchIndex");
        match = ApplicationClass.matches.get(matchIndex);
        team0Name = intent.getExtras().getString("team0Name");
        team1Name = intent.getExtras().getString("team1Name");
        team0Image = ApplicationClass.StringToBitmap(match.team0.logo);
        team1Image = ApplicationClass.StringToBitmap(match.team1.logo);

        editText = findViewById(R.id.save_game_editText_input);
        textView_team_name = findViewById(R.id.save_game_team_name);
        textView_X = findViewById(R.id.save_game_textView_X);
        imageView_ok = findViewById(R.id.save_game_ok);
        imageView_team_logo = findViewById(R.id.save_game_image);
        imageViews[0] = findViewById(R.id.save_game_star0);
        imageViews[1] = findViewById(R.id.save_game_star1);
        imageViews[2] = findViewById(R.id.save_game_star2);
        imageViews[3] = findViewById(R.id.save_game_star3);
        imageViews[4] = findViewById(R.id.save_game_star4);

        editText.setText("밴픽 1");
        imageView_team_logo.setImageResource(R.drawable.no);
        imageViews[0].setImageResource(R.drawable.starlight);

        for(int i = 0; i < 5; i++){
            imageViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int i = 0;
                    ImageView nowView = (ImageView)view;
                    for(ImageView imageView : imageViews){
                        if(imageView == nowView){
                            break;
                        }
                        i++;
                    }
                    for(int j = 0; j <= i; j++){
                        imageViews[j].setImageResource(R.drawable.starlight);
                    }
                    for(int j = i+1; j < 5; j++){
                        imageViews[j].setImageResource(R.drawable.star);
                    }
                    star = i + 1;
                }
            });
        }

        imageView_team_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialog customDialog = new CustomDialog(PopupSaveGameActivity.this);
                customDialog.callFunction(team0Name, team1Name);
                customDialog.setOnOkClickListener(new CustomDialog.OnOkClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(CustomDialog.victoryType == 0){
                            imageView_team_logo.setImageBitmap(team0Image);
                            textView_team_name.setText(team0Name);
                        }else if(CustomDialog.victoryType == 1){
                            imageView_team_logo.setImageBitmap(team1Image);
                            textView_team_name.setText(team1Name);
                        }else{
                            imageView_team_logo.setImageResource(R.drawable.no);
                            textView_team_name.setText("설정 안함");
                        }
                        victoryType = CustomDialog.victoryType;
                    }
                });
            }
        });
        textView_X.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imageView_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameName = editText.getText().toString();
                if(gameName.length() < 2){
                    ApplicationClass.showToast(getApplicationContext(), "이름을 2글자 이상으로 설정해주세요.");
                    return;
                }else if(gameName.length() > 15){
                    ApplicationClass.showToast(getApplicationContext(), "이름을 15글자 이하로 설정해주세요.");
                    return;
                }
                Match match = ApplicationClass.matches.get(matchIndex);
                for(int i = 1; i < match.games.size(); i++){
                    if(match.games.get(i).name.equals(gameName)){
                        ApplicationClass.showToast(getApplicationContext(), "중복된 이름입니다.");
                        return;
                    }
                }
                Intent intent1 = new Intent();
                intent1.putExtra("star", star);
                intent1.putExtra("gameName", gameName);
                intent1.putExtra("victoryType", victoryType);
                setResult(RESULT_OK, intent1);
                finish();
            }
        });

    }
}
