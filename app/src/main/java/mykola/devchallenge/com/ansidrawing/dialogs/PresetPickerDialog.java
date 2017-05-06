package mykola.devchallenge.com.ansidrawing.dialogs;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import mykola.devchallenge.com.ansidrawing.R;
import mykola.devchallenge.com.ansidrawing.adapters.PresetPickerAdapter;
import mykola.devchallenge.com.ansidrawing.callbacks.CallbackPreset;
import mykola.devchallenge.com.ansidrawing.helpers.DataHelper;
import mykola.devchallenge.com.ansidrawing.models.Preset;

/**
 * Created by mykola on 04.05.17.
 */

public class PresetPickerDialog extends DialogFragment implements CallbackPreset {

    private RecyclerView list;
    private RecyclerView.Adapter adapter;

    private CallbackPreset callbackPreset;

    public static PresetPickerDialog newInstance() {

        Bundle args = new Bundle();
        PresetPickerDialog fragment = new PresetPickerDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.list_picker_fragment, null);

        list = (RecyclerView) v.findViewById(R.id.list);
        list.setLayoutManager(new GridLayoutManager(getContext(), 3));

        adapter = new PresetPickerAdapter(this, DataHelper.get(getContext()).getPresets());
        list.setAdapter(adapter);


        return new AlertDialog.Builder(getContext()).
                setView(v)
             /*   .setPositiveButton("OK", null)
                .setNegativeButton("CANCEL", null)*/
                .setTitle("Pick Preset")
                .create();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CallbackPreset) {
            callbackPreset = (CallbackPreset) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CallbackPreset");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbackPreset = null;
    }

    @Override
    public void setSelectedPreset(Preset Preset) {
        callbackPreset.setSelectedPreset(Preset);
        dismiss();
    }


}
