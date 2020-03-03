package com.example.lol_ban_pick_manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import io.paperdb.Paper;

import static com.example.lol_ban_pick_manager.ApplicationClass.isNeedToSetting;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentMatchActivity fragmentMatchActivity = new FragmentMatchActivity();
    private FragmentTeamActivity fragmentTeamActivity = new FragmentTeamActivity();
    private FragmentPlayerActivity fragmentPlayerActivity = new FragmentPlayerActivity();

    static Context context;
    ImageView demo;

    public Bitmap setBitmap(int id){
        demo.setImageResource(id);
        Drawable drawable1 = demo.getDrawable();
        Bitmap bitmap = ((BitmapDrawable)drawable1).getBitmap();
        return bitmap;
    }

    public void setting(){
        if(isNeedToSetting){
            Champion.championSetting();
            Paper.init(this);
            Paper.book().destroy();
            Team.Player.makePlus();
            Team.makePlus();
            Match.makePlus();
            Team.makeDefaultTeam();
            Team.Player.makeDefaultPlayer();
            ApplicationClass.players.add(Team.makePlayer("faker", "D3", "가렌", "리산드라", "코그모"));
            ApplicationClass.players.add(Team.makePlayer("차상근", "C", "베인", "아펠리오스", "자야", "케이틀린"));
            ApplicationClass.players.add(Team.makePlayer("qwerqwe", "G2","리신"));
            ApplicationClass.savePlayer(ApplicationClass.players.get(1));
            ApplicationClass.savePlayer(ApplicationClass.players.get(2));
            ApplicationClass.savePlayer(ApplicationClass.players.get(3));

            ApplicationClass.loadData();
            isNeedToSetting = false;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        demo = findViewById(R.id.demo);
        setting();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.framelayout, fragmentMatchActivity).commitAllowingStateLoss();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());


    }

    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch(menuItem.getItemId())
            {
                case R.id.item_match:
                    transaction.replace(R.id.framelayout, fragmentMatchActivity).commitAllowingStateLoss();
                    break;
                case R.id.item_team:
                    transaction.replace(R.id.framelayout, fragmentTeamActivity).commitAllowingStateLoss();
                    break;
                case R.id.item_player:
                    transaction.replace(R.id.framelayout, fragmentPlayerActivity).commitAllowingStateLoss();
                    break;

            }
            return true;
        }
    }
}
