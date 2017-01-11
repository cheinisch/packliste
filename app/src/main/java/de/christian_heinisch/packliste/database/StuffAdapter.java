package de.christian_heinisch.packliste.database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import de.christian_heinisch.packliste.MainActivity;
import de.christian_heinisch.packliste.R;

/**
 * Created by chris on 09.01.2017.
 */

public class StuffAdapter  extends ArrayAdapter<Stuff> {

    private StuffDataSource dataSource;

    public StuffAdapter(Context context, ArrayList<Stuff> zeugs) {
        super(context, 0, zeugs);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final Stuff stuff = getItem(position);
        dataSource = new StuffDataSource(getContext());

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_stufflist, parent, false);
        }



        TextView tvStuff = (TextView) convertView.findViewById(R.id.tvStuffListTitle);
        TextView tvQuantity = (TextView) convertView.findViewById(R.id.tvStuffListQuantity);
        CheckBox cbPacked = (CheckBox) convertView.findViewById(R.id.checkBoxStuffList);


        String quantitiy = String.valueOf(stuff.getQuantitiy());
        final String checked = String.valueOf(stuff.isChecked());

        if(quantitiy.equalsIgnoreCase("1")){
            quantitiy = "+";
        }
        tvStuff.setText(stuff.getStuff().toString());
        tvQuantity.setText(quantitiy);
        if(checked.equalsIgnoreCase("true")){
            cbPacked.toggle();
        }

        cbPacked.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                dataSource.open();
                if(checked.equalsIgnoreCase("true")){
                    dataSource.updateStuff(stuff.getId(), stuff.getStuff(), "false", "false", stuff.getQuantitiy(), stuff.getCityid());
                }else{
                    dataSource.updateStuff(stuff.getId(), stuff.getStuff(), "true", "false", stuff.getQuantitiy(), stuff.getCityid());
                }
                dataSource.close();

                ((MainActivity)getContext()).stuffListFragment();
            }
        });

        return convertView;

    }
}
