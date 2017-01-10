package de.christian_heinisch.packliste.database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import de.christian_heinisch.packliste.R;

/**
 * Created by chris on 09.01.2017.
 */

public class StuffHaveToBuyAdapter  extends ArrayAdapter<Stuff> {

    public StuffHaveToBuyAdapter(Context context, ArrayList<Stuff> zeugs) {
        super(context, 0, zeugs);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Stuff stuff = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_stufflistbuy, parent, false);
        }

        TextView tvStuff = (TextView) convertView.findViewById(R.id.tvStuffListTitle);
        TextView tvQuantity = (TextView) convertView.findViewById(R.id.tvStuffListQuantity);
        CheckBox cbPacked = (CheckBox) convertView.findViewById(R.id.checkBoxStuffList);

        tvStuff.setText(stuff.getStuff().toString());
        tvQuantity.setText(String.valueOf(stuff.getQuantitiy()));
        //tvStartdate.setText(getDate(city.getStartdate()));

        return convertView;

    }
}
