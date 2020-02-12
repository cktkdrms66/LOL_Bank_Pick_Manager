package com.example.lol_ban_pick_manager;

import android.graphics.Color;

import java.util.ArrayList;

public class Team {

    String name;
    int logo;
    Player[] players = new Player[5];
    int type;


    public static class Player{
        String name;
        String tear;
        ArrayList<Champion> most = new ArrayList<>();

        public Player(int i){
            if(i == 0){
                name = " ";
                tear = " ";
            }
            else if(i == 1){
                name = "이름없는 소환사";
                tear = "UN";
            }
        }
        public Player(String name, String tear, ArrayList<Champion> most){
            this.name = name;
            this.tear = tear;
            this.most = most;
        }
    }

    Team(int i){
        if(i == 0){
            name = " ";
            logo = R.drawable.nothing;
            type = i;
        }
        else if(i == 1){
            name = "기본팀";
            logo = R.drawable.nothing;
            type = i;
        }
        for(int j = 0; j < 5; j++){
            players[j] = new Player(i);
        }
    }
    Team(String name, int logo, Player[] players){
        this.type = 2;
        this.name = name;
        this.logo = logo;
        this.players = players;
    }

    public static ArrayList<Champion> makeMost(String... championNames){
        ArrayList<Champion> champions = new ArrayList<>();
        for(int i = 0; i < championNames.length; i++){
            System.out.println(championNames[i]);
            champions.add(Champion.getChampion(championNames[i]));
        }
        return champions;
    }
    public static Player makePlayer(String name, String tear, String... champions){
        return new Player(name, tear, makeMost(champions));
    }
    public static Player[] makePlayers(Player player0, Player player1, Player player2, Player player3, Player player4){
        Player[] players = new Player[5];
        players[0] = player0;
        players[1] = player1;
        players[2] = player2;
        players[3] = player3;
        players[4] = player4;
        return players;
    }


    public static Team makePlus(){
        return new Team(0);
    }
    public static Team makeDefaultTeam(){
        ApplicationClass.totalTeamNum++;
        return new Team(1);
    }
    public static Team makeTeam(String name, int logo, Player[] players){
        ApplicationClass.totalTeamNum++;
        return new Team(name, logo, players);
    }



    public static int tear_color(String tear) {
        if (tear.equals("C")) {
            return 0xFFFBBD66;
        } else if (tear.equals("GM")) {
            return 0xFFDF4D4D;
        } else if (tear.equals("M")) {
            return 0xFF7E50A1;
        } else if (tear.charAt(0) == 'D') {
            return 0xFF03A9F4;
        } else if (tear.charAt(0) == 'P') {
            return 0xFF22D8A6;
        } else if (tear.charAt(0) == 'G') {
            return 0xFFFFB503;
        } else if (tear.charAt(0) == 'S') {
            return 0xFFABB5B8;
        } else if (tear.charAt(0) == 'B') {
            return 0xFF8E4B2D;
        } else if (tear.charAt(0) == 'I' || tear.equals("UN")) {
            return 0xFF5F5757;
        } else{
            return Color.TRANSPARENT;
        }
    }

}
