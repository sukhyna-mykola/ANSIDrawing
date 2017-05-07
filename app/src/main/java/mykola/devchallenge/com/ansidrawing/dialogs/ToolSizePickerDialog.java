package mykola.devchallenge.com.ansidrawing.dialogs;


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

import mykola.devchallenge.com.ansidrawing.R;
import mykola.devchallenge.com.ansidrawing.callbacks.CallbackSize;

import static mykola.devchallenge.com.ansidrawing.dialogs.SymbolPickerDialog.SELECTED_SYMBOL;

/**
 * Created by mykola on 04.05.17.
 */

public class ToolSizePickerDialog extends DialogFragment {

    public static final String SELECTED_SIZE = "selected_size";

    private static final int DELTA = 1;
    private CallbackSize callbackSize;

    private SeekBar seekSize;
    private TextView textSize;
    private TextView previewText;

    public static ToolSizePickerDialog newInstance(int selectedSize, int selectedSymbol) {

        Bundle args = new Bundle();
        args.putInt(SELECTED_SIZE, selectedSize);
        args.putInt(SELECTED_SYMBOL, selectedSymbol);

        ToolSizePickerDialog fragment = new ToolSizePickerDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View v = LayoutInflater.from(getContext()).inflate(R.layout.size_picker_fragment, null);

        seekSize = (SeekBar) v.findViewById(R.id.seekSize);
        textSize = (TextView) v.findViewById(R.id.textSize);
        previewText = (TextView) v.findViewById(R.id.previewSize);

        int size = getArguments().getInt(SELECTED_SIZE);
        final int symbol = getArguments().getInt(SELECTED_SYMBOL);

        previewText.setLineSpacing(0,0.6f);
        previewText.setText(generatePreview(size, Character.toString((char) symbol)));

        textSize.setText(String.valueOf(size));

        seekSize.setProgress(size - DELTA);
        seekSize.setMax(9);
        seekSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    previewText.setText(generatePreview(progress + DELTA, Character.toString((char) symbol)));
                    textSize.setText(String.valueOf(progress + DELTA));

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
                        callbackSize.setSelectedSizeTool(seekSize.getProgress() + DELTA);
                        dismiss();
                    }
                })
                // .setNegativeButton("CANCEL", null)*/
                .setTitle("Pick Tool Size")
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

    private String generatePreview(int n, String c) {
        String result = new String();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result += c;
            }
            result += '\n';

        }
        return result;

    }

}
