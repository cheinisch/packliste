package de.christian_heinisch.packliste;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import de.christian_heinisch.packliste.database.Travel;
import de.christian_heinisch.packliste.database.TravelDataSource;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddNewActivityFragment extends Fragment {

    public AddNewActivityFragment() {
    }

    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    private TravelDataSource dataSource;

    View rootview;

    EditText textCity;
    Button maddCityButton;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Travel testMemo = new Travel("Birnen", 5, 102, 123);
        Log.d(LOG_TAG, "Inhalt der Testmemo: " + testMemo.toString());

        dataSource = new TravelDataSource(getActivity());

        Log.d(LOG_TAG, "Die Datenquelle wird geöffnet.");
        dataSource.open();

        Log.d(LOG_TAG, "Die Datenquelle wird geschlossen.");
        dataSource.close();



        rootview = inflater.inflate(R.layout.fragment_add_new, container, false);

        maddCityButton = (Button)rootview.findViewById(R.id.addCityButton);

        maddCityButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        addCity();
                    }
                });

        return rootview;
    }

    public void addCity(){

        textCity = (EditText) rootview.findViewById(R.id.editTextCity);

        dataSource.open();

        Travel city = dataSource.createTravel(textCity.getText().toString(), 2, 5);
        Log.d(LOG_TAG, "Es wurde der folgende Eintrag in die Datenbank geschrieben:");
        Log.d(LOG_TAG, "ID: " + city.getId() + ", Inhalt: " + city.toString());

        Log.d(LOG_TAG, "Die Datenquelle wird geschlossen.");
        dataSource.close();
    }



}
