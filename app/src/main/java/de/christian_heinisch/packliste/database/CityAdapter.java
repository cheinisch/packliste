package de.christian_heinisch.packliste.database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

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

        final TextView tvCity = (TextView) convertView.findViewById(R.id.textViewCity);
        TextView tvEnddate = (TextView) convertView.findViewById(R.id.textViewEndDate);
        TextView tvStartdate = (TextView) convertView.findViewById(R.id.textViewStartDate);

        tvCity.setText(city.city);
        tvEnddate.setText(city.startdate);
        tvStartdate.setText(city.enddate);

        return convertView;

    }

}
