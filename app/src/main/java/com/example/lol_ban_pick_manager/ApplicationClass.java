package com.example.lol_ban_pick_manager;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.TreeSet;


public class ApplicationClass extends Application {

    static int totalGameNum = 0;
    static int totalTeamNum = 0;
    static int maxNodeNum = 5;
    static ArrayList<Team> teams = new ArrayList<>();
    static ArrayList<Champion> champions = new ArrayList<>();
    static ArrayList<Match> matches = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        setting();
    }


    static void showToast(Context context, String text){
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }
    static void addMatch(Match match){
        matches.add(match);
    }
    static void setting(){
        if(teams.size() == 0){
            Champion.championSetting();
            teams.add(Team.makePlus());
            teams.add(Team.makeDefaultTeam());
            teams.add(Team.makeTeam("SKT T1", R.drawable.ahri,
                Team.makePlayers(Team.makePlayer("khan", "D3", "갈리오", "나미", "갱플랭크"),
                                 Team.makePlayer("Haru", "C", "다리우스", "그레이브즈"),
                                 Team.makePlayer("Faker", "GM", "니달리", "니코", "나서스", "노틸러스"),
                                 Team.makePlayer("Teddy", "G2", "나르", "나미"),
                                 Team.makePlayer("Effort", "M", "갱플랭크"))));
            teams.add(Team.makeTeam("Gen G", R.drawable.leesin,
                    Team.makePlayers(Team.makePlayer("asdf", "P1", "나미", "나미", "나미", "나서스"),
                            Team.makePlayer("1234", "B1", "나르"),
                            Team.makePlayer("5678", "UN", "갱플랭크", "갈리오", "노틸러스"),
                            Team.makePlayer("xxxx", "S1", "니달리"),
                            Team.makePlayer("qwer", "I3", "나미", "니코", "다리우스"))));

            teams.add(Team.makeTeam("2", R.drawable.maokai,
                    Team.makePlayers(Team.makePlayer("khan", "D3", "갈리오", "나미", "갱플랭크"),
                            Team.makePlayer("Haru", "C", "다리우스", "그레이브즈"),
                            Team.makePlayer("Faker", "GM", "니달리", "니코", "나서스", "노틸러스"),
                            Team.makePlayer("Teddy", "G2", "나르", "나미"),
                            Team.makePlayer("Effort", "M", "갱플랭크"))));

            teams.add(Team.makeTeam("3", R.drawable.normal_team_logo,
                    Team.makePlayers(Team.makePlayer("khan", "D3", "갈리오", "나미", "갱플랭크"),
                            Team.makePlayer("Haru", "C", "다리우스", "그레이브즈"),
                            Team.makePlayer("Faker", "GM", "니달리", "니코", "나서스", "노틸러스"),
                            Team.makePlayer("Teddy", "G2", "나르", "나미"),
                            Team.makePlayer("Effort", "M", "갱플랭크"))));

            teams.add(Team.makeTeam("4", R.drawable.aatrox,
                    Team.makePlayers(Team.makePlayer("khan", "D3", "갈리오", "나미", "갱플랭크"),
                            Team.makePlayer("Haru", "C", "다리우스", "그레이브즈"),
                            Team.makePlayer("Faker", "GM", "니달리", "니코", "나서스", "노틸러스"),
                            Team.makePlayer("Teddy", "G2", "나르", "나미"),
                            Team.makePlayer("Effort", "M", "갱플랭크"))));

            teams.add(Team.makeTeam("5", R.drawable.diana,
                    Team.makePlayers(Team.makePlayer("khan", "D3", "갈리오", "나미", "갱플랭크"),
                            Team.makePlayer("Haru", "C", "다리우스", "그레이브즈"),
                            Team.makePlayer("Faker", "GM", "니달리", "니코", "나서스", "노틸러스"),
                            Team.makePlayer("Teddy", "G2", "나르", "나미"),
                            Team.makePlayer("Effort", "M", "갱플랭크"))));

            teams.add(Team.makeTeam("6", R.drawable.zyra,
                    Team.makePlayers(Team.makePlayer("khan", "D3", "갈리오", "나미", "갱플랭크"),
                            Team.makePlayer("Haru", "C", "다리우스", "그레이브즈"),
                            Team.makePlayer("Faker", "GM", "니달리", "니코", "나서스", "노틸러스"),
                            Team.makePlayer("Teddy", "G2", "나르", "나미"),
                            Team.makePlayer("Effort", "M", "갱플랭크"))));

            teams.add(Team.makeTeam("7", R.drawable.xerath,
                    Team.makePlayers(Team.makePlayer("khan", "D3", "갈리오", "나미", "갱플랭크"),
                            Team.makePlayer("Haru", "C", "다리우스", "그레이브즈"),
                            Team.makePlayer("Faker", "GM", "니달리", "니코", "나서스", "노틸러스"),
                            Team.makePlayer("Teddy", "G2", "나르", "나미"),
                            Team.makePlayer("Effort", "M", "갱플랭크"))));

        }
    }
}
