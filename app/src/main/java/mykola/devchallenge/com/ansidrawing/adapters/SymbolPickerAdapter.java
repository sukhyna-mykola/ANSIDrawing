package mykola.devchallenge.com.ansidrawing.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import mykola.devchallenge.com.ansidrawing.R;
import mykola.devchallenge.com.ansidrawing.callbacks.CallbackSymbol;

/**
 * Created by mykola on 23.04.17.
 */


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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.color_item, null);
        float width =  v.getWidth();
        v.setMinimumHeight((int) (width/3));

        ViewHolder vh = new ViewHolder(v);
        return vh;

    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        int symbol =  data[position];

        holder.symbolText.setText(Character.toString((char)symbol));

        if (symbol == selectedSymbol)
            holder.checkStatus.setVisibility(View.VISIBLE);
        else holder.checkStatus.setVisibility(View.INVISIBLE);


        holder.frameLayout.setOnClickListener(new View.OnClickListener() {
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

        public FrameLayout frameLayout;
        public ImageView checkStatus;
        public TextView symbolText;


        public ViewHolder(View view) {
            super(view);
            frameLayout = (FrameLayout) view;
            checkStatus = (ImageView) view.findViewById(R.id.checkItem);
            symbolText = (TextView) view.findViewById(R.id.textItem);


        }

    }


}
