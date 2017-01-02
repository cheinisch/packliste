package de.christian_heinisch.packliste.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by chris on 02.01.2017.
 */

public class TravelDataSource {

    private static final String LOG_TAG = TravelDataSource.class.getSimpleName();

    private SQLiteDatabase database;
    private TravelDbHelper dbHelper;


    public TravelDataSource(Context context) {
        Log.d(LOG_TAG, "Unsere DataSource erzeugt jetzt den dbHelper.");
        dbHelper = new TravelDbHelper(context);
    }
}
