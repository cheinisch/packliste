package de.christian_heinisch.packliste.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chris on 02.01.2017.
 */

public class StuffDataSource {

    private static final String LOG_TAG = StuffDataSource.class.getSimpleName();

    private SQLiteDatabase database;
    private TravelDbHelper dbHelper;

    private String[] columns = {
            TravelDbHelper.COLUMN_ID,
            TravelDbHelper.COLUMN_STUFF,
            TravelDbHelper.COLUMN_STUFF_QUANTITY,
            TravelDbHelper.COLUMN_STUFF_CHECKED,
            TravelDbHelper.COLUMN_STUFF_BUY,
            TravelDbHelper.COLUMN_CITY_ID
    };


    public StuffDataSource(Context context) {
        Log.d(LOG_TAG, "Unsere DataSource erzeugt jetzt den dbHelper.");
        dbHelper = new TravelDbHelper(context);
    }

    public void open() {
        Log.d(LOG_TAG, "Eine Referenz auf die Datenbank wird jetzt angefragt.");
        database = dbHelper.getWritableDatabase();
        Log.d(LOG_TAG, "Datenbank-Referenz erhalten. Pfad zur Datenbank: " + database.getPath());
    }

    public void close() {
        dbHelper.close();
        Log.d(LOG_TAG, "Datenbank mit Hilfe des DbHelpers geschlossen.");
    }

    public Stuff createStuff(String stuffname, String checked, String buy, int quantitiy, int cityid) {
        ContentValues values = new ContentValues();
        values.put(TravelDbHelper.COLUMN_STUFF, stuffname);
        values.put(TravelDbHelper.COLUMN_STUFF_QUANTITY, quantitiy);
        values.put(TravelDbHelper.COLUMN_STUFF_CHECKED, checked);
        values.put(TravelDbHelper.COLUMN_STUFF_BUY, buy);
        values.put(TravelDbHelper.COLUMN_CITY_ID, cityid);

        long insertId = database.insert(TravelDbHelper.TABLE_STUFF_LIST, null, values);

        Cursor cursor = database.query(TravelDbHelper.TABLE_STUFF_LIST,
                columns, TravelDbHelper.COLUMN_ID + "=" + insertId,
                null, null, null, null);

        cursor.moveToFirst();
        Stuff stuff = cursorToStuff(cursor);
        cursor.close();

        return stuff;
    }

    public void deleteStuffCity(long id) {
        database.delete(TravelDbHelper.TABLE_STUFF_LIST,
                TravelDbHelper.COLUMN_CITY_ID + "=" + id,
                null);

        Log.d(LOG_TAG, "Eintrag gelöscht! ID: " + id);
    }

    public Stuff updateStuff(long id, String stuffname, String checked, String buy, int quantitiy, int cityid) {

        ContentValues values = new ContentValues();
        values.put(TravelDbHelper.COLUMN_STUFF, stuffname);
        values.put(TravelDbHelper.COLUMN_STUFF_QUANTITY, quantitiy);
        values.put(TravelDbHelper.COLUMN_STUFF_CHECKED, checked);
        values.put(TravelDbHelper.COLUMN_CITY_ID, cityid);
        values.put(TravelDbHelper.COLUMN_STUFF_BUY, buy);

        database.update(TravelDbHelper.TABLE_STUFF_LIST,
                values,
                TravelDbHelper.COLUMN_ID + "=" + id,
                null);

        Cursor cursor = database.query(TravelDbHelper.TABLE_STUFF_LIST,
                columns, TravelDbHelper.COLUMN_ID + "=" + id,
                null, null, null, null);

        cursor.moveToFirst();
        Stuff stuff = cursorToStuff(cursor);
        cursor.close();

        return stuff;
    }


    private Stuff cursorToStuff(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(TravelDbHelper.COLUMN_ID);
        int idStuff = cursor.getColumnIndex(TravelDbHelper.COLUMN_STUFF);
        int idStuffQuantity = cursor.getColumnIndex(TravelDbHelper.COLUMN_STUFF_QUANTITY);
        int idStuffChecked = cursor.getColumnIndex(TravelDbHelper.COLUMN_STUFF_CHECKED);
        int idStuffBuy = cursor.getColumnIndex(TravelDbHelper.COLUMN_STUFF_BUY);
        int idCityid = cursor.getColumnIndex(TravelDbHelper.COLUMN_CITY_ID);

        String stuffname = cursor.getString(idStuff);
        int quantitiy = cursor.getInt(idStuffQuantity);
        int cityid = cursor.getInt(idCityid);
        String checked = cursor.getString(idStuffChecked);
        String buy = cursor.getString(idStuffBuy);
        long id = cursor.getLong(idIndex);

        Stuff stuff = new Stuff(stuffname, checked, buy, quantitiy, cityid, id);

        return stuff;
    }

    public List<Stuff> getAllStuffbuyed(long cityid) {
        List<Stuff> stuffList = new ArrayList<>();

        Cursor cursor = database.query(TravelDbHelper.TABLE_STUFF_LIST,
                columns, "cityid = " + cityid + " and buy = ?",new String[]{"false"}, null, null, null);

        cursor.moveToFirst();
        Stuff stuff;

        while (!cursor.isAfterLast()) {
            stuff = cursorToStuff(cursor);
            stuffList.add(stuff);
            Log.d(LOG_TAG, "ID: " + stuff.getId() + ", Inhalt: " + stuff.toString());
            cursor.moveToNext();
        }

        cursor.close();

        return stuffList;
    }
    public List<Stuff> getAllStuffBuy(long cityid) {
        List<Stuff> stuffList = new ArrayList<>();

        Cursor cursor = database.query(TravelDbHelper.TABLE_STUFF_LIST,
                columns, "cityid = " + cityid + " and buy = ?",new String[]{"true"}, null, null, null);

        cursor.moveToFirst();
        Stuff stuff;

        while (!cursor.isAfterLast()) {
            stuff = cursorToStuff(cursor);
            stuffList.add(stuff);
            Log.d(LOG_TAG, "ID: " + stuff.getId() + ", Inhalt: " + stuff.toString());
            cursor.moveToNext();
        }

        cursor.close();

        return stuffList;
    }

}