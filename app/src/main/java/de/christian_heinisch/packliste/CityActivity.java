package de.christian_heinisch.packliste;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import de.christian_heinisch.packliste.database.CityAdapter;
import de.christian_heinisch.packliste.database.Travel;
import de.christian_heinisch.packliste.database.TravelDataSource;

import static de.christian_heinisch.packliste.AddNewActivityFragment.LOG_TAG;

public class CityActivity extends AppCompatActivity {

    private TravelDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CityActivity.this, AddNewActivity.class);
                startActivity(intent);

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Travel testMemo = new Travel("Birnen", 5, 102, 123);
        Log.d(LOG_TAG, "Inhalt der Testmemo: " + testMemo.toString());

        dataSource = new TravelDataSource(this);

        Log.d(LOG_TAG, "Die Datenquelle wird geöffnet.");
        dataSource.open();



        /*Travel city = dataSource.createTravel("London", 2, 5);
        Log.d(LOG_TAG, "Es wurde der folgende Eintrag in die Datenbank geschrieben:");
        Log.d(LOG_TAG, "ID: " + city.getId() + ", Inhalt: " + city.toString());*/

        Log.d(LOG_TAG, "Folgende Einträge sind in der Datenbank vorhanden:");
        //showAllListEntries();
        showAllCities();

        Log.d(LOG_TAG, "Die Datenquelle wird geschlossen.");
        dataSource.close();

    }

    private void showAllListEntries () {
        List<Travel> shoppingMemoList = dataSource.getAllTravels();

        ArrayAdapter<Travel> shoppingMemoArrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_multiple_choice,
                shoppingMemoList);

        ListView shoppingMemosListView = (ListView) this.findViewById(R.id.listview_citys);
        shoppingMemosListView.setAdapter(shoppingMemoArrayAdapter);
    }

    public void showAllCities() {

        ArrayList<Travel> arrayOfCities = null;
        arrayOfCities = getContent();
        CityAdapter adapter = new CityAdapter(this, arrayOfCities);
        ListView listView = (ListView) this.findViewById(R.id.listview_citys);
        listView.setAdapter(adapter);

        //TODO: handle title, description, url, fulltext of listitem to openIten(...)
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Travel listitem = (Travel) parent.getAdapter().getItem(position);
                setCity(listitem.getId());
            }
        });

    }

    public ArrayList<Travel> getContent(){
        ArrayList<Travel> listitems = new ArrayList<Travel>();

        int length = dataSource.getLength();
        System.out.println("Arraylänge: " + length);
        for (int i = 1; i < length+1; i++) {
            long x = i;
            System.out.println("LONG: " + Long.parseLong(dataSource.getStartDate(x)));
            String city = dataSource.getTravelCity(x);
            long startdate = Long.parseLong(dataSource.getStartDate(x));
            long enddate = Long.parseLong(dataSource.getEndDate(x));
            listitems.add(new Travel(city, startdate, enddate, x));
        }
        return listitems;
    }

    private void setCity(long id) {

        SharedPreferences settings = this.getSharedPreferences("Packliste", MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        String idString = String.valueOf(id);

        // Speichert ID der Datenbank
        editor.putString("id", idString);

        editor.commit();


        // MainActivity neu laden
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);

    }

}
