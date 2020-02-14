package com.example.lol_ban_pick_manager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class PopupMakeGameActivity extends Activity {

    String matchName;
    int mode = 2;
    boolean isDefaultPosition = true;
    static Team team0;
    static Team team1;

    TextView textView_X;
    TextView textView_next;
    TextView textView_team0_name;
    TextView textView_team1_name;
    ImageView imageView_team0_logo;
    ImageView imageView_team1_logo;
    RadioButton radioButton_normal;
    RadioButton radioButton_rank;
    RadioButton radioButton_draft;
    EditText editText_match_name;
    ImageView imageView_change;

    static Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup_make_game);

        context = this;
        team0 = null;
        team1 = null;


        textView_X = findViewById(R.id.make_game_textView_X);
        textView_next = findViewById(R.id.make_game_textView_next);
        textView_team0_name = findViewById(R.id.make_game_textView_team0_name);
        textView_team1_name = findViewById(R.id.make_game_textView_team1_name);
        imageView_team0_logo = findViewById(R.id.make_game_team0_logo);
        imageView_team1_logo = findViewById(R.id.make_game_team1_logo);
        radioButton_draft = findViewById(R.id.make_game_radioButton_draft);
        radioButton_normal = findViewById(R.id.make_game_radioButton_normal);
        radioButton_rank = findViewById(R.id.make_game_radioButton_rank);
        editText_match_name = findViewById(R.id.make_game_editText_input);
        imageView_change = findViewById(R.id.make_game_imageView_change);

        radioButton_draft.setChecked(true);
        editText_match_name.setText("매치 " + (ApplicationClass.totalGameNum + 1));

        textView_X.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                matchName = null;
                isDefaultPosition = true;
                mode = 2;
                team0 = null;
                team1 = null;
            }
        });

        textView_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialog customDialog = new CustomDialog(PopupMakeGameActivity.this);
                customDialog.callFunction("이대로 진행하시겠습니까?");
                customDialog.setOnOkClickListener(new CustomDialog.OnOkClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), BanPickActivity.class);
                        matchName = editText_match_name.getText().toString();
                        intent.putExtra("matchName", matchName);
                        intent.putExtra("isDefault", isDefaultPosition);
                        if(radioButton_normal.isChecked()){
                            mode = 0;
                        }else if(radioButton_rank.isChecked()){
                            mode = 1;
                        }else if(radioButton_draft.isChecked()){
                            mode = 2;
                        }
                        startActivity(intent);
                        finish();
                    }
                });

            }
        });
        imageView_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int blueColor = ContextCompat.getColor(context, R.color.colorBlueTeam);
                int redColor = ContextCompat.getColor(context, R.color.colorRedTeam);
                if(textView_team0_name.getCurrentTextColor() == blueColor){
                    textView_team0_name.setTextColor(redColor);
                    textView_team1_name.setTextColor(blueColor);
                    isDefaultPosition = false;
                } else{
                    textView_team0_name.setTextColor(blueColor);
                    textView_team1_name.setTextColor(redColor);
                    isDefaultPosition = true;
                }
            }
        });

        imageView_team0_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PopupTeamActivity.class);
                intent.putExtra("isOurTeam", true);
                startActivity(intent);
            }
        });

        imageView_team1_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PopupTeamActivity.class);
                intent.putExtra("isOurTeam", false);
                startActivity(intent);
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
