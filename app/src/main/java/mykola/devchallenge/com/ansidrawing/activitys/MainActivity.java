package mykola.devchallenge.com.ansidrawing.activitys;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import mykola.devchallenge.com.ansidrawing.R;
import mykola.devchallenge.com.ansidrawing.callbacks.CallbackColor;
import mykola.devchallenge.com.ansidrawing.callbacks.CallbackSize;
import mykola.devchallenge.com.ansidrawing.callbacks.CallbackSymbol;
import mykola.devchallenge.com.ansidrawing.callbacks.CallbackTool;
import mykola.devchallenge.com.ansidrawing.callbacks.CallbackUpdate;
import mykola.devchallenge.com.ansidrawing.dialogs.ColorPickerDialog;
import mykola.devchallenge.com.ansidrawing.dialogs.SymbolPickerDialog;
import mykola.devchallenge.com.ansidrawing.dialogs.SymbolSizePickerDialog;
import mykola.devchallenge.com.ansidrawing.dialogs.ToolPickerDialog;
import mykola.devchallenge.com.ansidrawing.dialogs.ToolSizePickerDialog;
import mykola.devchallenge.com.ansidrawing.helpers.DataHelper;
import mykola.devchallenge.com.ansidrawing.helpers.DrawHelper;
import mykola.devchallenge.com.ansidrawing.models.tools.Tool;
import mykola.devchallenge.com.ansidrawing.views.CustomTextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        View.OnTouchListener, CallbackUpdate, CallbackColor, CallbackSymbol, CallbackSize, CallbackTool {

    private static final String TAG = "MainActivity";
    private static final String COLOR_PICKER_DIALOG = "color_picker_dialog";
    private static final String SYMBOL_PICKER_DIALOG = "symbol_picker_dialog";
    private static final String TOOL_SIZE_PICKER_DIALOG = "tool_size_picker_dialog";
    private static final String SYMBOL_SIZE_PICKER_DIALOG = "symbol_size_picker_dialog";
    private static final String TOOL_PICKER_DIALOG = "tool_picker_dialog";

    private TextView tollSizeTextView;
    private TextView symbolSizeTextView;
    private ImageView tollTypeImage;
    private ImageView tollColorImage;
    private TextView tollSymbolTextView;

    private FrameLayout layers;
    private CustomTextView canvas;
    private DrawHelper helper;
    private DataHelper dataHelper;

    private Context context;

    private ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onGlobalLayout() {
            float height = layers.getHeight();
            float width = layers.getWidth();

            int symbolSize = 10;
            int color = dataHelper.getColors()[0];
            int symbol = dataHelper.getSymbols()[0];
            int toolSize = 10;

            helper = new DrawHelper(context, symbolSize, color, symbol, toolSize, width, height);

            canvas = new CustomTextView(context, helper);
            canvas.setOnTouchListener((View.OnTouchListener) context);

            layers.addView(canvas);

            updateViews();

            layers.getViewTreeObserver().removeOnGlobalLayoutListener(this);

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        dataHelper = DataHelper.get(context);

        tollSizeTextView = (TextView) findViewById(R.id.tool_size_text);
        tollTypeImage = (ImageView) findViewById(R.id.tool_type_image);
        tollColorImage = (ImageView) findViewById(R.id.tool_color_image);
        tollSymbolTextView = (TextView) findViewById(R.id.tool_symbol_text);
        symbolSizeTextView = (TextView) findViewById(R.id.symbol_size_text);

        layers = (FrameLayout) findViewById(R.id.layers);
        layers.setOnTouchListener(this);
        layers.getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tool_size_btn:
                ToolSizePickerDialog.newInstance(helper.getSizeTool(), helper.getSymbolTool())
                        .show(getSupportFragmentManager(), TOOL_SIZE_PICKER_DIALOG);

                break;
            case R.id.symbol_size_btn:
                SymbolSizePickerDialog.newInstance(helper.getSizeSymbol(), helper.getSymbolTool())
                        .show(getSupportFragmentManager(), SYMBOL_SIZE_PICKER_DIALOG);
                break;
            case R.id.tool_color_btn:
                ColorPickerDialog.newInstance(helper.getColorTool())
                        .show(getSupportFragmentManager(), COLOR_PICKER_DIALOG);
                break;
            case R.id.tool_symbol_btn:
                SymbolPickerDialog.newInstance(helper.getSizeTool())
                        .show(getSupportFragmentManager(), SYMBOL_PICKER_DIALOG);

                break;
            case R.id.tool_type_btn:
                ToolPickerDialog.newInstance(helper.getTool().getName())
                        .show(getSupportFragmentManager(), TOOL_PICKER_DIALOG);
                break;

        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:
                helper.draw(x, y);
                Log.d(TAG, "Move: " + x + "," + y);
                break;
            case MotionEvent.ACTION_UP:

                break;
        }

        return true;
    }

    @Override
    public void updateCanvas() {
        canvas.invalidate();
    }

    @Override
    public void updateViews() {
        tollSizeTextView.setText(String.valueOf(helper.getSizeTool()));
        symbolSizeTextView.setText(String.valueOf(helper.getSizeSymbol()));
        tollSymbolTextView.setText(Character.toString((char) helper.getSymbolTool()));
        tollTypeImage.setImageResource(helper.getTool().getImage());
        tollColorImage.setBackgroundColor(helper.getColorTool());
    }

    @Override
    public void setSelectedColor(int color) {
        helper.setColorTool(color);
        tollColorImage.setBackgroundColor(color);
    }

    @Override
    public void setSelectedSymbol(int symbol) {
        helper.setSymbolTool(symbol);
        tollSymbolTextView.setText(Character.toString((char) symbol));
    }

    @Override
    public void setSelectedSizeSymbol(int size) {
        helper.setSizeSymbol(size);
        symbolSizeTextView.setText(String.valueOf(size));
    }

    @Override
    public void setSelectedSizeTool(int size) {
        helper.setSizeTool(size);
        tollSizeTextView.setText(String.valueOf(size));
    }

    @Override
    public void setSelectedTool(Tool tool) {
        helper.prepareTool(tool);
        tollTypeImage.setImageResource(tool.getImage());
    }
}
