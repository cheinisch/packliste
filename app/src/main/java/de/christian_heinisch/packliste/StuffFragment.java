package de.christian_heinisch.packliste;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.christian_heinisch.packliste.database.TravelDataSource;

import static de.christian_heinisch.packliste.AddNewActivityFragment.LOG_TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class StuffFragment extends Fragment {


    public View rootview;
    private TravelDataSource dataSource;

    public StuffFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_stuff, container, false);

        long id = 4;

        getTravelData(id);

        getStuff(id);

        getStuffBuy(id);



        return rootview;
    }

    public void getStuff(long cityid){

    }

    public void getStuffBuy(long cityid){

    }

    public void getTravelData(long id){

        dataSource = new TravelDataSource(getContext());

        Log.d(LOG_TAG, "Die Datenquelle wird geöffnet.");
        dataSource.open();



        /*Travel city = dataSource.createTravel("London", 2, 5);
        Log.d(LOG_TAG, "Es wurde der folgende Eintrag in die Datenbank geschrieben:");
        Log.d(LOG_TAG, "ID: " + city.getId() + ", Inhalt: " + city.toString());*/

        Log.d(LOG_TAG, "Folgende Einträge sind in der Datenbank vorhanden:");

        String City = dataSource.getTravelCity(id);
        String startDate = dataSource.getStartDate(id);
        String endDate = dataSource.getEndDate(id);

        Log.d(LOG_TAG, "Die Datenquelle wird geschlossen.");
        dataSource.close();

        TextView cityName = (TextView) rootview.findViewById(R.id.textViewCityname);
        cityName.setText(City);

        TextView travelDate = (TextView) rootview.findViewById(R.id.textViewTravelDate);
        travelDate.setText(startDate + " - " + endDate);

    }

}
