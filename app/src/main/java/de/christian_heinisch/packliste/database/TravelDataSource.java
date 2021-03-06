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

public class TravelDataSource {

    private static final String LOG_TAG = TravelDataSource.class.getSimpleName();

    private SQLiteDatabase database;
    private TravelDbHelper dbHelper;

    private String[] columns = {
            TravelDbHelper.COLUMN_ID,
            TravelDbHelper.COLUMN_CITY,
            TravelDbHelper.COLUMN_STARTDATE,
            TravelDbHelper.COLUMN_ENDDATE
    };


    public TravelDataSource(Context context) {
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

    public Travel createTravel(String city, long startdate, long enddate) {
        ContentValues values = new ContentValues();
        values.put(TravelDbHelper.COLUMN_CITY, city);
        values.put(TravelDbHelper.COLUMN_STARTDATE, startdate);
        values.put(TravelDbHelper.COLUMN_ENDDATE, enddate);

        long insertId = database.insert(TravelDbHelper.TABLE_CITY_LIST, null, values);

        Cursor cursor = database.query(TravelDbHelper.TABLE_CITY_LIST,
                columns, TravelDbHelper.COLUMN_ID + "=" + insertId,
                null, null, null, null);

        cursor.moveToFirst();
        Travel travel = cursorToTravel(cursor);
        cursor.close();

        return travel;
    }

    private Travel cursorToTravel(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(TravelDbHelper.COLUMN_ID);
        int idCity = cursor.getColumnIndex(TravelDbHelper.COLUMN_CITY);
        int idStartdate = cursor.getColumnIndex(TravelDbHelper.COLUMN_STARTDATE);
        int idEnddate = cursor.getColumnIndex(TravelDbHelper.COLUMN_ENDDATE);

        String city = cursor.getString(idCity);
        int startdate = cursor.getInt(idStartdate);
        int enddate = cursor.getInt(idEnddate);
        long id = cursor.getLong(idIndex);

        Travel travel = new Travel(city, startdate, enddate, id);

        return travel;
    }

    public Travel updateTravel(long id, String newCity, long newStartdate, long newEnddate) {
        ContentValues values = new ContentValues();
        values.put(TravelDbHelper.COLUMN_CITY, newCity);
        values.put(TravelDbHelper.COLUMN_STARTDATE, newStartdate);
        values.put(TravelDbHelper.COLUMN_ENDDATE, newEnddate);

        database.update(TravelDbHelper.TABLE_CITY_LIST,
                values,
                TravelDbHelper.COLUMN_ID + "=" + id,
                null);

        Cursor cursor = database.query(TravelDbHelper.TABLE_CITY_LIST,
                columns, TravelDbHelper.COLUMN_ID + "=" + id,
                null, null, null, null);

        cursor.moveToFirst();
        Travel travel = cursorToTravel(cursor);
        cursor.close();

        return travel;
    }

    public List<Travel> getAllTravels() {
        List<Travel> travelList = new ArrayList<>();

        Cursor cursor = database.query(TravelDbHelper.TABLE_CITY_LIST,
                columns, null, null, null, null, null);

        cursor.moveToFirst();
        Travel travel;

        while (!cursor.isAfterLast()) {
            travel = cursorToTravel(cursor);
            travelList.add(travel);
            Log.d(LOG_TAG, "ID: " + travel.getId() + ", Inhalt: " + travel.toString());
            cursor.moveToNext();
        }

        cursor.close();

        return travelList;
    }

    public ArrayList<Travel> getContent(){
        ArrayList<Travel> listitems = new ArrayList<Travel>();

        Cursor cursor = database.query(TravelDbHelper.TABLE_CITY_LIST,
                columns, null, null, null, null, null);

        cursor.moveToFirst();
        Travel travel;

        while (!cursor.isAfterLast()) {
            travel = cursorToTravel(cursor);
            System.out.println("time:" + travel.getStartdate());
            listitems.add(new Travel(travel.getCity(), getStartDateFix(travel.getId()), getEndDateFix(travel.getId()), travel.getId()));
            cursor.moveToNext();
        }

        cursor.close();

        return listitems;
    }

    public Long getFirstTravel() {
        //List<Travel> travelList = new ArrayList<>();

        long id;

        Cursor cursor = database.query(TravelDbHelper.TABLE_CITY_LIST,
                columns, null, null, null, null, null);

        cursor.moveToFirst();

        Travel travel;
        travel = cursorToTravel(cursor);
        id = travel.getId();
/*
        for (!cursor.isAfterLast()) {
            travel = cursorToTravel(cursor);
            travelList.add(travel);
            Log.d(LOG_TAG, "ID: " + travel.getId() + ", Inhalt: " + travel.toString());
            cursor.moveToNext();
        }
*/
        cursor.close();

        return id;
    }

    public void deleteTravel(long id) {
        database.delete(TravelDbHelper.TABLE_CITY_LIST,
                TravelDbHelper.COLUMN_ID + "=" + id,
                null);

        Log.d(LOG_TAG, "Eintrag gelöscht! ID: " + id);
    }


    public int getLength(){

        Cursor cursor = database.query(TravelDbHelper.TABLE_CITY_LIST,
                columns, null, null, null, null, null);

        cursor.moveToFirst();
        int x = 0;

        while (!cursor.isAfterLast()) {
            x = x + 1;
            cursor.moveToNext();
        }

        cursor.close();

        return x;

    }

    public String getTravelCity(Long id) {

        String cityName = "";
        Cursor cursor = database.query(TravelDbHelper.TABLE_CITY_LIST, columns,
                "_id = " + id, null, null, null, null);

        cursor.moveToFirst();

        if (cursor.moveToFirst()) {
            cityName= cursor.getString(cursor.getColumnIndex("city"));
        }

        cursor.close();

        return cityName;
    }


    public String getStartDate(Long id) {

        String cityName = "";
        Cursor cursor = database.query(TravelDbHelper.TABLE_CITY_LIST, columns,
                "_id = " + id, null, null, null, null);

        cursor.moveToFirst();

        if (cursor.moveToFirst()) {
            cityName= cursor.getString(cursor.getColumnIndex("startdate"));
        }

        cursor.close();

        return cityName;
    }



    public String getEndDate(Long id) {

        String cityName = "";
        Cursor cursor = database.query(TravelDbHelper.TABLE_CITY_LIST, columns,
                "_id = " + id, null, null, null, null);

        cursor.moveToFirst();

        if (cursor.moveToFirst()) {
            cityName= cursor.getString(cursor.getColumnIndex("enddate"));
        }

        cursor.close();

        return cityName;
    }
    /*
     * FIX FÜR DEN DB BUG BEIM DATUMAUSLESEN
     */
    public long getStartDateFix(Long id) {

        long date = 0;
        Cursor cursor = database.query(TravelDbHelper.TABLE_CITY_LIST, columns,
                "_id = " + id, null, null, null, null);

        cursor.moveToFirst();

        if (cursor.moveToFirst()) {
            date = Long.parseLong(cursor.getString(cursor.getColumnIndex("startdate")));
        }

        cursor.close();

        return date;
    }

    public long getEndDateFix(Long id) {

        long date = 0;
        Cursor cursor = database.query(TravelDbHelper.TABLE_CITY_LIST, columns,
                "_id = " + id, null, null, null, null);

        cursor.moveToFirst();

        if (cursor.moveToFirst()) {
            date = Long.parseLong(cursor.getString(cursor.getColumnIndex("enddate")));
        }

        cursor.close();

        return date;
    }
}