package com.example.lol_ban_pick_manager;

import android.graphics.Color;

import java.util.ArrayList;

public class Team {

    String name;
    int logo;
    int type;
    Player[] players = new Player[5];


    public static class Player{
        int type;
        String name;
        String tear;
        int champNum;
        ArrayList<Champion> most = new ArrayList<>();//최대 10개까지

        public Player(int i){
            if(i == 0){
                type = 0;
                name = " ";
                tear = " ";
            }
            else if(i == 1){
                type = 1;
                name = "이름없는 소환사";
                tear = "UN";
            }
        }

        public static Player makePlus(){
            Player player = new Player(0);
            ApplicationClass.players.add(player);
            return player;
        }
        public static Player makeDefaultPlayer(){
            Player player = new Player(1);
            ApplicationClass.players.add(player);
            return player;
        }
        public Player(String name, String tear, ArrayList<Champion> most){
            type = 2;
            this.champNum = most.size();
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
            champions.add(Champion.getChampion(championNames[i]));
        }
        return champions;
    }
    public static Player makePlayer(String name, String tear, ArrayList<Champion> champions){
        Player player = new Player(name, tear, champions);
        ApplicationClass.players.add(player);
        ApplicationClass.savePlayer(player);
        return player;
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
        Team team = new Team(0);
        ApplicationClass.teams.add(team);
        return team;
    }
    public static Team makeDefaultTeam(){
        Team team = new Team(1);
        ApplicationClass.teams.add(team);
        return team;
    }

    public static Team makeTeam(String name, int logo, Player[] players){
        Team team = new Team(name, logo, players);
        ApplicationClass.teams.add(team);
        ApplicationClass.saveTeam(team);
        return team;
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
