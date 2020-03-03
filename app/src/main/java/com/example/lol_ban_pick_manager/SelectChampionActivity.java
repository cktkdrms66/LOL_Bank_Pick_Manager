package com.example.lol_ban_pick_manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SelectChampionActivity extends Activity {
    ImageView imageView_search;
    RecyclerView recyclerView;

    Intent intent;
    Team.Player player;
    ChampionAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_champion);

        intent = getIntent();
        player = ApplicationClass.players.get(intent.getExtras().getInt("playerIndex"));
        imageView_search = findViewById(R.id.champion_imageView_search2);
        recyclerView = findViewById(R.id.champion_recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,4));
        adapter = new ChampionAdapter(ApplicationClass.champions);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ChampionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos, ImageView imageView) {
                for(int i = 0; i < player.most.size(); i++){
                    if(player.most.get(i).name.equals(Champion.getChampion(pos).name)){
                        ApplicationClass.showToast(getApplicationContext(), "모스트에 이미 해당 챔피언이 존재합니다");
                        return;
                    }
                }
                Intent intent = new Intent();
                intent.putExtra("championIndex", pos);
                setResult(RESULT_OK, intent);
                System.out.println(pos);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("championIndex", -1);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }
}
