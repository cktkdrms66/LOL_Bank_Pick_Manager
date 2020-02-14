package com.example.lol_ban_pick_manager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PopupTreeActivity extends Activity {

    Button button_ok;
    Button button_cancel;
    Button button_remove;
    RecyclerView recyclerView;

    boolean isPicked = false;
    int index;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_tree_select);

        button_ok = findViewById(R.id.dialog_tree_move);
        button_cancel = findViewById(R.id.dialog_tree_cancel);
        button_remove = findViewById(R.id.dialog_tree_remove);
        recyclerView = findViewById(R.id.dialog_tree_recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        final Intent intent = getIntent();
        ArrayList<Champion> champions = (ArrayList<Champion>)intent.getSerializableExtra("champions");
        champions.add(Champion.makePlus());
        final ChampionAdapter adapter = new ChampionAdapter(champions);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ChampionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos, ImageView imageView) {
                if(adapter.getIsClicked(pos) == false){
                    imageView.setColorFilter(Color.parseColor("#696969"), PorterDuff.Mode.MULTIPLY);
                    adapter.setIsClicked(pos, true);
                    adapter.setOnlyClick(pos, true);
                    if(adapter.getChampion(pos).isChampion){
                        index = pos;
                        isPicked = true;
                        button_ok.setTextColor(0xFF000000);
                        button_remove.setTextColor(0xFF000000);
                    }else{
                        if(adapter.getItemCount() == ApplicationClass.maxNodeNum + 1){
                            ApplicationClass.showToast(getApplicationContext(), "더 이상 추가할 수 없습니다.");
                        }else{
                            intent.putExtra("type", "plus");
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }
                }
            }
        });

        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPicked == false){
                    return;
                }
                intent.putExtra("type", 0);
                intent.putExtra("index", index);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        button_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPicked == false){
                    return;
                }
                CustomDialog customDialog = new CustomDialog(getApplicationContext());
                customDialog.callFunction("이 노드 이후의 밴픽 내용은 모두 삭제됩니다. 정말로 삭제하시겠습니까?");
                customDialog.setOnOkClickListener(new CustomDialog.OnOkClickListener() {
                    @Override
                    public void onClick(View v) {
                        ApplicationClass.showToast(getApplicationContext(),"삭제 완료");
                        intent.putExtra("type", 1);
                        intent.putExtra("index", index);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });

            }
        });
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("type", 2);
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
