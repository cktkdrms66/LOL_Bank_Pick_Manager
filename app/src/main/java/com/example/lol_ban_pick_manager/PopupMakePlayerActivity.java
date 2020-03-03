package com.example.lol_ban_pick_manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PopupMakePlayerActivity extends Activity {

    ImageView imageView_tear;
    TextView textView_tear;
    EditText editText;
    RecyclerView recyclerView;
    TextView textView_X;
    ImageView imageView_ok;

    String tear;
    ChampionAdapter adapter;
    ArrayList<Champion> champions;

    static Context context;

    int selectIndex;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup_make_player);


        context = this;
        tear = "UN";
        imageView_tear = findViewById(R.id.make_player_back);
        textView_tear = findViewById(R.id.make_player_tear);
        editText = findViewById(R.id.make_player_editText_input);
        recyclerView = findViewById(R.id.make_player_recycler);
        textView_X = findViewById(R.id.make_player_textView_X);
        imageView_ok = findViewById(R.id.make_player_ok);

        textView_X.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imageView_tear.setColorFilter(Team.tear_color("UN"), PorterDuff.Mode.SRC_IN);
        imageView_tear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialog customDialog = new CustomDialog(PopupMakePlayerActivity.context);
                customDialog.callFunction(0);
            }
        });

        champions = new ArrayList<>();
        champions.add(Champion.makePlus());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter = new ChampionAdapter(champions);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ChampionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos, ImageView imageView) {
                if(champions.get(pos).isChampion ==false){
                    Intent intent1 = new Intent(getApplicationContext(), SelectChampionActivity.class);
                    intent1.putExtra("playerIndex", 1);
                    startActivityForResult(intent1, 0);
                } else {
                    CustomDialog customDialog = new CustomDialog(PopupMakePlayerActivity.this);
                    customDialog.callFunction("삭제하시겠습니까?");
                    customDialog.setOnOkClickListener(new CustomDialog.OnOkClickListener() {
                        @Override
                        public void onClick(View v) {
                            champions.remove(selectIndex);
                            adapter.notifyItemRemoved(selectIndex);
                        }
                    });
                }
            }
        });

        imageView_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText().length() < 2){
                    ApplicationClass.showToast(getApplicationContext(), "이름을 2글자 이상 입력해주세요.");
                    return;
                }
                CustomDialog customDialog = new CustomDialog(PopupMakePlayerActivity.this);
                customDialog.callFunction("이대로 진행하시겠습니까?");
                customDialog.setOnOkClickListener(new CustomDialog.OnOkClickListener() {
                    @Override
                    public void onClick(View v) {
                        champions.remove(champions.size()-1);
                        Team.makePlayer(editText.getText().toString(), tear, champions);
                        Intent intent = new Intent();
                        intent.putExtra("isChange", true);
                        setResult(RESULT_OK, intent);
                        finish();

                    }
                });
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0){
            if(resultCode == RESULT_OK){
                int championIndex = data.getExtras().getInt("championIndex");
                if(championIndex == -1){
                    return;
                }
                champions.add(champions.size()-1, Champion.getChampion(championIndex));
                adapter.notifyItemInserted(champions.size()-2);

            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("isChange", false);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }
}
