package mykola.devchallenge.com.ansidrawing.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import mykola.devchallenge.com.ansidrawing.R;
import mykola.devchallenge.com.ansidrawing.callbacks.CallbackColor;

/**
 * Created by mykola on 23.04.17.
 */


public class ColorPickerAdapter extends RecyclerView.Adapter<ColorPickerAdapter.ViewHolder> {
    private int[] data;
    private int selectedColor;
    private CallbackColor callbackColor;

    public ColorPickerAdapter(CallbackColor callbackColor, int[] data, int selectedColor) {
        this.callbackColor = callbackColor;
        this.data = data;
        this.selectedColor = selectedColor;

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
        int color = data[position];
        holder.frameLayout.setBackgroundColor(color);
        if (color == selectedColor)
            holder.checkStatus.setVisibility(View.VISIBLE);
        else holder.checkStatus.setVisibility(View.INVISIBLE);

        holder.frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbackColor.setSelectedColor(data[position]);
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


        public ViewHolder(View view) {
            super(view);
            frameLayout = (FrameLayout) view;
            checkStatus = (ImageView) view.findViewById(R.id.checkItem);


        }

    }


}
