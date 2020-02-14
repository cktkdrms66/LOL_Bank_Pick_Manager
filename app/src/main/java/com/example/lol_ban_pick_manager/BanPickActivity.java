package com.example.lol_ban_pick_manager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
    ImageView imageView_swap;
    
    static Context context;


    boolean isPicked = false;
    boolean isLast = false;
    boolean isFirst = true;
    boolean isPickChange = true;
    int swapIndex = 0;

    private int[][] mostImage0 = new int[5][3];
    private int[][] mostImage1 = new int[5][3];
    ArrayList<Match.GameElement> pickSerial;
    private boolean[] isImageClickedTeam0 = new boolean[5];
    private boolean[] isImageClickedTeam1 = new boolean[5];
    private int clickedIndex0 = -1;
    private int clickedIndex1 = -1;
    private int lastPickIndex = 0;
    private int pickIndex = 0;
    private Match match;
    private Match.Game game;
    Team team0;
    Team team1;
    private Match.SwapPhaseClass swapPhaseClass;

   

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banpick);

        context = this;

        //매치 객체 만들기
        Intent intent = getIntent();
        String matchName = intent.getExtras().getString("matchName");
        boolean isDefaultPosition = intent.getExtras().getBoolean("isDefault");
        match = Match.makeMatch(matchName);

        //게임 객체 만들기
        game = new Match.Game();
        pickSerial = game.gameElements;
        
        
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


        //상단 팀이름 설정
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

        //벤픽 초기 설정
        if(isDefaultPosition == true){
            textView_team0_ban_backs[0].setBackgroundResource(R.drawable.custom_ban_backgroud_now);
        }else{
            textView_team0_name.setBackgroundResource(R.color.colorRedTeam);
            textView_team1_name.setBackgroundResource(R.color.colorBlueTeam);
            textView_team1_ban_backs[0].setBackgroundResource(R.drawable.custom_ban_backgroud_now);
        }

        for(int i = 0; i < 5; i++){
            setPlayer0(team0.players[i], textView_team0_tears[i], imageView_team0_mosts[i], i);
            setPlayer1(team1.players[i], textView_team1_tears[i], imageView_team1_mosts[i], i);
        }
        settingTeamArr(isDefaultPosition);



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

                    setButton_pick(true);
                    Match.PickClass nowPick = (Match.PickClass) pickSerial.get(pickIndex);
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




        //하단
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
                    ApplicationClass.showToast(context, "가장 처음 밴픽입니다.");
                    return;
                }
                lookPrevPick(adapter);

            }
        });
        
        textView_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLast) {
                    if(swapIndex == 0 && swapPhaseClass.isSwapChange()){
                        swapGo();
                        swapIndex++;
                    }else{
                        ApplicationClass.showToast(context, "더 이상 진행할 수 없습니다.");
                    }
                    return;
                }
                lookNextPick(adapter);
            }
        
            
        });

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
        
    }
    
    

    public void setMostColor(boolean isLightOn, ChampionAdapter adapter){
        PorterDuff.Mode mode;
        if(isLightOn){
            mode = PorterDuff.Mode.MULTIPLY;
        }else{
            mode = PorterDuff.Mode.DST;
        }
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 3; j++){
                int image = adapter.getmOnlyItemPosition();
                if(image == mostImage0[i][j]){
                    imageView_team0_mosts[i][j].setColorFilter(Color.parseColor("#696969"), mode);
                }
                if(image == mostImage1[i][j]){
                    imageView_team1_mosts[i][j].setColorFilter(Color.parseColor("#696969"), mode);
                }
            }
        }

    }
    public void setImage(int pickIndex){
        Match.PickClass nowPick = (Match.PickClass) pickSerial.get(pickIndex);
        int image = Champion.getChampionImage(nowPick.championIndex);
        if(nowPick.type == 0){
            if(nowPick.isOurTeam){
                imageView_team0_bans[nowPick.index]
                        .setImageResource(image);

            }else{
                imageView_team1_bans[nowPick.index]
                        .setImageResource(image);
            }
        }else if(nowPick.type == 1){
            if(nowPick.isOurTeam){
                imageView_team0_picks[nowPick.index]
                        .setImageResource(image);
            }else{
                imageView_team1_picks[nowPick.index]
                        .setImageResource(image);
            }
        }
    }
    public void resetImage(int pickIndex){
        Match.PickClass nowPick = (Match.PickClass)pickSerial.get(pickIndex);
        if(nowPick.type == 0){
            if(nowPick.isOurTeam){
                imageView_team0_bans[nowPick.index]
                        .setImageResource(R.drawable.normal_team_logo);

            }else{
                imageView_team1_bans[nowPick.index]
                        .setImageResource(R.drawable.normal_team_logo);
            }
        }else if(nowPick.type == 1){
            if(nowPick.isOurTeam){
                imageView_team0_picks[nowPick.index]
                        .setImageResource(R.drawable.normal_team_logo);
            }else{
                imageView_team1_picks[nowPick.index]
                        .setImageResource(R.drawable.normal_team_logo);
            }
        }
    }


    public void setBackColor(int pickIndex, int lightType){
        //lightType  0 == 아직 픽하기 전, 1 == 현재 픽 차례, 2 == 픽 후
        Match.PickClass nowPick = (Match.PickClass) pickSerial.get(pickIndex);
        if(nowPick.type == 0){
            if(nowPick.isOurTeam){
                if(lightType == 0){
                    textView_team0_ban_backs[nowPick.index]
                            .setBackgroundResource(R.drawable.custom_ban_backgroud);
                }else if(lightType == 1){
                    textView_team0_ban_backs[nowPick.index]
                            .setBackgroundResource(R.drawable.custom_ban_backgroud_now);
                }else{
                    textView_team0_ban_backs[nowPick.index]
                            .setBackgroundResource(R.drawable.custom_ban_backgroud_after_pick);
                }

            }else{
                if(lightType == 0){
                    textView_team1_ban_backs[nowPick.index]
                            .setBackgroundResource(R.drawable.custom_pick_backgroud);
                }else if(lightType == 1){
                    textView_team1_ban_backs[nowPick.index]
                            .setBackgroundResource(R.drawable.custom_pick_backgroud_now);
                }else{
                    textView_team1_ban_backs[nowPick.index]
                            .setBackgroundResource(R.drawable.custom_pick_backgroud_after_pick);
                }
            }
        }else if(nowPick.type == 1){
            if(nowPick.isOurTeam){
                if(lightType == 0){
                    textView_team0_pick_backs[nowPick.index]
                            .setBackgroundResource(R.drawable.custom_pick_backgroud);
                }else if(lightType ==1){
                    textView_team0_pick_backs[nowPick.index]
                            .setBackgroundResource(R.drawable.custom_pick_backgroud_now);
                }else{
                    textView_team0_pick_backs[nowPick.index]
                            .setBackgroundResource(R.drawable.custom_pick_backgroud_after_pick);
                }
            }else{
                if(lightType == 0){
                    textView_team1_pick_backs[nowPick.index]
                            .setBackgroundResource(R.drawable.custom_pick_backgroud);
                }else if(lightType == 1){
                    textView_team1_pick_backs[nowPick.index]
                            .setBackgroundResource(R.drawable.custom_pick_backgroud_now);
                }else{
                    textView_team1_pick_backs[nowPick.index]
                            .setBackgroundResource(R.drawable.custom_pick_backgroud_after_pick);
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

        pickSerial.add(new Match.PickClass(0, blueTeam, blueTeamBanIndex++));
        pickSerial.add(new Match.PickClass(0, redTeam,redTeamBanIndex++));
        pickSerial.add(new Match.PickClass(0, blueTeam,blueTeamBanIndex++));
        pickSerial.add(new Match.PickClass(0, redTeam,redTeamBanIndex++));
        pickSerial.add(new Match.PickClass(0, blueTeam,blueTeamBanIndex++));
        pickSerial.add(new Match.PickClass(0, redTeam,redTeamBanIndex++));

        pickSerial.add(new Match.PickClass(1, blueTeam,blueTeamPickIndex++));
        pickSerial.add(new Match.PickClass(1, redTeam,redTeamPickIndex++));
        pickSerial.add(new Match.PickClass(1, redTeam,redTeamPickIndex++));
        game.makePickClassEqaulPhase();
        pickSerial.add(new Match.PickClass(1, blueTeam,blueTeamPickIndex++));
        pickSerial.add(new Match.PickClass(1, blueTeam,blueTeamPickIndex++));
        game.makePickClassEqaulPhase();
        pickSerial.add(new Match.PickClass(1, redTeam,redTeamPickIndex++));

        pickSerial.add(new Match.PickClass(0, redTeam,redTeamBanIndex++));
        pickSerial.add(new Match.PickClass(0, blueTeam,blueTeamBanIndex++));
        pickSerial.add(new Match.PickClass(0, redTeam,redTeamBanIndex++));
        pickSerial.add(new Match.PickClass(0, blueTeam,blueTeamBanIndex++));

        pickSerial.add(new Match.PickClass(1, redTeam,redTeamPickIndex++));
        pickSerial.add(new Match.PickClass(1, blueTeam,blueTeamPickIndex++));
        pickSerial.add(new Match.PickClass(1, blueTeam,blueTeamPickIndex++));
        game.makePickClassEqaulPhase();
        pickSerial.add(new Match.PickClass(1, redTeam,redTeamPickIndex++));

        pickSerial.add(new Match.SwapPhaseClass());
        swapPhaseClass = (Match.SwapPhaseClass)pickSerial.get(pickSerial.size()-1);


    }
    public void setPlayer0(Team.Player player, TextView tear, ImageView[] mosts, int index){
        tear.setText(player.tear);
        int size = player.most.size() < 3 ? player.most.size() : 3;
        for(int i = 0; i < size; i++){
            mosts[i].setImageResource(player.most.get(i).image);
            mostImage0[index][i] = player.most.get(i).image;
        }
        for(int i = size; i < 3; i++){
            mostImage0[index][i] = -1;
        }
    }
    public void setPlayer1(Team.Player player, TextView tear, ImageView[] mosts, int index){
        tear.setText(player.tear);
        int size = player.most.size() < 3 ? player.most.size() : 3;
        for(int i = 0; i < size; i++){
            mosts[i].setImageResource(player.most.get(i).image);
            mostImage1[index][i] = player.most.get(i).image;
        }
        for(int i = size; i < 3; i++){
            mostImage0[index][i] = -1;
        }
    }


    public int getPickIndex() {
        return pickIndex;
    }

    public void setButton_pick(boolean isLightOn){
        if(isLightOn){
            button_pick.setBackgroundResource(R.drawable.custom_button);
            isPicked = true;
        }else{
            button_pick.setBackgroundResource(R.drawable.custom_button_not_pick);
            isPicked = false;
        }
    }
    public void lookPrevPick(ChampionAdapter adapter){

        isPickChange = false;
        if(isLast){
            //스왑 페이즈일때
            if(swapIndex == 0){
                //처음 스왑 페이즈일때
                swapUnAvailable();
            }else if(swapIndex == 1){
                //내가 바꾼 스왑 페이즈일때
                swapIndex--;
                swapReturn();
                return;
            }
        }else{
            //현재 픽을 조정한다
            Match.PickClass nowPick = ((Match.PickClass)pickSerial.get(pickIndex));
            if(nowPick.equalPhase != null){
                if(nowPick.isFirst){
                    //현재 픽과 같은 페이즈가 있고 현재 픽이 앞이라면 둘다 불을 끈다.
                    setBackColor(pickIndex, 0);
                    setBackColor(pickIndex  +1, 0);
                    resetImage(pickIndex);
                }else{
                    //아니라면 그냥 현재 픽만 이미지를 바꾸고, 배경색은 그대로 빨강으로 둔다
                    resetImage(pickIndex);
                }

            }else{
                //현재 픽에 불을 끄고 이미지를 없는 걸로 한다.
                setBackColor(pickIndex, 0);
                resetImage(pickIndex);
            }

        }


        pickIndex--;
        isLast = false;

        //이전 픽에 대한 이야기.
        //현재 픽에 불을 켜준다
        Match.PickClass nowPick = (Match.PickClass) pickSerial.get(pickIndex);
        setBackColor(pickIndex, 1);
        setImage(pickIndex);
        if(nowPick.equalPhase != null){
            if(nowPick.isFirst == false){
                //만약 또 불을 앞에 켜야될 놈이 있으면 켜준다
                setBackColor(pickIndex-1, 1);
            }

        }

        setButton_pick(true);
        adapter.setClickClear(true, false);

        if(pickIndex == 0){
            isFirst = true;
        }

    }

    public void lookNextPick(ChampionAdapter adapter){
        if(lastPickIndex == pickIndex){
            ApplicationClass.showToast(context, "마지막 밴픽입니다.");
            return;
        }

        isFirst = false;

        setButton_pick(true);

        //현재 픽 완료
        Match.PickClass nowPick = ((Match.PickClass)pickSerial.get(pickIndex));
        setBackColor(pickIndex, 2);
        if(nowPick.equalPhase != null){
            //만약 현재 픽의 앞에 끌 놈이 있다면 끄기
            if(nowPick.isFirst == false){
                setBackColor(pickIndex-1, 2);
            }
        }
        setImage(pickIndex);
        adapter.setClickClear(true, true);

        pickIndex++;

        //마지막이라면
        if(pickIndex + 1 == pickSerial.size()){
            isLast = true;
            swapAvailable();
            swapIndex = 0;
            return;
        }


        //다음픽에 대한 이야기
        //현재 픽 불켜기
        nowPick = ((Match.PickClass)pickSerial.get(pickIndex));
        setBackColor(pickIndex, 1);
        if(isPickChange == false && nowPick.championIndex != -1){
            //만약 픽이 변경되지 않았다면 보여주기
            setImage(pickIndex);
        }
        if(nowPick.equalPhase != null){
            //뒤에 불 킬 놈이 있다면 키기
            if(nowPick.isFirst){
                setBackColor(pickIndex+1, 1);
            }
        }




    }


    public void nextPick(ChampionAdapter adapter){
        isFirst = false;
        isPickChange = false;

        //픽 완료 버튼에 불 끄기
        setButton_pick(false);

        //현재 픽 인덱스의 챔피언을 바꿈. 현재 인덱스의 배경색을 픽 후로 변경
        isPickChange = false;
        Match.PickClass nowPick = (Match.PickClass) pickSerial.get(pickIndex);
        if(nowPick.championIndex != adapter.getmOnlyItemPosition()){
            isPickChange = true;
        }
        nowPick.setChampionIndex(adapter.getmOnlyItemPosition());
        setBackColor(pickIndex, 2);

        adapter.setClickClear(false, true);


        //픽 인덱스 증가
        pickIndex++;
        if(isPickChange){
            lastPickIndex = pickIndex;
        }

        //벤픽페이즈일때
        if(pickIndex + 1 == pickSerial.size()){
            isLast = true;
            swapAvailable();
            return;
        }

        //픽에 불켜기
        setBackColor(pickIndex, 1);
        nowPick = ((Match.PickClass)pickSerial.get(pickIndex));
        //같은 페이즈에 픽이 하나가 아니고, 뒤에 불킬 놈 있을 때
        if(nowPick.equalPhase != null){
            if(nowPick.isFirst){
                System.out.println(pickIndex + " " + nowPick.equalPhase.index);
                setBackColor(pickIndex +1, 1);
            }
        }

    }



    //swap

    public void swapGo(){
        if(isPickChange){
            //픽이 바뀌었을떄
            ApplicationClass.showToast(context,"더 이상 진행할 수 없습니다.");
            return;
        }

        //픽이 바뀌지 않고, 스왑이 바뀌었을때
        if(swapPhaseClass.isSwapChange()){
            for(int i = 0; i < 5; i++){
                imageView_team0_picks[i].setImageBitmap(swapPhaseClass.bitmapsNew0[i]);
                imageView_team1_picks[i].setImageBitmap(swapPhaseClass.bitmapsNew1[i]);
            }
        }else{
            ApplicationClass.showToast(context,"더 이상 진행할 수 없습니다.");
            return;
        }
    }
    public void swapReturn(){
        for(int i = 0; i < 5; i++){
            imageView_team0_picks[i].setImageBitmap(swapPhaseClass.bitmapsOld0[i]);
            imageView_team1_picks[i].setImageBitmap(swapPhaseClass.bitmapsOld1[i]);
        }
    }

    public void setSwapBitMap(){
        for(int i = 0; i < 5; i++){
            swapPhaseClass.bitmapsOld0[i] =
                    ((BitmapDrawable)imageView_team0_picks[i].getDrawable()).getBitmap();
            swapPhaseClass.bitmapsOld1[i] =
                    ((BitmapDrawable)imageView_team1_picks[i].getDrawable()).getBitmap();
            swapPhaseClass.bitmapsNew0[i] =
                    ((BitmapDrawable)imageView_team0_picks[i].getDrawable()).getBitmap();
            swapPhaseClass.bitmapsNew1[i] =
                    ((BitmapDrawable)imageView_team1_picks[i].getDrawable()).getBitmap();


        }
    }
    public void swapAvailable (){
        ApplicationClass.showToast(context, "이제 스왑할 수 있습니다.");
        if(isPickChange){
            setSwapBitMap();
        }
        for(int i = 0; i < 5; i++){
            textView_team0_pick_backs[i].setBackgroundResource(R.drawable.custom_pick_backgroud_swap);
            textView_team1_pick_backs[i].setBackgroundResource(R.drawable.custom_pick_backgroud_swap);

            imageView_team0_picks[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int i = 0;
                    ImageView nowImageView = (ImageView)view;
                    for(ImageView imageView: imageView_team0_picks){
                        if(imageView == nowImageView){
                            break;
                        }
                        i++;
                    }
                    if(isImageClickedTeam0[i] == false){
                        isImageClickedTeam0[i] = true;
                        imageView_team0_picks[i].setColorFilter(Color.parseColor("#696969"), PorterDuff.Mode.MULTIPLY);

                        if(clickedIndex0 == -1){
                            clickedIndex0 = i;                         
                        }else{
                            if(swapIndex == 0){
                                setSwapBitMap();
                            }
                            swapImage(imageView_team0_picks[i], imageView_team0_picks[clickedIndex0], i, clickedIndex0, true);
                            imageView_team0_picks[i].setColorFilter(Color.parseColor("#696969"), PorterDuff.Mode.DST);
                            imageView_team0_picks[clickedIndex0].setColorFilter(Color.parseColor("#696969"), PorterDuff.Mode.DST);
                            isImageClickedTeam0[i] = false;
                            isImageClickedTeam0[clickedIndex0] = false;
                            clickedIndex0 = -1;
                            if(swapPhaseClass.isSwapChange()){
                                swapIndex = 1;
                            }else{
                                swapIndex = 0;
                            }
                        }
                        
                    }else{
                        isImageClickedTeam0[i] = false;
                        clickedIndex0 = -1;
                        imageView_team0_picks[i].setColorFilter(Color.parseColor("#696969"), PorterDuff.Mode.DST);
                    }
                }
            });

            imageView_team1_picks[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int i = 0;
                    ImageView nowImageView = (ImageView)view;
                    for(ImageView imageView: imageView_team1_picks){
                        if(imageView == nowImageView){
                            break;
                        }
                        i++;
                    }
                    if(isImageClickedTeam1[i] == false){
                        isImageClickedTeam1[i] = true;
                        imageView_team1_picks[i].setColorFilter(Color.parseColor("#696969"), PorterDuff.Mode.MULTIPLY);

                        if(clickedIndex1 == -1){
                            clickedIndex1 = i;
                        }else{
                            if(swapIndex == 0){
                                setSwapBitMap();
                            }
                            swapImage(imageView_team1_picks[i], imageView_team1_picks[clickedIndex1], i, clickedIndex1, false);
                            imageView_team1_picks[i].setColorFilter(Color.parseColor("#696969"), PorterDuff.Mode.DST);
                            imageView_team1_picks[clickedIndex1].setColorFilter(Color.parseColor("#696969"), PorterDuff.Mode.DST);
                            isImageClickedTeam1[i] = false;
                            isImageClickedTeam1[clickedIndex1] = false;
                            clickedIndex1 = -1;
                            if(swapPhaseClass.isSwapChange()){
                                swapIndex = 1;
                            }else{
                                swapIndex = 0;
                            }

                        }

                    }else{
                        isImageClickedTeam1[i] = false;
                        clickedIndex1 = -1;
                        imageView_team1_picks[i].setColorFilter(Color.parseColor("#696969"), PorterDuff.Mode.DST);
                    }
                }
            });
        }
    }

    public void swapUnAvailable(){
        for(int i = 0; i < 5; i++){

            textView_team0_pick_backs[i].setBackgroundResource(R.drawable.custom_pick_backgroud_after_pick);
            textView_team1_pick_backs[i].setBackgroundResource(R.drawable.custom_pick_backgroud_after_pick);

            imageView_team0_picks[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    return;
                }
            });

            imageView_team1_picks[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    return;
                }
            });
        }
    }

    public void swapImage(ImageView imageView0, ImageView imageView1, int i, int j, boolean isOurTeam){
        Drawable drawable0 = imageView0.getDrawable();
        Drawable drawable1 = imageView1.getDrawable();

        Bitmap bitmap0 = ((BitmapDrawable)drawable0).getBitmap();
        Bitmap bitmap1 = ((BitmapDrawable)drawable1).getBitmap();

        imageView0.setImageBitmap(bitmap1);
        imageView1.setImageBitmap(bitmap0);
        if(isOurTeam){
            swapPhaseClass.bitmapsNew0[i] = bitmap1;
            swapPhaseClass.bitmapsNew0[j] = bitmap0;
        }else{
            swapPhaseClass.bitmapsNew1[i] = bitmap1;
            swapPhaseClass.bitmapsNew1[j] = bitmap0;
        }

    }

}