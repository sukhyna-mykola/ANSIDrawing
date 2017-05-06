package mykola.devchallenge.com.ansidrawing.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mykola.devchallenge.com.ansidrawing.callbacks.CallbackFile;

/**
 * Created by mykola on 23.04.17.
 */


public class FilePickerAdapter extends RecyclerView.Adapter<FilePickerAdapter.ViewHolder> {
    private List<String> data;

    private CallbackFile callbackFile;

    public FilePickerAdapter(List<String> data, CallbackFile callbackFile) {
        this.data = data;
        this.callbackFile = callbackFile;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView textView = (TextView) LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, null);
        ViewHolder vh = new ViewHolder(textView);
        return vh;

    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final String file = data.get(position);
        holder.fileName.setText(file);

        holder.fileName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbackFile.load(file);
            }
        });

    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView fileName;


        public ViewHolder(TextView view) {
            super(view);
            this.fileName = view;


        }

    }


}
