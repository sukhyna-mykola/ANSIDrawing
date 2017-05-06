package mykola.devchallenge.com.ansidrawing.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import mykola.devchallenge.com.ansidrawing.R;
import mykola.devchallenge.com.ansidrawing.callbacks.CallbackPreset;
import mykola.devchallenge.com.ansidrawing.models.Preset;


/**
 * Created by mykola on 23.04.17.
 */


public class PresetPickerAdapter extends RecyclerView.Adapter<PresetPickerAdapter.ViewHolder> {
    private Preset[] data;
    private CallbackPreset callbackPreset;

    public PresetPickerAdapter(CallbackPreset callbackPreset, Preset[] data) {
        this.callbackPreset = callbackPreset;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.preset_item, null);

        ViewHolder vh = new ViewHolder(v);
        return vh;

    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Preset Preset = data[position];

        holder.imageItem.setImageResource(Preset.getImage());
        holder.textItem.setText(Preset.getName());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbackPreset.setSelectedPreset(data[position]);
            }
        });

    }


    @Override
    public int getItemCount() {
        return data.length;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {


        public ImageView imageItem;
        public TextView textItem;
        public View view;


        public ViewHolder(View view) {
            super(view);
            this.view = view;
            textItem = (TextView) view.findViewById(R.id.textItem);
            imageItem = (ImageView) view.findViewById(R.id.imageItem);


        }

    }


}
