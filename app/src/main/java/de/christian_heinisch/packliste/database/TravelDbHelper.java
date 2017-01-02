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


    public TravelDbHelper(Context context) {
        super(context, "TRAVELDATABASE", null, 1);
        Log.d(LOG_TAG, "DbHelper hat die Datenbank: " + getDatabaseName() + " erzeugt.");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}