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
public class DialogChangeStuffText extends DialogFragment {

    View rootview;
    private StuffDataSource dataSource;
    private EditText text;


    public DialogChangeStuffText() {
        // Required empty public constructor
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final long id = this.getArguments().getLong("id");

        // Inflate the layout for this fragment

        LayoutInflater inflater = getActivity().getLayoutInflater();
        rootview = inflater.inflate(R.layout.fragment_dialog_edit_stuff, null);

        dataSource = new StuffDataSource(getContext());

        text = (EditText) rootview.findViewById(R.id.editStuffText);
        dataSource.open();
        getText(id);
        dataSource.close();

        return new AlertDialog.Builder(getActivity())
                .setView(rootview)
                .setTitle("Test ändern")
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
                        setStuffText(id,text.getText().toString());
                        ((MainActivity)getContext()).stuffListFragment();

                    }
                })
                .create();
    }

    public void getText(long id){
        String StuffText = dataSource.getText(id);
        text.setText(StuffText);

    }

    public void setStuffText(long id, String stufftext){
        dataSource.open();
        dataSource.updateText(id, stufftext);
        dataSource.close();

    }

}
