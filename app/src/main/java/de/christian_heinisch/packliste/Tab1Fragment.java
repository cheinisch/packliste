package de.christian_heinisch.packliste;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tab1Fragment extends Fragment {


    public Tab1Fragment() {
        // Required empty public constructor
    }

    View rootview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_tab1, container, false);
        setVersion();
        return rootview;
    }

    public void setVersion() {

        String versionString = BuildConfig.VERSION_NAME;
        int versioncode = BuildConfig.VERSION_CODE;
        TextView version = (TextView) rootview.findViewById(R.id.textVersion);
        version.setText("Version " + versionString + " (" + versioncode + ")");
    }

}
