package de.christian_heinisch.packliste.database;

/**
 * Created by chris on 02.01.2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TravelDbHelper extends SQLiteOpenHelper{

    private static final String LOG_TAG = TravelDbHelper.class.getSimpleName();


    public static final String DB_NAME = "packing_list.db";
    public static final int DB_VERSION = 2;

    public static final String TABLE_CITY_LIST = "city_list";
    public static final String TABLE_STUFF_LIST = "stuff_list";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CITY = "city";
    public static final String COLUMN_STARTDATE = "startdate";
    public static final String COLUMN_ENDDATE = "enddate";

    public static final String COLUMN_STUFF = "stuff";
    public static final String COLUMN_STUFF_BUY = "buy";
    public static final String COLUMN_STUFF_CHECKED = "checked";
    public static final String COLUMN_STUFF_QUANTITY = "quantitiy";
    public static final String COLUMN_CITY_ID = "cityid";

    public static final String SQL_CREATE_CITY =
            "CREATE TABLE " + TABLE_CITY_LIST +
                    "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_CITY + " TEXT NOT NULL, " +
                    COLUMN_STARTDATE + " INTEGER NOT NULL, " +
                    COLUMN_ENDDATE + " INTEGER NOT NULL);";


    public static final String SQL_CREATE_STUFF =
            "CREATE TABLE " + TABLE_STUFF_LIST +
                    "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_STUFF + " TEXT NOT NULL, " +
                    COLUMN_STUFF_QUANTITY + " INT NOT NULL, " +
                    COLUMN_STUFF_BUY + " TEXT NOT NULL, " +
                    COLUMN_STUFF_CHECKED + " TEXT NOT NULL, " +
                    COLUMN_CITY_ID + " INT NOT NULL);";


    public TravelDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.d(LOG_TAG, "DbHelper hat die Datenbank: " + getDatabaseName() + " erzeugt.");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Log.d(LOG_TAG, "Die Tabelle Stadt wird mit SQL-Befehl: " + SQL_CREATE_CITY + " angelegt.");
            db.execSQL(SQL_CREATE_CITY);
            Log.d(LOG_TAG, "Die Tabelle Zeug wird mit SQL-Befehl: " + SQL_CREATE_STUFF + " angelegt.");
            db.execSQL(SQL_CREATE_STUFF);
        }
        catch (Exception ex) {
            Log.e(LOG_TAG, "Fehler beim Anlegen der Tabelle: " + ex.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}