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
import mykola.devchallenge.com.ansidrawing.adapters.ColorPickerAdapter;
import mykola.devchallenge.com.ansidrawing.callbacks.CallbackColor;
import mykola.devchallenge.com.ansidrawing.helpers.DataHelper;

/**
 * Created by mykola on 04.05.17.
 */

public class ColorPickerDialog extends DialogFragment implements CallbackColor {

    public static final String SELECTED_COLOR = "selected_color";

    private RecyclerView list;
    private RecyclerView.Adapter adapter;

    private DataHelper dataHelper;

    private CallbackColor callbackColor;

    public static ColorPickerDialog newInstance(int selectedColor) {

        Bundle args = new Bundle();
        args.putInt(SELECTED_COLOR, selectedColor);
        ColorPickerDialog fragment = new ColorPickerDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        dataHelper = DataHelper.get(getContext());

        View v = LayoutInflater.from(getContext()).inflate(R.layout.list_picker_fragment, null);

        list = (RecyclerView) v.findViewById(R.id.list);
        list.setLayoutManager(new GridLayoutManager(getContext(), 3));
        adapter = new ColorPickerAdapter(this,dataHelper.getColors(), getArguments().getInt(SELECTED_COLOR));
        list.setAdapter(adapter);


        return new AlertDialog.Builder(getContext()).
                setView(v)
             /*   .setPositiveButton("OK", null)
                .setNegativeButton("CANCEL", null)*/
                .setTitle("Pick color")
                .create();
    }

    @Override
    public void setSelectedColor(int color) {
        callbackColor.setSelectedColor(color);
        dismiss();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CallbackColor) {
            callbackColor = (CallbackColor) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CallbackColor");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbackColor = null;
    }
}
