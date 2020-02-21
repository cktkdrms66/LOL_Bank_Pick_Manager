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


public class ApplicationClass extends Application {

    static int totalMatchNum = 0;
    static int totalTeamNum = 0;
    static int totalPlayerNum = 0;

    static ArrayList<Team> teams = new ArrayList<>();
    static ArrayList<Champion> champions = new ArrayList<>();
    static ArrayList<Match> matches = new ArrayList<>();
    static ArrayList<Team.Player> players = new ArrayList<>();
    static int playerID = 0;
    static int teamID = 0;
    static int matchID = 0;
    static MatchDBhelper matchDBhelper = null;
    static TeamDBhelper teamDBhelper = null;
    static PlayerDBhelper playerDBhelper = null;
    @Override
    public void onCreate() {
        super.onCreate();

        Champion.championSetting();
        initTables();

        Team.Player.makePlus();
        Team.makePlus();
        Match.makePlus();
        Team.makeDefaultTeam();
        Match.makeDefaultMatch();
        if(checkFirst()){
            settingFirst();
        }else{
            loadValues();
        }

    }


    private void initTables(){
        matchDBhelper = new MatchDBhelper(this);
        teamDBhelper = new TeamDBhelper(this);
        playerDBhelper = new PlayerDBhelper(this);
    }



    //로드 DB에서 꺼내오기

    private void loadValues() {

        SQLiteDatabase db = matchDBhelper.getReadableDatabase() ;
        Cursor cursor = db.rawQuery(MatchDBCtrct.SQL_SELECT, null) ;

        //Match.makePlusMatch();
        //Match.makeDefaultMatch();

        while(cursor.moveToNext()){
            String name = cursor.getString(1);
            boolean isTeam0Blue = (cursor.getInt(2) == 1 ? true : false);
            int team0Index = cursor.getInt(3);
            int team1Index = cursor.getInt(4);
            int gameNumber = cursor.getInt(5);
            ArrayList<Match.Game> games = new ArrayList<>();
            games.add(new Match.Game());
            for(int i = 0; i < gameNumber; i++){
                String stringGame = cursor.getString(6+i);
                games.add(getGame(stringGame));
            }
            Team team0 = getTeam(team0Index);
            Team team1 = getTeam(team1Index);

            Match.makeMatch(name, team0, team1, isTeam0Blue, gameNumber, games);
        }
    }

