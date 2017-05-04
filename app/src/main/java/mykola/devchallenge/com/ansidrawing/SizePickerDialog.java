package mykola.devchallenge.com.ansidrawing;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import mykola.devchallenge.com.ansidrawing.callbacks.CallbackSize;
import mykola.devchallenge.com.ansidrawing.helpers.DataHelper;

import static mykola.devchallenge.com.ansidrawing.SymbolPickerDialog.SELECTED_SYMBOL;

/**
 * Created by mykola on 04.05.17.
 */

public class SizePickerDialog extends DialogFragment {

    public static final String SELECTED_SIZE = "selected_size";

    private DataHelper dataHelper;

    private CallbackSize callbackSize;

    private SeekBar seekSize;
    private TextView textSize;
    private TextView previewText;

    public static SizePickerDialog newInstance(int selectedSize, int selectedSymbol) {

        Bundle args = new Bundle();
        args.putInt(SELECTED_SIZE, selectedSize);
        args.putInt(SELECTED_SYMBOL, selectedSymbol);
        SizePickerDialog fragment = new SizePickerDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        dataHelper = DataHelper.get(getContext());

        View v = LayoutInflater.from(getContext()).inflate(R.layout.size_picker_fragment, null);

        seekSize = (SeekBar) v.findViewById(R.id.seekSize);
        textSize  = (TextView) v.findViewById(R.id.textSize);
        previewText = (TextView) v.findViewById(R.id.previewSize);

        int size = getArguments().getInt(SELECTED_SIZE)-5;
        int symbol =  getArguments().getInt(SELECTED_SYMBOL);

        seekSize.setProgress(size);
        seekSize.setMax(45);

        previewText.setTextSize(size);
        previewText.setText(Character.toString((char)symbol));

        textSize.setText(String.valueOf(size));


        seekSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    previewText.setTextSize(progress + 5);
                    textSize.setText(String.valueOf(progress+5));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        previewText = (TextView) v.findViewById(R.id.previewSize);

        return new AlertDialog.Builder(getContext()).
                setView(v)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callbackSize.setSelectedSize(seekSize.getProgress()+5);
                        dismiss();
                    }
                })
               // .setNegativeButton("CANCEL", null)*/
                .setTitle("Pick size")
                .create();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CallbackSize) {
            callbackSize = (CallbackSize) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CallbackSize");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbackSize = null;
    }

}