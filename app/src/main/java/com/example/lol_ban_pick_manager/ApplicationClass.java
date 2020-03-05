package com.example.lol_ban_pick_manager;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.TreeSet;

import io.paperdb.Paper;


public class ApplicationClass extends Application {

    static int totalMatchNum = 0;
    static int totalTeamNum = 0;
    static int totalPlayerNum = 0;


    static ArrayList<Champion> champions = new ArrayList<>();
    static ArrayList<Team> teams = new ArrayList<>();
    static ArrayList<Match> matches = new ArrayList<>();
    static ArrayList<Team.Player> players = new ArrayList<>();

    static boolean isNeedToSetting = true;

    static DataReadWrite task;

    @Override
    public void onCreate() {
        super.onCreate();
        isNeedToSetting = true;
    }

    public static Bitmap StringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage(); return null;
        }
    }
    public static String BitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, baos);
        byte[] bytes = baos.toByteArray();
        String temp = Base64.encodeToString(bytes, Base64.DEFAULT);
        return temp;
    }


    public static void saveMatch(int matchIndex){
        task = new DataReadWrite();
        task.execute(matchIndex, 0);
    }
    public static void saveReMatch(int matchIndex){
        task = new DataReadWrite();
        task.execute(matchIndex, 1);
    }
    public static void removeMatch(int matchIndex){
        task = new DataReadWrite();
        task.execute(matchIndex, 2);
    }




    public static void saveTeam(int teamIndex){
        task = new DataReadWrite();
        task.execute(teamIndex, 3);
    }
    public static void saveReTeam(Team team){
        for(int i = 0; i < ApplicationClass.teams.size(); i++){
            if(team == ApplicationClass.teams.get(i)){
                saveReTeam(i);
                return;
            }
        }
    }
    public static void saveReTeam(int teamIndex){
        task = new DataReadWrite();
        task.execute(teamIndex, 4);
    }
    public static void removeTeam(int teamIndex){
        task = new DataReadWrite();
        task.execute(teamIndex, 5);
    }




    public static void savePlayer(int playerIndex){
        task = new DataReadWrite();
        task.execute(playerIndex, 6);
    }
    public static void saveRePlayer(Team.Player player){
        for(int i =0 ; i < players.size(); i++){
            if(player == players.get(i)){
                saveRePlayer(i);
                return;
            }
        }
    }
    public static void saveRePlayer(int playerIndex){
        task = new DataReadWrite();
        task.execute(playerIndex, 7);
    }
    public static void removePlayer(int playerIndex){
        task = new DataReadWrite();
        task.execute(playerIndex, 8);
    }

    public static void loadData(){
        task = new DataReadWrite();
        task.execute(0, 9);
    }

    public static void addGame(int matchIndex, Match.Game game){
        ApplicationClass.matches.get(matchIndex).games.add(game);
        ApplicationClass.matches.get(matchIndex).gameNum++;
        saveReMatch(matchIndex);
    }

    static void showToast(Context context, String text){
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    static void settingFirst(){
//        savePlayer(Team.Player.makeDefaultPlayer());
//        savePlayer(Team.makePlayer("khan", "D3", "갈리오", "나미", "갱플랭크"));
//        savePlayer(Team.makePlayer("Haru", "C", "다리우스", "그레이브즈"));
//        savePlayer(Team.makePlayer("Faker", "GM", "니달리", "니코", "나서스", "노틸러스"));
//        savePlayer(Team.makePlayer("Teddy", "G2", "나르", "나미"));
//        savePlayer(Team.makePlayer("Effort", "M", "갱플랭크"));
//        savePlayer(Team.makePlayer("asdf", "P1", "나미", "나미", "나미", "나서스"));
//        savePlayer(Team.makePlayer("1234", "B1", "나르"));
//        savePlayer(Team.makePlayer("5678", "UN", "갱플랭크", "갈리오", "노틸러스"));
//        savePlayer(Team.makePlayer("qwer", "I3", "나미", "니코", "다리우스"));
//        savePlayer(Team.makePlayer("xxxx", "S1", "니달리"));
//

//        saveTeam(Team.makeTeam("SKT T1", R.drawable.zyra,
//                Team.makePlayers(players.get(0), players.get(1), players.get(2), players.get(3), players.get(4))));
//        saveTeam(Team.makeTeam("DRX", R.drawable.lucian,
//                Team.makePlayers(players.get(5), players.get(6), players.get(7), players.get(8), players.get(9))));



        System.out.println("total match " + totalMatchNum);
        System.out.println("total team " + totalTeamNum);
    }


}
