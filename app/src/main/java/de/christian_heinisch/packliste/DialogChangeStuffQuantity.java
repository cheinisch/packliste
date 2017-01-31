package de.christian_heinisch.packliste;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import de.christian_heinisch.packliste.database.StuffDataSource;


/**
 * A simple {@link Fragment} subclass.
 */
public class DialogChangeStuffQuantity extends DialogFragment {

    View rootview;
    private StuffDataSource dataSource;
    private EditText quantity;


    public DialogChangeStuffQuantity() {
        // Required empty public constructor
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final long id = this.getArguments().getLong("id");

        // Inflate the layout for this fragment

        LayoutInflater inflater = getActivity().getLayoutInflater();
        rootview = inflater.inflate(R.layout.fragment_dialog_change_stuff_quantity, null);

        dataSource = new StuffDataSource(getContext());

        quantity = (EditText) rootview.findViewById(R.id.editStuffQuantity);
        dataSource.open();
        getQuantity(id);
        dataSource.close();

        return new AlertDialog.Builder(getActivity())
                .setView(rootview)
                .setTitle(R.string.stuff_change_quantity_title)
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
                        setQuantity(id,Integer.parseInt(quantity.getText().toString()));
                        ((MainActivity)getContext()).stuffListFragment();

                    }
                })
                .create();
    }

    public void getQuantity(long id){


        String text = Long.toString(dataSource.getQuantity(id));
        quantity.setText(text);

    }

    public void setQuantity(long id, int quantity){
        dataSource.open();
        dataSource.updateQuantity(id, quantity);
        dataSource.close();

    }

}
