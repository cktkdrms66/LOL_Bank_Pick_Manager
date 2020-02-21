package com.example.lol_ban_pick_manager;

public class MatchDBCtrct {

    private MatchDBCtrct(){};

    public static final String TBL_MATCH = "MATCH_T";
    public static final String MATCH_ID = "ID";
    public static final String MATCH_NAME = "NAME";
    public static final String MATCH_ISTEAM0BLUE = "ISTEAM0BLUE";
    public static final String MATCH_TEAM0 = "TEAM0";
    public static final String MATCH_TEAM1 = "TEAM1";
    public static final String MATCH_GAMENUMBER = "GAMENUMBER";
    public static final String MATCH_GAME0 = "GAME0";
    public static final String MATCH_GAME1 = "GAME1";
    public static final String MATCH_GAME2 = "GAME2";
    public static final String MATCH_GAME3 = "GAME3";
    public static final String MATCH_GAME4 = "GAME4";
    public static final String MATCH_GAME5 = "GAME5";
    public static final String MATCH_GAME6 = "GAME6";
    public static final String MATCH_GAME7 = "GAME7";
    public static final String MATCH_GAME8 = "GAME8";
    public static final String MATCH_GAME9 = "GAME9";

    public static final String SQL_CREATE_TBL = "CREATE TABLE IF NOT EXISTS " + TBL_MATCH + " " +
            "(" +
            MATCH_ID +        " INTEGER NOT NULL" +   ", " +
            MATCH_NAME +      " TEXT"             +   ", " +
            MATCH_ISTEAM0BLUE +     " INTEGER"             +   ", " +
            MATCH_TEAM0 +    " INTEGER"          + "," +
            MATCH_TEAM1 +    " INTEGER"          + "," +
            MATCH_GAMENUMBER +    " INTEGER"          + "," +
            MATCH_GAME0 +    " TEXT"          + "," +
            MATCH_GAME1 +    " TEXT"          + "," +
            MATCH_GAME2 +    " TEXT"          + "," +
            MATCH_GAME3 +    " TEXT"          + "," +
            MATCH_GAME4 +    " TEXT"          + "," +
            MATCH_GAME5 +    " TEXT"          + "," +
            MATCH_GAME6 +    " TEXT"          + "," +
            MATCH_GAME7 +    " TEXT"          + "," +
            MATCH_GAME8 +    " TEXT"          + "," +
            MATCH_GAME9 +    " TEXT"          +
            ")" ;

    // DROP TABLE IF EXISTS CONTACT_T
    public static final String SQL_DROP_TBL = "DROP TABLE IF EXISTS " + TBL_MATCH ;

    // SELECT * FROM CONTACT_T
    public static final String SQL_SELECT = "SELECT * FROM " + TBL_MATCH ;

    public static final String SQL_CHECK = "SELECT ID FROM " + TBL_MATCH + " WHERE " + MATCH_ID + " = 1";
    // INSERT OR REPLACE INTO CONTACT_T (NO, NAME, PHONE, OVER20) VALUES (x, x, x, x)
    public static final String SQL_INSERT = "INSERT OR REPLACE INTO " + TBL_MATCH + " " +
            "(" + MATCH_ID + ", " + MATCH_NAME + ", " + MATCH_ISTEAM0BLUE + ", " + MATCH_TEAM0
            + ", " + MATCH_TEAM1+ ", " + MATCH_GAMENUMBER+ ", " + MATCH_GAME0+ ", " + MATCH_GAME1+ ", "
            + MATCH_GAME2+ ", " + MATCH_GAME3 + ", " + MATCH_GAME4+ ", " + MATCH_GAME5
            + ", " + MATCH_GAME6+ ", " + MATCH_GAME7+ ", " + MATCH_GAME8+ ", " + MATCH_GAME9 +") VALUES " ;

    // DELETE FROM CONTACT_T
    public static final String SQL_DELETE = "DELETE FROM " + TBL_MATCH ;
}
