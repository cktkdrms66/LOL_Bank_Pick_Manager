package com.example.lol_ban_pick_manager;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BanPickActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    TextView textView_team0_name;
    TextView textView_team1_name;
    ImageView[] imageView_team0_bans = new ImageView[5];
    ImageView[] imageView_team1_bans = new ImageView[5];
    TextView[] textView_team0_ban_backs = new TextView[5];
    TextView[] textView_team1_ban_backs = new TextView[5];
    ImageView[] imageView_team0_picks = new ImageView[5];
    ImageView[] imageView_team1_picks = new ImageView[5];
    TextView[] textView_team0_pick_backs = new TextView[5];
    TextView[] textView_team1_pick_backs = new TextView[5];
    TextView[] textView_team0_tears = new TextView[5];
    TextView[] textView_team1_tears = new TextView[5];
    ImageView[][] imageView_team0_mosts = new ImageView[5][3];
    ImageView[][] imageView_team1_mosts = new ImageView[5][3];
    EditText editText_search;
    ImageView[] imageView_position = new ImageView[5];
    ImageView imageView_menu;
    TextView textView_prev;
    TextView textView_next;
    Button button_pick;

    static Context context;
    boolean isPicked = false;
    boolean isLast = false;
    boolean isFirst = true;
    ArrayList<PickClass> pick_serial;
    private int pickIndex = 0;
    private int championIndex = 0;
    private Tree pick_tree = new Tree();
    private Tree.Node presentNode = null;

    Team team0;
    Team team1;

    public class PickClass{
        int type = 0; //0 == 밴 ,  1 == 픽
        int index;
        boolean isOurTeam;
        Champion champion = null;
        public PickClass(int type, boolean isOurTeam, int index){
            this.type = type;
            this.index = index;
            this.isOurTeam = isOurTeam;
        }
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banpick);

        context = this;

        textView_team0_name = findViewById(R.id.banpick_textView_team0_name);
        textView_team1_name = findViewById(R.id.banpick_textView_team1_name);

        imageView_team0_bans[0] = findViewById(R.id.banpick_imageView_ban0);
        imageView_team0_bans[1] = findViewById(R.id.banpick_imageView_ban1);
        imageView_team0_bans[2] = findViewById(R.id.banpick_imageView_ban2);
        imageView_team0_bans[3] = findViewById(R.id.banpick_imageView_ban3);
        imageView_team0_bans[4] = findViewById(R.id.banpick_imageView_ban4);

        imageView_team1_bans[0] = findViewById(R.id.banpick_imageView_ban5);
        imageView_team1_bans[1] = findViewById(R.id.banpick_imageView_ban6);
        imageView_team1_bans[2] = findViewById(R.id.banpick_imageView_ban7);
        imageView_team1_bans[3] = findViewById(R.id.banpick_imageView_ban8);
        imageView_team1_bans[4] = findViewById(R.id.banpick_imageView_ban9);

        imageView_team0_picks[0] = findViewById(R.id.banpick_imageView_pick0);
        imageView_team0_picks[1] = findViewById(R.id.banpick_imageView_pick1);
        imageView_team0_picks[2] = findViewById(R.id.banpick_imageView_pick2);
        imageView_team0_picks[3] = findViewById(R.id.banpick_imageView_pick3);
        imageView_team0_picks[4] = findViewById(R.id.banpick_imageView_pick4);

        imageView_team1_picks[0] = findViewById(R.id.banpick_imageView_pick5);
        imageView_team1_picks[1] = findViewById(R.id.banpick_imageView_pick6);
        imageView_team1_picks[2] = findViewById(R.id.banpick_imageView_pick7);
        imageView_team1_picks[3] = findViewById(R.id.banpick_imageView_pick8);
        imageView_team1_picks[4] = findViewById(R.id.banpick_imageView_pick9);

        textView_team0_tears[0] = findViewById(R.id.banpick_textView_tear0);
        textView_team0_tears[1] = findViewById(R.id.banpick_textView_tear1);
        textView_team0_tears[2] = findViewById(R.id.banpick_textView_tear2);
        textView_team0_tears[3] = findViewById(R.id.banpick_textView_tear3);
        textView_team0_tears[4] = findViewById(R.id.banpick_textView_tear4);

        textView_team1_tears[0] = findViewById(R.id.banpick_textView_tear5);
        textView_team1_tears[1] = findViewById(R.id.banpick_textView_tear6);
        textView_team1_tears[2] = findViewById(R.id.banpick_textView_tear7);
        textView_team1_tears[3] = findViewById(R.id.banpick_textView_tear8);
        textView_team1_tears[4] = findViewById(R.id.banpick_textView_tear9);

        imageView_team0_mosts[0][0] = findViewById(R.id.banpick_imageView_mostpick00);
        imageView_team0_mosts[0][1] = findViewById(R.id.banpick_imageView_mostpick01);
        imageView_team0_mosts[0][2] = findViewById(R.id.banpick_imageView_mostpick02);

        imageView_team0_mosts[1][0] = findViewById(R.id.banpick_imageView_mostpick10);
        imageView_team0_mosts[1][1] = findViewById(R.id.banpick_imageView_mostpick11);
        imageView_team0_mosts[1][2] = findViewById(R.id.banpick_imageView_mostpick12);

        imageView_team0_mosts[2][0] = findViewById(R.id.banpick_imageView_mostpick20);
        imageView_team0_mosts[2][1] = findViewById(R.id.banpick_imageView_mostpick21);
        imageView_team0_mosts[2][2] = findViewById(R.id.banpick_imageView_mostpick22);

        imageView_team0_mosts[3][0] = findViewById(R.id.banpick_imageView_mostpick30);
        imageView_team0_mosts[3][1] = findViewById(R.id.banpick_imageView_mostpick31);
        imageView_team0_mosts[3][2] = findViewById(R.id.banpick_imageView_mostpick32);

        imageView_team0_mosts[4][0] = findViewById(R.id.banpick_imageView_mostpick40);
        imageView_team0_mosts[4][1] = findViewById(R.id.banpick_imageView_mostpick41);
        imageView_team0_mosts[4][2] = findViewById(R.id.banpick_imageView_mostpick42);




        imageView_team1_mosts[0][0] = findViewById(R.id.banpick_imageView_mostpick50);
        imageView_team1_mosts[0][1] = findViewById(R.id.banpick_imageView_mostpick51);
        imageView_team1_mosts[0][2] = findViewById(R.id.banpick_imageView_mostpick52);

        imageView_team1_mosts[1][0] = findViewById(R.id.banpick_imageView_mostpick60);
        imageView_team1_mosts[1][1] = findViewById(R.id.banpick_imageView_mostpick61);
        imageView_team1_mosts[1][2] = findViewById(R.id.banpick_imageView_mostpick62);

        imageView_team1_mosts[2][0] = findViewById(R.id.banpick_imageView_mostpick70);
        imageView_team1_mosts[2][1] = findViewById(R.id.banpick_imageView_mostpick71);
        imageView_team1_mosts[2][2] = findViewById(R.id.banpick_imageView_mostpick72);

        imageView_team1_mosts[3][0] = findViewById(R.id.banpick_imageView_mostpick80);
        imageView_team1_mosts[3][1] = findViewById(R.id.banpick_imageView_mostpick81);
        imageView_team1_mosts[3][2] = findViewById(R.id.banpick_imageView_mostpick82);

        imageView_team1_mosts[4][0] = findViewById(R.id.banpick_imageView_mostpick90);
        imageView_team1_mosts[4][1] = findViewById(R.id.banpick_imageView_mostpick91);
        imageView_team1_mosts[4][2] = findViewById(R.id.banpick_imageView_mostpick92);

        textView_team0_ban_backs[0] = findViewById(R.id.banpick_textView_ban_back0);
        textView_team0_ban_backs[1] = findViewById(R.id.banpick_textView_ban_back1);
        textView_team0_ban_backs[2] = findViewById(R.id.banpick_textView_ban_back2);
        textView_team0_ban_backs[3] = findViewById(R.id.banpick_textView_ban_back3);
        textView_team0_ban_backs[4] = findViewById(R.id.banpick_textView_ban_back4);

        textView_team1_ban_backs[0] = findViewById(R.id.banpick_textView_ban_back5);
        textView_team1_ban_backs[1] = findViewById(R.id.banpick_textView_ban_back6);
        textView_team1_ban_backs[2] = findViewById(R.id.banpick_textView_ban_back7);
        textView_team1_ban_backs[3] = findViewById(R.id.banpick_textView_ban_back8);
        textView_team1_ban_backs[4] = findViewById(R.id.banpick_textView_ban_back9);

        textView_team0_pick_backs[0] = findViewById(R.id.banpick_textView_back0);
        textView_team0_pick_backs[1] = findViewById(R.id.banpick_textView_back1);
        textView_team0_pick_backs[2] = findViewById(R.id.banpick_textView_back2);
        textView_team0_pick_backs[3] = findViewById(R.id.banpick_textView_back3);
        textView_team0_pick_backs[4] = findViewById(R.id.banpick_textView_back4);

        textView_team1_pick_backs[0] = findViewById(R.id.banpick_textView_back5);
        textView_team1_pick_backs[1] = findViewById(R.id.banpick_textView_back6);
        textView_team1_pick_backs[2] = findViewById(R.id.banpick_textView_back7);
        textView_team1_pick_backs[3] = findViewById(R.id.banpick_textView_back8);
        textView_team1_pick_backs[4] = findViewById(R.id.banpick_textView_back9);


        imageView_position[0] = findViewById(R.id.banpick_imageView_top);
        imageView_position[1] = findViewById(R.id.banpick_imageView_jg);
        imageView_position[2] = findViewById(R.id.banpick_imageView_mid);
        imageView_position[3] = findViewById(R.id.banpick_imageView_bot);
        imageView_position[4] = findViewById(R.id.banpick_imageView_sup);

        editText_search = findViewById(R.id.banpick_editText_search);
        imageView_menu = findViewById(R.id.banpick_imageView_menu);
        textView_prev = findViewById(R.id.banpick_textView_left);
        textView_next = findViewById(R.id.banpick_textView_right);
        button_pick = findViewById(R.id.banpick_button_pick);


        recyclerView = findViewById(R.id.banpick_recyclerView);


        //중단 챔피언 리사이클러뷰
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));

        final ChampionAdapter adapter = new ChampionAdapter(ApplicationClass.champions);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ChampionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos, ImageView imageView) {
                if(isLast){
                    return;
                }
                if(adapter.getIsClicked(pos) == false){

                    imageView.setColorFilter(Color.parseColor("#696969"), PorterDuff.Mode.MULTIPLY);
                    adapter.setIsClicked(pos, true);
                    adapter.setOnlyClick(pos, true);
                    isPicked = true;
                    championIndex = pos;
                    button_pick.setBackgroundResource(R.drawable.custom_button);
                    PickClass nowPick = pick_serial.get(pickIndex);
                    if(nowPick.type == 0){
                        if(nowPick.isOurTeam){
                            imageView_team0_bans[nowPick.index]
                                    .setImageResource(ApplicationClass.champions.get(pos).image);
                        }else{
                            imageView_team1_bans[nowPick.index]
                                    .setImageResource(ApplicationClass.champions.get(pos).image);
                        }
                    }else if(nowPick.type == 1){
                        if(nowPick.isOurTeam){
                            imageView_team0_picks[nowPick.index]
                                    .setImageResource(ApplicationClass.champions.get(pos).image);
                        }else{
                            imageView_team1_picks[nowPick.index]
                                    .setImageResource(ApplicationClass.champions.get(pos).image);
                        }
                    }
                }
            }
        });

        if(PopupMakeGameActivity.team0 == null){
            team0 = ApplicationClass.teams.get(1);
        }else{
            team0 = PopupMakeGameActivity.team0;

        }
        if(PopupMakeGameActivity.team1 == null){
            team1 = ApplicationClass.teams.get(1);
        }else{
            team1 = PopupMakeGameActivity.team1;

        }
        textView_team0_name.setText(team0.name);
        textView_team1_name.setText(team1.name);

        if(PopupMakeGameActivity.isDefaultPosition == true){
            textView_team0_ban_backs[0].setBackgroundResource(R.drawable.custom_ban_backgroud_now);
        }else{
            textView_team0_name.setBackgroundResource(R.color.colorRedTeam);
            textView_team1_name.setBackgroundResource(R.color.colorBlueTeam);
            textView_team1_ban_backs[0].setBackgroundResource(R.drawable.custom_ban_backgroud_now);
        }

        for(int i = 0; i < 5; i++){
            setPlayer(team0.players[i], textView_team0_tears[i], imageView_team0_mosts[i]);
            setPlayer(team1.players[i], textView_team1_tears[i], imageView_team1_mosts[i]);
        }
        settingTeamArr(PopupMakeGameActivity.isDefaultPosition);


        //하단

        button_pick.setBackgroundResource(R.drawable.custom_button_not_pick);
        button_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isLast){
                    return;
                }
                if(isPicked == false){
                    return;
                }else{
                    nextPick(adapter);
                }
            }
        });

        registerForContextMenu(imageView_menu);
        imageView_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openContextMenu(imageView_menu);
            }
        });
        textView_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFirst){
                    return;
                }else{
                    prevPick(adapter);
                }
            }
        });
        textView_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo
            }
        });

    }

    public void setBackColor(int pickIndex, boolean isBack){
        PickClass nowPick = pick_serial.get(pickIndex);
        if(nowPick.type == 0){
            if(nowPick.isOurTeam){
                if(isBack){
                    textView_team0_ban_backs[nowPick.index]
                            .setBackgroundResource(R.drawable.custom_ban_backgroud);
                }else{
                    textView_team0_ban_backs[nowPick.index]
                            .setBackgroundResource(R.drawable.custom_ban_backgroud_now);
                }

            }else{
                if(isBack){
                    textView_team1_ban_backs[nowPick.index]
                            .setBackgroundResource(R.drawable.custom_pick_backgroud);
                }else{
                    textView_team1_ban_backs[nowPick.index]
                            .setBackgroundResource(R.drawable.custom_pick_backgroud_now);
                }
            }
        }else if(nowPick.type == 1){
            if(nowPick.isOurTeam){
                if(isBack){
                    textView_team0_pick_backs[nowPick.index]
                            .setBackgroundResource(R.drawable.custom_pick_backgroud);
                }else{
                    textView_team0_pick_backs[nowPick.index]
                            .setBackgroundResource(R.drawable.custom_pick_backgroud_now);
                }
            }else{
                if(isBack){
                    textView_team1_pick_backs[nowPick.index]
                            .setBackgroundResource(R.drawable.custom_pick_backgroud);
                }else{
                    textView_team1_pick_backs[nowPick.index]
                            .setBackgroundResource(R.drawable.custom_pick_backgroud_now);
                }
            }
        }

    }

    @Override
    public void onBackPressed() {
        CustomDialog customDialog = new CustomDialog(BanPickActivity.this);
        customDialog.callFunction("나가시겠습니까?");
        customDialog.setOnOkClickListener(new CustomDialog.OnOkClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_banpick, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.save:
                //저장
                Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_LONG).show();
                return true;
            case R.id.load:
                return true;
            case R.id.tree:
                //todo
                return true;

        }
        return super.onContextItemSelected(item);
    }

    public void settingTeamArr(boolean isDefaultPosition){
        pick_serial = new ArrayList<>();

        int blueTeamBanIndex = 0;
        int redTeamBanIndex = 0;
        int blueTeamPickIndex = 0;
        int redTeamPickIndex = 0;
        boolean blueTeam = true;
        boolean redTeam = false;
        if(isDefaultPosition == false){
            blueTeam = false;
            redTeam = true;
        }
        pick_serial.add(new PickClass(0, blueTeam, blueTeamBanIndex++));
        pick_serial.add(new PickClass(0, redTeam,redTeamBanIndex++));
        pick_serial.add(new PickClass(0, blueTeam,blueTeamBanIndex++));
        pick_serial.add(new PickClass(0, redTeam,redTeamBanIndex++));
        pick_serial.add(new PickClass(0, blueTeam,blueTeamBanIndex++));
        pick_serial.add(new PickClass(0, redTeam,redTeamBanIndex++));

        pick_serial.add(new PickClass(1, blueTeam,blueTeamPickIndex++));
        pick_serial.add(new PickClass(1, redTeam,redTeamPickIndex++));
        pick_serial.add(new PickClass(1, redTeam,redTeamPickIndex++));
        pick_serial.add(new PickClass(1, blueTeam,blueTeamPickIndex++));
        pick_serial.add(new PickClass(1, blueTeam,blueTeamPickIndex++));
        pick_serial.add(new PickClass(1, redTeam,redTeamPickIndex++));

        pick_serial.add(new PickClass(0, redTeam,redTeamBanIndex++));
        pick_serial.add(new PickClass(0, blueTeam,blueTeamBanIndex++));
        pick_serial.add(new PickClass(0, redTeam,redTeamBanIndex++));
        pick_serial.add(new PickClass(0, blueTeam,blueTeamBanIndex++));

        pick_serial.add(new PickClass(1, redTeam,redTeamPickIndex++));
        pick_serial.add(new PickClass(1, blueTeam,blueTeamPickIndex++));
        pick_serial.add(new PickClass(1, blueTeam,blueTeamPickIndex++));
        pick_serial.add(new PickClass(1, redTeam,redTeamPickIndex++));

        for(int i =0; i < pick_serial.size(); i++){
            System.out.println(pick_serial.get(i).type + " " +pick_serial.get(i).isOurTeam +" "+pick_serial.get(i).index );
        }

    }
    public void setPlayer(Team.Player player, TextView tear, ImageView[] mosts){
        tear.setText(player.tear);
        int size = player.most.size() < 3 ? player.most.size() : 3;
        System.out.println(player.name);
        System.out.println(size);
        for(int i = 0; i < size; i++){
            System.out.println(player.most.get(i).name);
            System.out.println(player.most.get(i).image);
            mosts[i].setImageResource(player.most.get(i).image);
        }
    }
    public void prevPick(ChampionAdapter adapter){
        isLast = false;
        button_pick.setBackgroundResource(R.drawable.custom_button_not_pick);
        setBackColor(pickIndex, true);
        adapter.setClickClear(true);
        removePick();
        pickIndex--;
        setBackColor(pickIndex, false);
        isPicked = false;
        if(pickIndex == 0){
            isFirst = true;
        }
    }
    public void nextPick(ChampionAdapter adapter){
        isFirst = false;
        button_pick.setBackgroundResource(R.drawable.custom_button_not_pick);
        setBackColor(pickIndex, true);
        adapter.setClickClear(false);
        addPick(pick_serial.get(pickIndex), championIndex);
        pickIndex++;
        isPicked = false;
        if(pickIndex == pick_serial.size()){
            isLast = true;
            return;
        }
        setBackColor(pickIndex, false);

    }

    public void removePick(){
//            int nextIndex = presentNode.childIndex;
//            Tree.Node prevNode;
//            if(presentNode.parent. == 1){
//                prevNode = presentNode.parent[0];
//            }else{
//                prevNode = presentNode.parent[showDialog()];
//            }
//            prevNode.remove(nextIndex);
    }

    public int showDialog(String text, int type){
        CustomDialog customDialog = new CustomDialog(BanPickActivity.this);
        customDialog.callFunction(text);
        if(type == 0){

        }
        return 0;
    }
    public void addPick(PickClass pickClass, int pos){
        Champion champion = ApplicationClass.champions.get(pos);
        pickClass.champion = champion;
        if(pickIndex == 0){
            if(pick_tree.isOktoInsertRoot()){
                pick_tree.setRoot(Tree.makeRoot(pickClass));
                Toast.makeText(this, "루트가 추가.", Toast.LENGTH_LONG).show();
                presentNode = pick_tree.getRoot(0);
            }else{
                Toast.makeText(this, "루트를 더 이상 추가할 수 없습니다.", Toast.LENGTH_LONG).show();
            }
        }
        else{
            if(presentNode.isOktoInsertNode()){
                int childNum = presentNode.getChildNum();
                presentNode.add(Tree.makeNode(pickClass, pickIndex, childNum));
                Toast.makeText(this, "노드가 추가.", Toast.LENGTH_LONG).show();
                presentNode = presentNode.child[childNum];
            }else{
                Toast.makeText(this, "노드를 더 이상 추가할 수 없습니다.", Toast.LENGTH_LONG).show();
            }
        }
    }

}
