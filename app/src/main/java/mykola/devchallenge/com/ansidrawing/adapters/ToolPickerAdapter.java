package mykola.devchallenge.com.ansidrawing.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import mykola.devchallenge.com.ansidrawing.R;
import mykola.devchallenge.com.ansidrawing.callbacks.CallbackTool;
import mykola.devchallenge.com.ansidrawing.models.tools.Tool;


public class ToolPickerAdapter extends RecyclerView.Adapter<ToolPickerAdapter.ViewHolder> {
    private Tool[] data;
    private String selectedTool;
    private CallbackTool callbackTool;

    public ToolPickerAdapter(CallbackTool callbackTool, Tool[] data, String selectedTool) {
        this.callbackTool = callbackTool;
        this.data = data;
        this.selectedTool = selectedTool;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tool_item, null);

        ViewHolder vh = new ViewHolder(v);
        return vh;

    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Tool tool = data[position];
        holder.imageItem.setBackgroundResource(tool.getImage());
        holder.textItem.setText(tool.getName());
        if (tool.getName().equals(selectedTool))
            holder.checkItem.setVisibility(View.VISIBLE);
        else holder.checkItem.setVisibility(View.GONE);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbackTool.setSelectedTool(data[position]);
            }
        });

    }


    @Override
    public int getItemCount() {
        return data.length;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {


        public ImageView checkItem, imageItem;
        public TextView textItem;
        public View view;


        public ViewHolder(View view) {
            super(view);
            this.view = view;
            textItem = (TextView) view.findViewById(R.id.textItem);
            checkItem = (ImageView) view.findViewById(R.id.checkItem);
            imageItem = (ImageView) view.findViewById(R.id.imageItem);


        }

    }


}
