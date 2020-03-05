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
        adapter = new ChampionAdapter(this, champions);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ChampionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos, ImageView imageView) {
                if(champions.get(pos).isChampion ==false){
                    Intent intent1 = new Intent(getApplicationContext(), SelectChampionActivity.class);
                    intent1.putExtra("where", 0);
                    intent1.putExtra("champions", champions);
                    startActivityForResult(intent1, 0);
                } else{
                    if(adapter.getIsClicked(pos) == false){
                        if(adapter.getmOnlyItemPosition() == -1){
                            adapter.setIsClicked(pos, true);
                            adapter.mOnlyItemPosition = pos;
                            adapter.notifyDataSetChanged();
                        }else{
                            Champion champion = Champion.getChampion(champions.get(pos).name);
                            champions.set(pos, Champion.getChampion(champions.get(adapter.getmOnlyItemPosition()).name));
                            champions.set(adapter.getmOnlyItemPosition(), Champion.getChampion(champion.name));
                            adapter.setIsClicked(adapter.mOnlyItemPosition, false);
                            adapter.mOnlyItemPosition = -1;
                            adapter.notifyDataSetChanged();
                        }
                    }else{
                        adapter.setIsClicked(pos, false);
                        adapter.mOnlyItemPosition = -1;
                        adapter.notifyDataSetChanged();
                    }

                }

            }
        });

        adapter.setOnLongClickListener(new ChampionAdapter.OnLongClickListener() {
            @Override
            public void onLongClick(View view, int pos) {
                if(champions.get(pos).isChampion ==false){
                    return;
                }
                selectIndex = pos;
                CustomDialog customDialog = new CustomDialog(PopupMakePlayerActivity.this);
                customDialog.callFunction("삭제하시겠습니까?");
                customDialog.setOnOkClickListener(new CustomDialog.OnOkClickListener() {
                    @Override
                    public void onClick(View v) {
                        champions.remove(selectIndex);
                        adapter.mIsClicked.remove(selectIndex);
                        adapter.mIsPicked.remove(selectIndex);
                        adapter.notifyItemRemoved(selectIndex);
                    }
                });
            }
        });

        imageView_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText().length() < 2){
                    ApplicationClass.showToast(getApplicationContext(), "이름을 2글자 이상 입력해주세요.");
                    return;
                }
                for(int i = 0; i < ApplicationClass.players.size(); i++){
                    if(editText.getText().toString().equals(ApplicationClass.players.get(i).name)){
                        ApplicationClass.showToast(getApplicationContext(), "중복된 이름입니다.");
                        return;
                    }
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
                System.out.println("b");
                if(championIndex == -1){
                    return;
                }
                champions.add(champions.size()-1, Champion.getChampion(championIndex));
                System.out.println("c");
                adapter.mIsClicked.add(false);
                adapter.mIsPicked.add(false);
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
