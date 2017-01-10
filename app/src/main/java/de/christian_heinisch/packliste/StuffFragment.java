package de.christian_heinisch.packliste;


import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import de.christian_heinisch.packliste.database.Stuff;
import de.christian_heinisch.packliste.database.StuffAdapter;
import de.christian_heinisch.packliste.database.StuffDataSource;
import de.christian_heinisch.packliste.database.StuffHaveToBuyAdapter;
import de.christian_heinisch.packliste.database.TravelDataSource;

import static android.content.Context.MODE_PRIVATE;
import static de.christian_heinisch.packliste.AddNewActivityFragment.LOG_TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class StuffFragment extends Fragment {


    public View rootview;
    private TravelDataSource dataSource;
    private StuffDataSource dataSource_stuff;
    long id;
    private ListView listView;

    public StuffFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_stuff, container, false);

        listView = (ListView) rootview.findViewById(R.id.listviewStuffList);



        id = getID();

        getTravelData(id);
        getStuffList(id);
        activateAddButton(id);
        activateAddButtonBuy(id);

        return rootview;
    }

    public void getTravelData(long id){

        dataSource = new TravelDataSource(getContext());

        Log.d(LOG_TAG, "Die Datenquelle wird geöffnet.");
        dataSource.open();

        String City = dataSource.getTravelCity(id);
        Long startDate = Long.parseLong(dataSource.getStartDate(id));
        Long endDate = Long.parseLong(dataSource.getEndDate(id));

        Log.d(LOG_TAG, "Die Datenquelle wird geschlossen.");
        dataSource.close();

        TextView cityName = (TextView) rootview.findViewById(R.id.textViewCityname);
        cityName.setText(City);

        TextView travelDate = (TextView) rootview.findViewById(R.id.textViewTravelDate);
        travelDate.setText(getDate(startDate) + " - " + getDate(endDate));

    }

    public void getStuffList(long id){
        dataSource_stuff = new StuffDataSource(getContext());


        Log.d(LOG_TAG, "Die Datenquelle wird geöffnet.");
        dataSource_stuff.open();

        showAllStuff(id);
        showAllListEntriesBuy(id);

        Log.d(LOG_TAG, "Die Datenquelle wird geschlossen.");
        dataSource_stuff.close();
    }

    public void showAllStuff (long id) {

        ArrayList<Stuff> arrayOfStuff = null;
        arrayOfStuff = dataSource_stuff.getStuffForList(id, "false");

        StuffAdapter adapter = new StuffAdapter(getActivity(), arrayOfStuff);

        listView.setAdapter(adapter);
        /*Fix für die Höhe*/
        setListViewHeightBasedOnChildren(listView);

        /*OnClick Listener*/

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Knobb gedrückt");
            }
        });
    }

    private void showAllListEntriesBuy (long id) {

        ArrayList<Stuff> arrayOfStuff = null;
        arrayOfStuff = dataSource_stuff.getStuffForList(id, "true");

        StuffHaveToBuyAdapter adapter = new StuffHaveToBuyAdapter(getContext(), arrayOfStuff);
        ListView listView = (ListView) rootview.findViewById(R.id.listviewStuffBuy);
        listView.setAdapter(adapter);
        /*Fix für die Höhe*/
        setListViewHeightBasedOnChildren(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Knobb auch gedrückt");
            }
        });
    }

    private void activateAddButton(long id) {
        Button buttonAddProduct = (Button) rootview.findViewById(R.id.button_add_product);
        final EditText editTextQuantity = (EditText) rootview.findViewById(R.id.editText_quantity);
        final EditText editTextProduct = (EditText) rootview.findViewById(R.id.editText_product);
        final long reloadid = id;
        final int cityid = (int) id;

        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String quantityString = editTextQuantity.getText().toString();
                String product = editTextProduct.getText().toString();

                if(TextUtils.isEmpty(quantityString)) {
                    editTextQuantity.setError("Fehler");
                    return;
                }
                if(TextUtils.isEmpty(product)) {
                    editTextProduct.setError("Fehler");
                    return;
                }

                int quantity = Integer.parseInt(quantityString);
                editTextQuantity.setText("");
                editTextProduct.setText("");

                Log.d(LOG_TAG, "Die Datenquelle wird geöffnet.");
                dataSource_stuff.open();
                dataSource_stuff.createStuff(product, "false","false" , quantity ,cityid);

                showAllStuff(reloadid);
                dataSource_stuff.close();


            }
        });

    }

    private void activateAddButtonBuy(long id) {
        Button buttonAddProduct = (Button) rootview.findViewById(R.id.button_add_product_buy);
        final EditText editTextQuantity = (EditText) rootview.findViewById(R.id.editText_quantity_buy);
        final EditText editTextProduct = (EditText) rootview.findViewById(R.id.editText_product_buy);
        final long reloadid = id;
        final int cityid = (int) id;

        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String quantityString = editTextQuantity.getText().toString();
                String product = editTextProduct.getText().toString();

                if(TextUtils.isEmpty(quantityString)) {
                    editTextQuantity.setError("Fehler");
                    return;
                }
                if(TextUtils.isEmpty(product)) {
                    editTextProduct.setError("Fehler");
                    return;
                }

                int quantity = Integer.parseInt(quantityString);
                editTextQuantity.setText("");
                editTextProduct.setText("");

                Log.d(LOG_TAG, "Die Datenquelle wird geöffnet.");
                dataSource_stuff.open();
                dataSource_stuff.createStuff(product, "false","true" , quantity ,cityid);

              //  showAllListEntriesBuy(reloadid);
                dataSource_stuff.close();
            }
        });

    }

    /**** Method for Setting the Height of the ListView dynamically.
     **** Hack to fix the issue of not showing all the items of the ListView
     **** when placed inside a ScrollView  ****/
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        int x = listAdapter.getCount();
        View view = null;
        for (int i = 0; i < x; i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, LinearLayout.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount()));
        listView.setLayoutParams(params);
    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.GERMAN);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("dd.MM.yyyy", cal).toString();
        return date;
    }

    private long getID(){

        SharedPreferences settings = getContext().getSharedPreferences("Packliste", MODE_PRIVATE);

        long id = 1;

        String idString = settings.getString("id", "1");

        id = Long.parseLong(idString);
        dataSource = new TravelDataSource(getContext());
        dataSource.open();
        String city = dataSource.getTravelCity(id);
        if (city.equalsIgnoreCase(""))
        {
            id = dataSource.getFirstTravel();
        }

        dataSource.close();

        return id;
    }
}
