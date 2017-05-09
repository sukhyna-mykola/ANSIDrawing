package mykola.devchallenge.com.ansidrawing.adapters;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import mykola.devchallenge.com.ansidrawing.R;
import mykola.devchallenge.com.ansidrawing.callbacks.CallbackSymbol;

public class SymbolPickerAdapter extends RecyclerView.Adapter<SymbolPickerAdapter.ViewHolder> {
    private int[] data;
    private int selectedSymbol;
    private CallbackSymbol callbackSymbol;

    public SymbolPickerAdapter(CallbackSymbol callbackSymbol, int[] data, int selectedSymbol) {
        this.callbackSymbol = callbackSymbol;
        this.data = data;
        this.selectedSymbol = selectedSymbol;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.symbol_item, null);
        ViewHolder vh = new ViewHolder(v);
        return vh;

    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        int symbol = data[position];

        holder.symbolText.setText(Character.toString((char) symbol));

        if (symbol == selectedSymbol) {
            holder.symbolText.setTypeface(Typeface.DEFAULT_BOLD);
            holder.symbolText.setBackgroundResource(R.drawable.menu_button_bg_presed);
        }


        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbackSymbol.setSelectedSymbol(data[position]);
            }
        });

    }


    @Override
    public int getItemCount() {
        return data.length;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View view;
        public TextView symbolText;


        public ViewHolder(View view) {
            super(view);
            this.view = view;
            symbolText = (TextView) view.findViewById(R.id.textItem);


        }

    }


}
