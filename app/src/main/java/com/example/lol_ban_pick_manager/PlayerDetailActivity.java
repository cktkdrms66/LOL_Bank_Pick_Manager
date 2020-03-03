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
        team = ApplicationClass.teams.get(intent.getExtras().getInt("teamIndex"));
        player = team.players[intent.getExtras().getInt("playerIndex")];
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

        adapter = new ChampionAdapter(arrayList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ChampionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos, ImageView imageView) {
                if(arrayList.get(pos).isChampion ==false){
                    //todo plus
                    System.out.println("plus");
                    Intent intent1 = new Intent(getApplicationContext(), SelectChampionActivity.class);
                    intent1.putExtra("playerIndex",intent.getExtras().getInt("playerIndex"));
                    startActivityForResult(intent1, 0);
                } else{
                    if(selectSwitch == 0){
                        selectIndex = pos;
                        CustomDialog customDialog = new CustomDialog(PlayerDetailActivity.this);
                        customDialog.callFunction("삭제하시겠습니까?");
                        customDialog.setOnOkClickListener(new CustomDialog.OnOkClickListener() {
                            @Override
                            public void onClick(View v) {
                                arrayList.remove(selectIndex);
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
                    }else if(selectSwitch == 1){
                        selectSwitch = 0;
                        recyclerView.setBackgroundColor(Color.WHITE);
                        if(player.most.get(changeIndex).name.equals(arrayList.get(pos).name)){
                            return;
                        }
                        Champion champion = player.most.get(changeIndex);
                        player.most.set(changeIndex, Champion.getChampion(arrayList.get(pos).name));
                        arrayList.set(changeIndex, Champion.getChampion(arrayList.get(pos).name));
                        imageViews[changeIndex].setImageResource(player.most.get(changeIndex).image);
                        textViews[changeIndex].setText(player.most.get(changeIndex).name);
                        for(int i = 0; i < player.most.size(); i++){
                            if(changeIndex == i){
                                continue;
                            }
                            if(player.most.get(changeIndex).name.equals(player.most.get(i).name)){
                                player.most.set(i, champion);
                                arrayList.set(i, champion);
                                if(i< 3){
                                    imageViews[i].setImageResource(champion.image);
                                    textViews[i].setText(champion.name);
                                }
                                break;
                            }
                        }
                        adapter.notifyDataSetChanged();
                        isChange = true;
                        ApplicationClass.saveRePlayer(player);

                    }

                }
            }
        });


        for(int i = 0; i < 3; i++){
            imageViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(selectSwitch ==0){
                        ApplicationClass.showToast(getApplicationContext(), "챔피언을 선택해주세요.");
                        int i = 0;
                        ImageView nowView = (ImageView)view;
                        for(ImageView imageView : imageViews){
                            if(nowView == imageView){
                                break;
                            }
                            i++;
                        }
                        changeIndex = i;
                        selectSwitch = 1;
                        recyclerView.setBackgroundColor(Color.GRAY);
                    }else if(selectSwitch == 1){
                        selectSwitch = 0;
                        recyclerView.setBackgroundColor(Color.WHITE);
                    }

                }
            });
        }

        
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
