package mykola.devchallenge.com.ansidrawing.dialogs;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import mykola.devchallenge.com.ansidrawing.R;
import mykola.devchallenge.com.ansidrawing.adapters.FilePickerAdapter;
import mykola.devchallenge.com.ansidrawing.callbacks.CallbackFile;
import mykola.devchallenge.com.ansidrawing.helpers.DataHelper;
import mykola.devchallenge.com.ansidrawing.helpers.FileHelper;


public class OpenDialog extends DialogFragment implements CallbackFile {

    private CallbackFile callbackFile;


    private RecyclerView list;
    private RecyclerView.Adapter adapter;

    public static OpenDialog newInstance() {

        Bundle args = new Bundle();

        OpenDialog fragment = new OpenDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View v = LayoutInflater.from(getContext()).inflate(R.layout.list_picker_fragment, null);

        list = (RecyclerView) v.findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new FilePickerAdapter(FileHelper.getInstance(getContext()).getFilesInDirecory(), this);

        list.setAdapter(adapter);


        return new AlertDialog.Builder(getContext()).
                setView(v)
             /*   .setPositiveButton("OK", null)
                .setNegativeButton("CANCEL", null)*/
                .setTitle("Pick file")
                .create();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CallbackFile) {
            callbackFile = (CallbackFile) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CallbackFile");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbackFile = null;
    }


    @Override
    public void save(String fileName) {
        //
    }

    @Override
    public void load(String fileName) {
        callbackFile.load(fileName);
        dismiss();
    }
}
