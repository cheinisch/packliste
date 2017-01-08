package de.christian_heinisch.packliste;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import de.christian_heinisch.packliste.database.CityAdapter;
import de.christian_heinisch.packliste.database.StuffDataSource;
import de.christian_heinisch.packliste.database.TravelDataSource;

import static de.christian_heinisch.packliste.AddNewActivityFragment.LOG_TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class DialogCityFragment extends DialogFragment {

    private TravelDataSource dataSource;
    private StuffDataSource dataSource_stuff;
    View dialoglayout;
    EditText travelname;
    EditText startdate;
    EditText enddate;
    CheckBox delete;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {




        final long id = this.getArguments().getLong("id");

        System.out.println("ID: " + id);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        dialoglayout = inflater.inflate(R.layout.fragment_dialog_city, null);
        dataSource = new TravelDataSource(getContext());
        dataSource_stuff = new StuffDataSource(getContext());
        travelname = (EditText) dialoglayout.findViewById(R.id.editTextTravelEdit);
        startdate  = (EditText) dialoglayout.findViewById(R.id.editTextTravelDateedit);
        enddate = (EditText) dialoglayout.findViewById(R.id.editTextTravelDate2edit);


        getTravelData(id);

        return new AlertDialog.Builder(getActivity())
                .setView(dialoglayout)
                .setTitle("Reise bearbeiten/löschen")
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing (will close dialog)
                    }
                })
                .setPositiveButton(android.R.string.yes,  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // do something

                        delete = (CheckBox) dialoglayout.findViewById(R.id.checkDelete);

                        if(delete.isChecked()){
                            delete(id);
                        }else{
                            update(id);
                        }

                        ((CityActivity)getActivity()).showAllCities();

                    }
                })
                .create();
    }

    public void getTravelData(long id){



        Log.d(LOG_TAG, "Die Datenquelle wird geöffnet.");
        dataSource.open();

        String City = dataSource.getTravelCity(id);
        Long startDate = Long.parseLong(dataSource.getStartDate(id));
        Long endDate = Long.parseLong(dataSource.getEndDate(id));

        Log.d(LOG_TAG, "Die Datenquelle wird geschlossen.");
        dataSource.close();

        travelname.setText(City);
        startdate.setText(getDate(startDate));
        enddate.setText(getDate(endDate));

    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.GERMAN);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("dd.MM.yyyy", cal).toString();
        return date;
    }

    public void delete(long id){

        Log.d(LOG_TAG, "Die Datenquelle wird geöffnet.");
        dataSource.open();

        dataSource.deleteTravel(id);

        Log.d(LOG_TAG, "Die Datenquelle wird geschlossen.");
        dataSource.close();

        Log.d(LOG_TAG, "Die Datenquelle wird geöffnet.");
        dataSource_stuff.open();

        dataSource_stuff.deleteStuffCity(id);

        Log.d(LOG_TAG, "Die Datenquelle wird geschlossen.");
        dataSource_stuff.close();

    }

    public void update(long id){

        dataSource.open();

        String newCity = travelname.getText().toString();
        long newStartdate =convertTime(startdate.getText().toString());
        long newEnddate = convertTime(enddate.getText().toString());

        dataSource.updateTravel(id, newCity, newStartdate, newEnddate);

        dataSource.close();

    }

    public long convertTime(String newdate) {



        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date date = null;
        try {
            date = (Date)formatter.parse(newdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("Today is " +date.getTime() + " neues Datum: " + newdate);

        long returnDate = date.getTime();

        return returnDate;
    }
}