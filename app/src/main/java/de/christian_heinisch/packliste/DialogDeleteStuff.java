package de.christian_heinisch.packliste;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import de.christian_heinisch.packliste.database.StuffDataSource;


/**
 * A simple {@link Fragment} subclass.
 */
public class DialogDeleteStuff extends DialogFragment {

    View rootview;
    private StuffDataSource dataSource;


    public DialogDeleteStuff() {
        // Required empty public constructor
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final long id = this.getArguments().getLong("id");

        // Inflate the layout for this fragment

        LayoutInflater inflater = getActivity().getLayoutInflater();
        rootview = inflater.inflate(R.layout.fragment_dialog_delete_stuff, null);

        dataSource = new StuffDataSource(getContext());

        return new AlertDialog.Builder(getActivity())
                .setView(rootview)
                .setTitle(R.string.stuff_delete_title)
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
                        deleteStuff(id);
                        ((MainActivity)getContext()).stuffListFragment();

                    }
                })
                .create();
    }

    public void deleteStuff(long id){
        dataSource.open();
        dataSource.deleteStuff(id);
        dataSource.close();

    }

}