    private Match.Game getGame(String stringGame){
        // name, teamlogo, star, {id, index, chamindex, isour, isfirst, equalid},{id, index, chamindex, isour, isfirst, equalid},~~
        // {{byte, byte, byte, b, b}, {b,b,b,b,b}, {b,b,b,b,b}, {b,b,b,b,b}}
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonGame = (JsonObject) jsonParser.parse(stringGame);
        JsonArray jsonArray = (JsonArray) jsonGame.get("gameElements");

        String name = jsonGame.get("name").toString();
        int teamLogo = Integer.valueOf(jsonGame.get("teamLogo").toString());
        int star = Integer.valueOf(jsonGame.get("star").toString());

        ArrayList<Match.GameElement> gameElements = new ArrayList<>();
        for(int i = 0; i < 20; i++){
            //gameelements에 각 pickclass넣기
            JsonObject jsonGameElement = (JsonObject)jsonArray.get(i);
            int index = Integer.valueOf(jsonGameElement.get("index").toString());
            int type =  Integer.valueOf(jsonGameElement.get("type").toString());
            int championIndex = Integer.valueOf(jsonGameElement.get("championIndex").toString());
            boolean isOurTeam = (Integer.valueOf(jsonGameElement.get("isOurTeam").toString()) == 1 ? true : false );
            boolean isFirst = (Integer.valueOf(jsonGameElement.get("isFirst").toString()) == 1 ? true : false);

            Match.PickClass pickClass = new Match.PickClass(type, isOurTeam, index);
            pickClass.equalPhase = null;
            pickClass.isFirst = isFirst;
            pickClass.setChampionIndex(championIndex);
            gameElements.add(pickClass);
        }
        for(int i = 0; i < 20; i++){
            //equalphase 설정
            JsonObject jsonGameElement = (JsonObject)jsonArray.get(i);
            int equalChampIndex = Integer.valueOf(jsonGameElement.get("equalChampIndex").toString());
            if(equalChampIndex != -1){
                for(int j = 0; j< 20; j++){
                    if(((Match.PickClass)gameElements.get(j)).championIndex == equalChampIndex){
                        ((Match.PickClass)gameElements.get(i)).equalPhase = (Match.PickClass)gameElements.get(j);
                    }
                }
            }
        }
        JsonObject jsonGameElement = (JsonObject)jsonArray.get(20);
        JsonArray jsonArray0 = (JsonArray)jsonGameElement.get("bit0");
        JsonArray jsonArray1 = (JsonArray)jsonGameElement.get("bit1");
        JsonArray jsonArray2 = (JsonArray)jsonGameElement.get("bit2");
        JsonArray jsonArray3 = (JsonArray)jsonGameElement.get("bit3");
        Match.SwapPhaseClass swapPhaseClass = new Match.SwapPhaseClass();
        for(int i = 0; i < 4; i++){
            swapPhaseClass.bitmapsOld0[i] = getBitmapFromString(jsonArray0.get(i).toString());
            swapPhaseClass.bitmapsOld1[i] = getBitmapFromString(jsonArray1.get(i).toString());
            swapPhaseClass.bitmapsNew0[i] = getBitmapFromString(jsonArray2.get(i).toString());
            swapPhaseClass.bitmapsNew1[i] = getBitmapFromString(jsonArray3.get(i).toString());

        }
        return new Match.Game(name, teamLogo, star, gameElements);
    }
    private Bitmap getBitmapFromString(String stringPicture) {
        byte[] decodedString = Base64.decode(stringPicture, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }



    private Team getTeam(int teamID){
        SQLiteDatabase db = teamDBhelper.getReadableDatabase() ;
        Cursor cursor = db.rawQuery(TeamDBCtrct.SQL_SELECT + " WHERE ID = " + teamID , null) ;
        cursor.moveToFirst();
        String name = cursor.getString(1);
        int logo = cursor.getInt(2);
        int[] intPlayers = new int[5];
        Team.Player[] players = new Team.Player[5];
        for(int i = 0; i < 5; i++){
            intPlayers[i] = cursor.getInt(3 + i);
            players[i] = getPlayer(intPlayers[i]);
        }
        return Team.makeTeam(name, logo, players);
    }
    private Team.Player getPlayer(int playerID){
        SQLiteDatabase db = playerDBhelper.getReadableDatabase() ;
        Cursor cursor = db.rawQuery(PlayerDBCtrct.SQL_SELECT + " WHERE ID = " + playerID , null) ;

        cursor.moveToFirst();

        String name = cursor.getString(1);
        String tear = cursor.getString(2);
        int champNum = cursor.getInt(3);
        ArrayList<Champion> champions = new ArrayList<>();
        for(int i = 0; i < champNum; i++) {
            champions.add(Champion.getChampion(cursor.getInt(4 + i)));
        }
        return Team.makePlayer(name,tear,champions);


    }



    //세이브 DB에 저장하기
    static void savePlayer(Team.Player player){
        SQLiteDatabase db = playerDBhelper.getWritableDatabase();

        String stringChampIndexs = "";
        for(int i = 0; i < player.champNum; i++){
            stringChampIndexs +=  Champion.getChampionIndex(player.most.get(i).name)+",";
        }
        for(int i = player.champNum; i < 10; i++){
            stringChampIndexs += -1 + ",";
        }
        stringChampIndexs = stringChampIndexs.substring(0, stringChampIndexs.length()-1);
        
        String sqlInsert = PlayerDBCtrct.SQL_INSERT +
                " (" +
                player.id + ", " +
                "'" + player.name + "', " +
                "'" + player.tear + "', " +
                player.champNum + ", " +
                stringChampIndexs +
                ")" ;
        System.out.println(sqlInsert);

        db.execSQL(sqlInsert);
    }

    static void saveTeam(Team team){
        SQLiteDatabase db = teamDBhelper.getWritableDatabase();

        String sqlInsert = TeamDBCtrct.SQL_INSERT +
                " (" +
                team.id + ", " +
                "'" + team.name + "', " +
                team.logo + ", " +
                team.players[0].id + ", " +
                team.players[1].id + ", " +
                team.players[2].id + ", " +
                team.players[3].id + ", " +
                team.players[4].id  +
                ")" ;
        System.out.println(sqlInsert);
        
        db.execSQL(sqlInsert);
    }
    static void saveMatch(Match match) {
        SQLiteDatabase db = matchDBhelper.getWritableDatabase() ;

        String stringGames = "";
        for(int i = 0; i < match.gameNum; i++){
            stringGames += "'" + getStringFromGame(match.games.get(i))+"',";
        }
        for(int i = match.gameNum; i < 10; i++){
            stringGames += "'" + -1 + "',";
        }
        stringGames = stringGames.substring(0, stringGames.length()-1);
        String sqlInsert = MatchDBCtrct.SQL_INSERT +
                " (" +
                match.id + ", " +
                "'" + match.name + "', " +
                (match.isTeam0Blue ? 1 : 0) + ", " +
                match.team0.id + ", " +
                match.team1.id + ", " +
                match.gameNum + ", " +
                stringGames +
                ")" ;
        System.out.println(sqlInsert);

        //MATCH
        // id
        // name
        // isTeam0Blue
        // team0 id
        // team1 id
        // gameNum
        // game0
        // ~~~
        // game9

        //GAME
        // name:
        // teamLogo:
        // star:
        // gameElements: [
        //                  {
        //                      type:
        //                      index:
        //                      championIndex:
        //                      isOurTeam:
        //                      isFirst:
        //                      equalChamIndex:
        //                  }
        //                  ~~~~~
        //                  {
        //                      bit0: [1,2,3,4,5]  <- 모두 String
        //                      bit1: [1,2,3,4,5]
        //                      bit2: [1,2,3,4,5]
        //                      bit3: [1,2,3,4,5]
        //                  }
        //               ]
        db.execSQL(sqlInsert) ;
    }
    private static String getStringFromGame(Match.Game game){
        JSONObject data = new JSONObject();
        try{
            data.put("name", game.name);
            data.put("teamLogo", game.victoryTeamLogo);
            data.put("star", game.star);
            data.put("gameElements", getStringFromGameElement(game));
        }catch (JSONException e){
            e.printStackTrace();
        }
        return data.toString();
    }
    private static String getStringFromGameElement(Match.Game game){
        JSONArray array = new JSONArray();

        for(int i = 0; i < 20; i++){
            Match.PickClass nowPick = (Match.PickClass)game.gameElements.get(i);
            int type = nowPick.type;
            int index = nowPick.index;
            int championIndex = nowPick.championIndex;
            int isOurTeam = (nowPick.isOurTeam ? 1 : 0);
            int isFirst = (nowPick.isFirst ? 1 : 0);
            int equalChampIndex = (nowPick.equalPhase != null ? nowPick.equalPhase.championIndex : -1);
            JSONObject data = new JSONObject();
            try {
                data.put("type", type);
                data.put("index", index);
                data.put("championIndex", championIndex);
                data.put("isOurTeam", isOurTeam);
                data.put("isFirst", isFirst);
                data.put("equalChampIndex", equalChampIndex);
                array.put(data);
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        Match.SwapPhaseClass swapPhaseClass = (Match.SwapPhaseClass)game.gameElements.get(20);
        JSONObject data = new JSONObject();
        try{
            data.put("bit0", getStringFromBitMaps(swapPhaseClass.bitmapsOld0));
            data.put("bit1", getStringFromBitMaps(swapPhaseClass.bitmapsOld1));
            data.put("bit2", getStringFromBitMaps(swapPhaseClass.bitmapsNew0));
            data.put("bit3", getStringFromBitMaps(swapPhaseClass.bitmapsNew1));
            array.put(data);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return array.toString();
    }
    private static String getStringFromBitMaps(Bitmap[] bitmaps){
        JSONArray array = new JSONArray();

        for(int i = 0; i < bitmaps.length; i++){
            array.put(getStringFromBitMap(bitmaps[i]));
        }
        return array.toString();
    }

    private static String getStringFromBitMap(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] data = stream.toByteArray();
        String encodedImage = Base64.encodeToString(data, Base64.DEFAULT);
        return encodedImage;
    }







    ///편의 제공


    private static boolean checkFirst(){
        SQLiteDatabase db = matchDBhelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(MatchDBCtrct.SQL_CHECK, null);
        if(cursor.moveToNext()){
            System.out.println("it's not first");
            return false;
        }
        System.out.println("it's first!!!");
        return true;
    }



    static void showToast(Context context, String text){
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    static void addGame(Match match, Match.Game game){
        match.games.add(game);
        match.gameNum++;
        saveMatch(match);
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
        saveTeam(Team.makeDefaultTeam());
//        saveTeam(Team.makeTeam("SKT T1", R.drawable.zyra,
//                Team.makePlayers(players.get(0), players.get(1), players.get(2), players.get(3), players.get(4))));
//        saveTeam(Team.makeTeam("DRX", R.drawable.lucian,
//                Team.makePlayers(players.get(5), players.get(6), players.get(7), players.get(8), players.get(9))));
        saveMatch(Match.makeDefaultMatch());


        System.out.println("total match " + totalMatchNum);
        System.out.println("total team " + totalTeamNum);
    }


}
