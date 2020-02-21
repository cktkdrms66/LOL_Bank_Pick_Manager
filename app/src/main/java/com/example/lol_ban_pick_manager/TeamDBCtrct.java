package com.example.lol_ban_pick_manager;

public class TeamDBCtrct {
    
    private TeamDBCtrct(){};

    public static final String TBL_TEAM = "TEAM_T";
    public static final String TEAM_ID = "ID";
    public static final String TEAM_NAME = "NAME";
    public static final String TEAM_LOGO = "LOGO";
    public static final String TEAM_PLAYER0 = "PLAYER0";
    public static final String TEAM_PLAYER1 = "PLAYER1";
    public static final String TEAM_PLAYER2 = "PLAYER2";
    public static final String TEAM_PLAYER3 = "PLAYER3";
    public static final String TEAM_PLAYER4 = "PLAYER4";


    public static final String SQL_CREATE_TBL = "CREATE TABLE IF NOT EXISTS " + TBL_TEAM + " " +
            "(" +
            TEAM_ID +        " INTEGER NOT NULL" +   ", " +
            TEAM_NAME +      " TEXT"             +   ", " +
            TEAM_LOGO +     " INTEGER"             +   ", " +
            TEAM_PLAYER0 +    " INTEGER"          + "," +
            TEAM_PLAYER1 +    " INTEGER"          + "," +
            TEAM_PLAYER2 +    " INTEGER"          + "," +
            TEAM_PLAYER3 +    " INTEGER"          + "," +
            TEAM_PLAYER4 +    " INTEGER"           +
            ")" ;

    // DROP TABLE IF EXISTS CONTACT_T
    public static final String SQL_DROP_TBL = "DROP TABLE IF EXISTS " + TBL_TEAM ;

    // SELECT * FROM CONTACT_T
    public static final String SQL_SELECT = "SELECT * FROM " + TBL_TEAM ;

    // INSERT OR REPLACE INTO CONTACT_T (NO, NAME, PHONE, OVER20) VALUES (x, x, x, x)
    public static final String SQL_INSERT = "INSERT OR REPLACE INTO " + TBL_TEAM + " " +
            "(" + TEAM_ID + ", " + TEAM_NAME + ", " + TEAM_LOGO + ", " + TEAM_PLAYER0
            + ", " + TEAM_PLAYER1+ ", " + TEAM_PLAYER2+ ", " + TEAM_PLAYER3 + ", " + TEAM_PLAYER4 +") VALUES " ;

    // DELETE FROM CONTACT_T
    public static final String SQL_DELETE = "DELETE FROM " + TBL_TEAM ;
}
