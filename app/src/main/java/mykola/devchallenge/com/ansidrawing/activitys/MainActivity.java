package mykola.devchallenge.com.ansidrawing.activitys;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import mykola.devchallenge.com.ansidrawing.ColorPickerDialog;
import mykola.devchallenge.com.ansidrawing.R;
import mykola.devchallenge.com.ansidrawing.SizePickerDialog;
import mykola.devchallenge.com.ansidrawing.SymbolPickerDialog;
import mykola.devchallenge.com.ansidrawing.callbacks.CallbackColor;
import mykola.devchallenge.com.ansidrawing.callbacks.CallbackSize;
import mykola.devchallenge.com.ansidrawing.callbacks.CallbackSymbol;
import mykola.devchallenge.com.ansidrawing.callbacks.CallbackUpdate;
import mykola.devchallenge.com.ansidrawing.helpers.DrawHelper;
import mykola.devchallenge.com.ansidrawing.views.CustomTextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        View.OnTouchListener, CallbackUpdate, CallbackColor, CallbackSymbol, CallbackSize {

    private static final String TAG = "MainActivity";
    private static final String COLOR_PICKER_DIALOG = "color_picker_dialog";
    private static final String SYMBOL_PICKER_DIALOG = "symbol_picker_dialog";
    private static final String SIZE_PICKER_DIALOG = "size_picker_dialog";
    private Button tollSizeButton;
    private ImageButton tollTypeButton;
    private Button tollColorButton;
    private Button tollSymbolButton;

    private FrameLayout layers;
    private CustomTextView canvas;
    private DrawHelper helper;

    private Context context;

    private ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onGlobalLayout() {
            float height = layers.getHeight();
            float width = layers.getWidth();
            helper = new DrawHelper(context, 19, Color.GREEN, 56, width, height);

            canvas = new CustomTextView(context, helper);
            canvas.setOnTouchListener((View.OnTouchListener) context);

            layers.addView(canvas);

            layers.getViewTreeObserver().removeOnGlobalLayoutListener(this);

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        tollSizeButton = (Button) findViewById(R.id.tool_size_btn);
        tollTypeButton = (ImageButton) findViewById(R.id.tool_type_btn);
        tollColorButton = (Button) findViewById(R.id.tool_color_btn);
        tollSymbolButton = (Button) findViewById(R.id.tool_symbol_btn);

        layers = (FrameLayout) findViewById(R.id.layers);
        layers.setOnTouchListener(this);
        layers.getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tool_size_btn:
                SizePickerDialog.newInstance(helper.getSizeTool(), helper.getSymbolTool())
                        .show(getSupportFragmentManager(), SYMBOL_PICKER_DIALOG);
                break;
            case R.id.tool_color_btn:
                ColorPickerDialog.newInstance(helper.getColorTool())
                        .show(getSupportFragmentManager(), COLOR_PICKER_DIALOG);
                break;
            case R.id.tool_symbol_btn:
                SymbolPickerDialog.newInstance(helper.getSizeTool())
                        .show(getSupportFragmentManager(), SIZE_PICKER_DIALOG);

                break;
            case R.id.tool_type_btn:
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
    public void update() {
        canvas.invalidate();
    }

    @Override
    public void setSelectedColor(int color) {
        helper.setColorTool(color);
        tollColorButton.setBackgroundColor(color);
    }

    @Override
    public void setSelectedSymbol(int symbol) {
        helper.setSymbolTool(symbol);
        tollSymbolButton.setText(Character.toString((char) symbol));
    }

    @Override
    public void setSelectedSize(int size) {
        helper.setSizeTool(size);
        tollSizeButton.setText(String.valueOf(size));
    }
}
