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

    
    public static void saveMatch(Match match){
        int index = ApplicationClass.totalMatchNum++;
        String string = "Match" + index;
        System.out.println("save " + string);
        Paper.book().write(string, match);
        Paper.book().write("totalMatchNum", index);
    }
    public static void saveReMatch(Match match){
        for(int i = 1; i < matches.size(); i++){
            if(matches.get(i) == match){
                String string = "Match" + (i-1);
                System.out.println("resave " + string);
                Paper.book().write(string, match);
                break;
            }
        }
    }
    public static void removeMatch(int matchIndex){
        matches.get(matchIndex).team0.using--;
        saveReTeam(matches.get(matchIndex).team0);
        if(matches.get(matchIndex).team0 != matches.get(matchIndex).team1){
            matches.get(matchIndex).team1.using--;
            saveReTeam(matches.get(matchIndex).team1);
        }
        matches.remove(matchIndex);
        System.out.println("remove Match" + (matchIndex-1));
        Paper.book().delete("Match" + (matchIndex-1));
        for(int i = 1; i < matches.size(); i++){
            String string = "Match" + (i-1);
            System.out.println("save " + string);
            Paper.book().write(string, matches.get(i));
        }
        totalMatchNum--;
        Paper.book().write("totalMatchNum", totalMatchNum);

    }
    public static void saveTeam(Team team){
        int index = ApplicationClass.totalTeamNum++;
        System.out.println("save Team" + index);
        Paper.book().write("Team" + index, team);
        Paper.book().write("totalTeamNum", index);
    }
    public static void saveReTeam(Team team){
        for(int i = 1; i < teams.size(); i++){
            if(teams.get(i) == team){
                String string = "Team" + (i-1);
                System.out.println("resave " + string);
                Paper.book().write(string, team);
                break;
            }
        }
    }
    public static void removeTeam(int teamIndex){
        System.out.println("removeTeam");
        ArrayList<Team.Player> dummyPlayers = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            if(teams.get(teamIndex).players[i].type == 1){
                continue;
            }
            boolean isSame = false;
            for(int j = 0; j < dummyPlayers.size(); j++){
                if(dummyPlayers.get(j) == teams.get(teamIndex).players[j]){
                    isSame =true;
                    break;
                }
            }
            if(isSame){
                continue;
            }
            teams.get(teamIndex).players[i].using--;
            System.out.println("resave Player " + teams.get(teamIndex).players[i].name);
            dummyPlayers.add(teams.get(teamIndex).players[i]);
            saveRePlayer(teams.get(teamIndex).players[i]);
        }
        teams.remove(teamIndex);
        Paper.book().delete("Team" + (teamIndex-1));
        System.out.println("remove Team" + (teamIndex-1));
        for(int i = 1; i < teams.size(); i++){
            String string = "Team" + (i-1);
            System.out.println("resave " + string);
            Paper.book().write(string, teams.get(i));
        }
        totalTeamNum--;
        Paper.book().write("totalTeamNum", totalTeamNum);
    }
    
    public static void savePlayer(Team.Player player){
        int index = ApplicationClass.totalPlayerNum++;
        System.out.println("save Player" + index);
        Paper.book().write("Player" + index, player);
        Paper.book().write("totalTeamNum", index);
    }
    public static void saveRePlayer(Team.Player player){
        for(int i = 1; i < players.size(); i++){
            if(players.get(i) == player){
                String string = "Player" + (i-1);
                System.out.println("write " + string);
                Paper.book().write(string, player);
                break;
            }
        }
    }
    public static void removePlayer(int PlayerIndex){
        System.out.println("removePlayer");
        players.remove(PlayerIndex);
        Paper.book().delete("Player" + (PlayerIndex-1));
        System.out.println("remove Player"+(PlayerIndex-1));
        for(int i = 1; i < players.size(); i++){
            String string = "Player" + (i-1);
            System.out.println("write " + string);
            Paper.book().write(string, players.get(i));
        }
        totalPlayerNum--;
        Paper.book().write("totalPlayerNum", totalPlayerNum);
    }

    public static void loadData(){

        String first = Paper.book().read("first");
        if(first == null){
            System.out.println("first");
            totalMatchNum = 0;
            totalPlayerNum = 0;
            totalTeamNum = 0;
            Paper.book().write("totalPlayerNum", totalPlayerNum);
            Paper.book().write("totalMatchNum", totalMatchNum);
            Paper.book().write("totalTeamNum", totalTeamNum);
            Paper.book().write("first", "not first");
        }else{
            System.out.println("not first");
            totalPlayerNum = Paper.book().read("totalPlayerNum");
            totalTeamNum = Paper.book().read("totalTeamNum");
            totalMatchNum = Paper.book().read("totalMatchNum");
            loadPlayer();
            loadTeam();
            loadMatch();
        }
    }
    public static void loadMatch(){
        for(int i = 0; i < totalMatchNum; i++){
            Match match = Paper.book().read("Match" + i);
            matches.add(match);
        }
    }
    public static void loadTeam(){
        for(int i = 0; i < totalTeamNum; i++){
            Team team = Paper.book().read("Team" + i);
            teams.add(team);
        }
    }
    public static void loadPlayer(){
        for(int i = 0; i < totalPlayerNum; i++){
            Team.Player player = Paper.book().read("Player" + i);
            players.add(player);
        }
    }






    static void showToast(Context context, String text){
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    static void addGame(Match match, Match.Game game){
        match.games.add(game);
        match.gameNum++;
        saveReMatch(match);
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
