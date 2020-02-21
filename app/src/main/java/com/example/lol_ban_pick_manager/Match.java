package com.example.lol_ban_pick_manager;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.TextView;

import java.util.ArrayList;

public class Match {

    int id;
    String name;
    int type;// 0 == 플러스, 1 == 디폴트(삭제 불가) 2 == 그냥 기본팀
    boolean isTeam0Blue;
    ArrayList<Game> games = new ArrayList<>();//10개까지 추가가능.
    Team team0;
    Team team1;
    int gameNum = 0;

    public Match(int i){
        if(i == 0){
            name = "";
            type = 0;
        }else{
            name = "기본 매치업";
            type = 1;
            id = ApplicationClass.matchID++;
            team0 = ApplicationClass.teams.get(1);
            team1 = ApplicationClass.teams.get(1);
            isTeam0Blue = true;
            games.add(new Game());
        }

    }

    public Match(String name, Team team0, Team team1, boolean isTeam0Blue, int gameNum, ArrayList<Game> games){
        id = ApplicationClass.matchID++;
        type = 2;
        this.name = name;
        this.team0 = team0;
        this.team1 = team1;
        this.isTeam0Blue = isTeam0Blue;
        this.gameNum = gameNum;
        this.games = games;
    }
    public static Match makeMatch(String name, Team team0, Team team1, boolean isTeam0Blue, int gameNum, ArrayList<Game> games){
        Match match = new Match(name, team0, team1, isTeam0Blue, gameNum, games);
        ApplicationClass.matches.add(match);
        ApplicationClass.totalMatchNum++;
        return match;
    }
    public static Match makeDefaultMatch(){
        Match match = new Match(1);
        ApplicationClass.matches.add(match);
        ApplicationClass.totalMatchNum++;
        return new Match(1);
    }
    public static Match makePlus(){
        Match match = new Match(0);
        ApplicationClass.matches.add(match);
        return new Match(0);
    }
    public static ArrayList<Game> makeDefaultgames(){
        ArrayList<Game> games = new ArrayList<>();
        games.add(new Game());
        return games;
    }


    public static class Game{
        String name;
        int victoryTeamLogo;
        int type;// 0 == 플러스,  1 == 그냥 기본팀
        int star = 0;
        ArrayList<GameElement> gameElements = new ArrayList<>();

        public Game(){
            //plus
            type = 0;
            name = "";
            victoryTeamLogo = R.drawable.nothing;

        }
        public Game(String name, int victoryTeamLogo, int star, ArrayList<GameElement> gameElements){
            type = 1;
            this.name = name;
            this.victoryTeamLogo = victoryTeamLogo;
            this.star = star;
            this.gameElements = gameElements;
        }


        public void makePickClassEqaulPhase(){
            PickClass gameElement0 = (PickClass)gameElements.get(gameElements.size()-2);
            PickClass gameElement1 = (PickClass)gameElements.get(gameElements.size()-1);
            gameElement0.setEqualPhase(gameElement1);
            gameElement0.isFirst = true;
            gameElement1.setEqualPhase(gameElement0);
            gameElement1.isFirst = false;
        }
    }

    public static class GameElement{
        int type;//0 == 밴 ,  1 == 픽, 2 == 스왑페이즈
    }
    public static class PickClass extends  GameElement{
        int index;
        int championIndex = -1;
        boolean isOurTeam;
        boolean isFirst = true;
        PickClass equalPhase = null;

        public PickClass(int type, boolean isOurTeam, int index){
            this.type = type;
            this.index = index;
            this.isOurTeam = isOurTeam;
        }
        public void setChampionIndex(int championIndex){
            this.championIndex = championIndex;
        }
        public void setEqualPhase(PickClass pickClass){
            this.equalPhase = pickClass;
        }
    }

    public static class SwapPhaseClass extends GameElement{
        Bitmap[] bitmapsOld0 = new Bitmap[5];
        Bitmap[] bitmapsOld1 = new Bitmap[5];
        Bitmap[] bitmapsNew0 = new Bitmap[5];
        Bitmap[] bitmapsNew1 = new Bitmap[5];

        public SwapPhaseClass(){
            type = 2;
        }
        public boolean isSwapChange(){
            for(int i = 0; i < 5; i++){
                if(bitmapsOld0[i] != bitmapsNew0[i]){
                    return true;
                }
                if(bitmapsOld1[i] != bitmapsNew1[i]){
                    return true;
                }
            }
            return false;
        }
    }




}
