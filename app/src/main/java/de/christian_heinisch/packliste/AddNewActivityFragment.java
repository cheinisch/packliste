package de.christian_heinisch.packliste;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.christian_heinisch.packliste.database.Travel;
import de.christian_heinisch.packliste.database.TravelDataSource;

import static android.content.Context.MODE_PRIVATE;

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
    EditText textStart;
    EditText textEnde;
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
        textStart = (EditText) rootview.findViewById(R.id.editTextStart);
        textEnde = (EditText) rootview.findViewById(R.id.editTextEnde);

        long dateStart = 0;
        long dateEnd = 0;

        dataSource.open();

        System.out.println("Datumseingabe:" + textStart.getText().toString());

        try {
            dateStart = convertTime(textStart.getText().toString());
            dateEnd = convertTime(textEnde.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Travel city = dataSource.createTravel(textCity.getText().toString(), dateStart, dateEnd);
        Log.d(LOG_TAG, "Die Datenquelle wird geschlossen.");
        dataSource.close();


        // Setzt die ID als Shared Preference und startet die Main Activity
        SharedPreferences settings = getContext().getSharedPreferences("Packliste", MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        String idString = String.valueOf(city.getId());

        // Speichert ID der Datenbank
        editor.putString("id", idString);

        editor.commit();


        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
    }

    public long convertTime(String newdate) throws ParseException {



        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date date = (Date)formatter.parse(newdate);
        System.out.println("Today is " +date.getTime() + " altes Datum: " + newdate);

        long returnDate = date.getTime();

        return returnDate;
    }



}
