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
import mykola.devchallenge.com.ansidrawing.adapters.SymbolPickerAdapter;
import mykola.devchallenge.com.ansidrawing.callbacks.CallbackSymbol;
import mykola.devchallenge.com.ansidrawing.helpers.DataHelper;


public class SymbolPickerDialog extends DialogFragment implements CallbackSymbol {

    public static final String SELECTED_SYMBOL = "selected_symbol";

    private RecyclerView list;
    private RecyclerView.Adapter adapter;

    private DataHelper dataHelper;

    private CallbackSymbol callbackSymbol;

    public static SymbolPickerDialog newInstance(int selectedSymbol) {

        Bundle args = new Bundle();
        args.putInt(SELECTED_SYMBOL, selectedSymbol);
        SymbolPickerDialog fragment = new SymbolPickerDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        dataHelper =  DataHelper.get(getContext());

        View v = LayoutInflater.from(getContext()).inflate(R.layout.list_picker_fragment, null);

        list = (RecyclerView) v.findViewById(R.id.list);
        list.setLayoutManager(new GridLayoutManager(getContext(), 5));
        adapter = new SymbolPickerAdapter(this,dataHelper.getSymbols(), getArguments().getInt(SELECTED_SYMBOL));
        list.setAdapter(adapter);


        return new AlertDialog.Builder(getContext()).
                setView(v)
             /*   .setPositiveButton("OK", null)
                .setNegativeButton("CANCEL", null)*/
                .setTitle("Pick symbol")
                .create();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CallbackSymbol) {
            callbackSymbol = (CallbackSymbol) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CallbackSymbol");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbackSymbol = null;
    }

    @Override
    public void setSelectedSymbol(int symbol) {
        callbackSymbol.setSelectedSymbol(symbol);
        dismiss();
    }
}
