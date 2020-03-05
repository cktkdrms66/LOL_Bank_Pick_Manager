package com.example.lol_ban_pick_manager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlayerDetailActivity extends Activity {

    ImageView imageView_tear;
    TextView textView_tear;
    TextView textView_name;
    RecyclerView recyclerView;
    Team.Player player;
    Team team;
    ImageView[] imageViews = new ImageView[3];
    TextView[] textViews = new TextView[3];

    ChampionAdapter adapter;
    ArrayList<Champion> arrayList;
    static int selectIndex;
    static int selectSwitch = 0;
    static int changeIndex = 0;
    static Context context;

    boolean isChange = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_detail);

        context = this;

        imageView_tear = findViewById(R.id.player_detail_back);
        textView_tear = findViewById(R.id.player_detail_tear);
        textView_name = findViewById(R.id.player_detail_name);
        recyclerView = findViewById(R.id.player_detail_recycler);
        imageViews[0] = findViewById(R.id.cardView_champion_image0);
        imageViews[1] = findViewById(R.id.cardView_champion_image1);
        imageViews[2] = findViewById(R.id.cardView_champion_image2);
        textViews[0] = findViewById(R.id.cardView_champion_name0);
        textViews[1] = findViewById(R.id.cardView_champion_name1);
        textViews[2] = findViewById(R.id.cardView_champion_name2);

        final Intent intent = getIntent();
        final int playerIndex = intent.getExtras().getInt("playerIndex");
        if(intent.getExtras().getInt("where") == 0){
            player = ApplicationClass.players.get(playerIndex);
        }else{
            team = ApplicationClass.teams.get(intent.getExtras().getInt("teamIndex"));
            player = team.players[playerIndex];
        }
        imageView_tear.setColorFilter(Team.tear_color(player.tear), PorterDuff.Mode.SRC_IN);
        textView_tear.setText(player.tear);
        textView_name.setText(player.name);



        int size = (player.most.size() > 3 ? 3 : player.most.size());
        for(int i = 0; i < size; i++){
            imageViews[i].setImageResource(player.most.get(i).image);
            textViews[i].setText(player.most.get(i).name);
        }

        imageView_tear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialog customDialog = new CustomDialog(PlayerDetailActivity.this);
                customDialog.callFunction(1);
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));

        arrayList = new ArrayList<>();
        for(int i = 0; i < player.most.size(); i++){
            arrayList.add(player.most.get(i));
        }
        arrayList.add(Champion.makePlus());

        adapter = new ChampionAdapter(this, arrayList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ChampionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos, ImageView imageView) {
                if(arrayList.get(pos).isChampion ==false){
                    Intent intent1 = new Intent(getApplicationContext(), SelectChampionActivity.class);
                    intent1.putExtra("where", 1);
                    int[] championIndexes = new int[arrayList.size()-1];
                    for(int i = 0; i < championIndexes.length; i++){
                        championIndexes[i] = arrayList.get(i).id;
                    }
                    intent1.putExtra("championIndexes", championIndexes);
                    startActivityForResult(intent1, 0);
                } else{
                    if(adapter.getIsClicked(pos) == false){
                        if(adapter.getmOnlyItemPosition() == -1){
                            adapter.setIsClicked(pos, true);
                            adapter.mOnlyItemPosition = pos;
                            adapter.notifyDataSetChanged();
                        }else{
                            Champion champion = Champion.getChampion(player.most.get(pos).name);
                            player.most.set(pos, Champion.getChampion(player.most.get(adapter.getmOnlyItemPosition()).name));
                            arrayList.set(pos, Champion.getChampion(player.most.get(adapter.getmOnlyItemPosition()).name));
                            player.most.set(adapter.getmOnlyItemPosition(), champion);
                            arrayList.set(adapter.getmOnlyItemPosition(), Champion.getChampion(champion.name));
                            int size = player.most.size() > 3 ? 3 : player.most.size();
                            for(int i = 0; i < size; i++){
                                imageViews[i].setImageResource(player.most.get(i).image);
                                textViews[i].setText(player.most.get(i).name);
                            }
                            for(int i = size; i < 3; i++){
                                imageViews[i].setImageResource(R.drawable.randomchampion);
                                textViews[i].setText("");
                            }
                            isChange = true;
                            ApplicationClass.saveRePlayer(player);

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
                if(arrayList.get(pos).isChampion ==false){
                    return;
                }
                selectIndex = pos;
                CustomDialog customDialog = new CustomDialog(PlayerDetailActivity.this);
                customDialog.callFunction("삭제하시겠습니까?");
                customDialog.setOnOkClickListener(new CustomDialog.OnOkClickListener() {
                    @Override
                    public void onClick(View v) {
                        arrayList.remove(selectIndex);
                        adapter.mIsClicked.remove(selectIndex);
                        adapter.mIsPicked.remove(selectIndex);
                        player.most.remove(selectIndex);
                        if (selectIndex < 3) {// 0 1 2
                            int size = player.most.size() > 3 ? 3 : player.most.size();
                            for(int i = 0; i < size; i++){
                                imageViews[i].setImageResource(player.most.get(i).image);
                                textViews[i].setText(player.most.get(i).name);
                            }
                            for(int i = player.most.size(); i < 3; i++){
                                imageViews[i].setImageResource(R.drawable.randomchampion);
                                textViews[i].setText("");
                            }
                        }
                        adapter.notifyItemRemoved(selectIndex);
                        isChange = true;
                        ApplicationClass.saveRePlayer(player);
                    }
                });

            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("isChange", isChange);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0){
            if(resultCode == RESULT_OK){
                int championIndex = data.getExtras().getInt("championIndex");
                if(championIndex == -1){
                    return;
                }

                Champion champion = Champion.getChampion(championIndex);
                arrayList.add(arrayList.size()-1,champion);
                player.most.add(champion);
                adapter.mIsClicked.add(false);
                adapter.mIsPicked.add(false);

                adapter.notifyItemInserted(arrayList.size()-2);
                isChange = true;
                int size = player.most.size() > 3 ? 3 : player.most.size();
                for(int i = 0; i < size; i++){
                    imageViews[i].setImageResource(player.most.get(i).image);
                    textViews[i].setText(player.most.get(i).name);
                }
                ApplicationClass.saveRePlayer(player);

            }
        }
    }
}
