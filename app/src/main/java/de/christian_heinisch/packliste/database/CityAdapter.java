package de.christian_heinisch.packliste.database;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import de.christian_heinisch.packliste.R;

/**
 * Created by chris on 07.01.2017.
 */

public class CityAdapter extends ArrayAdapter<Travel> {

    public CityAdapter(Context context, ArrayList<Travel> cities) {
        super(context, 0, cities);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Travel city = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_citylist, parent, false);
        }

        TextView tvCity = (TextView) convertView.findViewById(R.id.textViewCityList);
        TextView tvEnddate = (TextView) convertView.findViewById(R.id.textViewEndDateList);
        TextView tvStartdate = (TextView) convertView.findViewById(R.id.textViewStartDateList);

        tvCity.setText(city.getCity().toString());
        tvEnddate.setText(getDate(city.getEnddate()));
        tvStartdate.setText(getDate(city.getStartdate()));

        return convertView;

    }

    public void openitem(View view){


        Travel city = (Travel) view.getTag();

        // Erstelle einen neuen Intent und weise ihm eine Actvity zu


    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.GERMAN);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("dd.MM.yyyy", cal).toString();
        return date;
    }

}
