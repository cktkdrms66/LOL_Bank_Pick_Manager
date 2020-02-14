package com.example.lol_ban_pick_manager;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import java.util.ArrayList;

public class Match {


    String name;
    ArrayList<Game> games;

    public static class Game{
        String name;
        String text;
        ArrayList<GameElement> gameElements;

        public Game(){
            gameElements = new ArrayList<>();
        }
        public Game(String name, String text){
            this.name = name;
            this.text = text;
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
        int type;//0 == 밴 ,  1 == 픽, 2 == 스왑페이즈, 3 == 스왑
    }
    public static class PickClass extends  GameElement{

        int index;
        int championIndex = -1;
        boolean isOurTeam;
        boolean isFirst = true;
        PickClass equalPhase = null;
        Champion champion = null;

        public PickClass(int type, boolean isOurTeam, int index){
            this.type = type;
            this.index = index;
            this.isOurTeam = isOurTeam;
        }
        public PickClass(int type, boolean isOurTeam, int index, PickClass equalPhase){
            this.type = type;
            this.index = index;
            this.isOurTeam = isOurTeam;
            this.equalPhase = equalPhase;
        }

        public void resetChampionIndex(){
            championIndex = -1;
        }
        public void setChampionIndex(int championIndex){
            this.championIndex = championIndex;
        }
        public void setEqualPhase(PickClass pickClass){
            this.equalPhase = pickClass;
        }
        public void setChampion() {
            this.champion = Champion.getChampion(championIndex);
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



    public Match(String name){
        this.name = name;
        games = new ArrayList<>();
    }
    public static Match makeMatch(String name){
        return new Match(name);
    }

}
