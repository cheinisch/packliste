package de.christian_heinisch.packliste;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Travel testMemo = new Travel("Birnen", 5, 102, 123);
        Log.d(LOG_TAG, "Inhalt der Testmemo: " + testMemo.toString());

        dataSource = new TravelDataSource(getActivity());


        return inflater.inflate(R.layout.fragment_add_new, container, false);
    }
}
