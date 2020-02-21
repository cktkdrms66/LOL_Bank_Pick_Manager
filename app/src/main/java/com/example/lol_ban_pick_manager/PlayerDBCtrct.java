package com.example.lol_ban_pick_manager;

public class PlayerDBCtrct {

    public static final String TBL_PLAYER = "PLAYER_T";
    public static final String PLAYER_ID = "ID";
    public static final String PLAYER_NAME = "NAME";
    public static final String PLAYER_TEAR = "TEAR";
    public static final String PLAYER_CHAMPNUM = "CHAMPNUM";
    public static final String PLAYER_CHAMP0 = "CHAMP0";
    public static final String PLAYER_CHAMP1 = "CHAMP1";
    public static final String PLAYER_CHAMP2 = "CHAMP2";
    public static final String PLAYER_CHAMP3 = "CHAMP3";
    public static final String PLAYER_CHAMP4 = "CHAMP4";
    public static final String PLAYER_CHAMP5 = "CHAMP5";
    public static final String PLAYER_CHAMP6 = "CHAMP6";
    public static final String PLAYER_CHAMP7 = "CHAMP7";
    public static final String PLAYER_CHAMP8 = "CHAMP8";
    public static final String PLAYER_CHAMP9 = "CHAMP9";

    public static final String SQL_CREATE_TBL = "CREATE TABLE IF NOT EXISTS " + TBL_PLAYER + " " +
            "(" +
            PLAYER_ID +        " INTEGER NOT NULL" +   ", " +
            PLAYER_NAME +      " TEXT"             +   ", " +
            PLAYER_TEAR +     " TEXT"             +   ", " +
            PLAYER_CHAMPNUM +    " INTEGER"          + "," +
            PLAYER_CHAMP0 +    " INTEGER"          + "," +
            PLAYER_CHAMP1 +    " INTEGER"          + "," +
            PLAYER_CHAMP2 +    " INTEGER"          + "," +
            PLAYER_CHAMP3 +    " INTEGER"          + "," +
            PLAYER_CHAMP4 +    " INTEGER"          + "," +
            PLAYER_CHAMP5 +    " INTEGER"          + "," +
            PLAYER_CHAMP6 +    " INTEGER"          + "," +
            PLAYER_CHAMP7 +    " INTEGER"          + "," +
            PLAYER_CHAMP8 +    " INTEGER"          + "," +
            PLAYER_CHAMP9 +    " INTEGER"          +
            ")" ;

    // DROP TABLE IF EXISTS CONTACT_T
    public static final String SQL_DROP_TBL = "DROP TABLE IF EXISTS " + TBL_PLAYER ;

    // SELECT * FROM CONTACT_T
    public static final String SQL_SELECT = "SELECT * FROM " + TBL_PLAYER ;

    // INSERT OR REPLACE INTO CONTACT_T (NO, NAME, PHONE, OVER20) VALUES (x, x, x, x)
    public static final String SQL_INSERT = "INSERT OR REPLACE INTO " + TBL_PLAYER + " " +
            "(" + PLAYER_ID + ", " + PLAYER_NAME + ", " + PLAYER_TEAR + ", " + PLAYER_CHAMPNUM
            + ", " + PLAYER_CHAMP0+ ", "+ ", " + PLAYER_CHAMP1+ ", "+ ", " + PLAYER_CHAMP2+ ", "
            + ", " + PLAYER_CHAMP3+ ", "+ ", " + PLAYER_CHAMP4+ ", "+ ", " + PLAYER_CHAMP5+ ", "
            + ", " + PLAYER_CHAMP6+ ", "+ ", " + PLAYER_CHAMP7+ ", "+ ", " + PLAYER_CHAMP8+ ", "
            + ", " + PLAYER_CHAMP9+ ", "+") VALUES " ;

    // DELETE FROM CONTACT_T
    public static final String SQL_DELETE = "DELETE FROM " + TBL_PLAYER ;

}
