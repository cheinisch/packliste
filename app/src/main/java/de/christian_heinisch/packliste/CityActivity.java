package de.christian_heinisch.packliste;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

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

        dataSource = new TravelDataSource(this);

        Log.d(LOG_TAG, "Die Datenquelle wird geöffnet.");
        dataSource.open();
        showAllCities();
        Log.d(LOG_TAG, "Die Datenquelle wird geschlossen.");
        dataSource.close();

    }


    public void showAllCities() {

        ArrayList<Travel> arrayOfCities = null;
        dataSource.open();
        arrayOfCities = dataSource.getContent();
        dataSource.close();

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

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                // TODO Auto-generated method stub
                Travel listitem = (Travel) parent.getAdapter().getItem(position);

                Bundle args = new Bundle();
                args.putLong("id", listitem.getId());
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                // Create and show the dialog.
                DialogCityFragment newFragment = new DialogCityFragment();
                newFragment.setArguments(args);
                newFragment.show(ft, "dialog");


                return true;
            }
        });

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
