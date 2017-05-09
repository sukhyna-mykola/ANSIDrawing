package mykola.devchallenge.com.ansidrawing.dialogs;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import mykola.devchallenge.com.ansidrawing.R;
import mykola.devchallenge.com.ansidrawing.adapters.ToolPickerAdapter;
import mykola.devchallenge.com.ansidrawing.adapters.ToolPickerAdapter;
import mykola.devchallenge.com.ansidrawing.callbacks.CallbackTool;
import mykola.devchallenge.com.ansidrawing.callbacks.CallbackTool;
import mykola.devchallenge.com.ansidrawing.helpers.DataHelper;
import mykola.devchallenge.com.ansidrawing.models.tools.Tool;


public class ToolPickerDialog extends DialogFragment implements CallbackTool {

    public static final String SELECTED_TOOL = "selected_tool";

    private RecyclerView list;
    private RecyclerView.Adapter adapter;

    private DataHelper dataHelper;

    private CallbackTool callbackTool;

    public static ToolPickerDialog newInstance(String selectedTool) {

        Bundle args = new Bundle();
        args.putString(SELECTED_TOOL, selectedTool);
        ToolPickerDialog fragment = new ToolPickerDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        dataHelper = DataHelper.get(getContext());

        View v = LayoutInflater.from(getContext()).inflate(R.layout.list_picker_fragment, null);

        list = (RecyclerView) v.findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        
        adapter = new ToolPickerAdapter(this,dataHelper.getTools(), getArguments().getString(SELECTED_TOOL));
        list.setAdapter(adapter);


        return new AlertDialog.Builder(getContext()).
                setView(v)
             /*   .setPositiveButton("OK", null)
                .setNegativeButton("CANCEL", null)*/
                .setTitle("Pick tool")
                .create();
    }

 

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CallbackTool) {
            callbackTool = (CallbackTool) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CallbackTool");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbackTool = null;
    }

    @Override
    public void setSelectedTool(Tool tool) {
       callbackTool.setSelectedTool(tool);
        dismiss();
    }
}
