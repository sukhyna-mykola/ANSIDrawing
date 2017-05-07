package mykola.devchallenge.com.ansidrawing.activitys;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import mykola.devchallenge.com.ansidrawing.R;
import mykola.devchallenge.com.ansidrawing.callbacks.CallbackColor;
import mykola.devchallenge.com.ansidrawing.callbacks.CallbackCrop;
import mykola.devchallenge.com.ansidrawing.callbacks.CallbackFile;
import mykola.devchallenge.com.ansidrawing.callbacks.CallbackPreset;
import mykola.devchallenge.com.ansidrawing.callbacks.CallbackSize;
import mykola.devchallenge.com.ansidrawing.callbacks.CallbackSymbol;
import mykola.devchallenge.com.ansidrawing.callbacks.CallbackTool;
import mykola.devchallenge.com.ansidrawing.callbacks.CallbackUpdate;
import mykola.devchallenge.com.ansidrawing.dialogs.ColorPickerDialog;
import mykola.devchallenge.com.ansidrawing.dialogs.OpenDialog;
import mykola.devchallenge.com.ansidrawing.dialogs.PresetPickerDialog;
import mykola.devchallenge.com.ansidrawing.dialogs.SaveDialog;
import mykola.devchallenge.com.ansidrawing.dialogs.SymbolPickerDialog;
import mykola.devchallenge.com.ansidrawing.dialogs.SymbolSizePickerDialog;
import mykola.devchallenge.com.ansidrawing.dialogs.ToolPickerDialog;
import mykola.devchallenge.com.ansidrawing.dialogs.ToolSizePickerDialog;
import mykola.devchallenge.com.ansidrawing.helpers.DataHelper;
import mykola.devchallenge.com.ansidrawing.helpers.DrawHelper;
import mykola.devchallenge.com.ansidrawing.helpers.FileHelper;
import mykola.devchallenge.com.ansidrawing.models.Preset;
import mykola.devchallenge.com.ansidrawing.models.tools.Tool;
import mykola.devchallenge.com.ansidrawing.views.CropView;
import mykola.devchallenge.com.ansidrawing.views.CustomTextView;
import mykola.devchallenge.com.ansidrawing.views.PresetView;

import static mykola.devchallenge.com.ansidrawing.helpers.FileHelper.PERMISSION_REQUEST_CODE_READ;
import static mykola.devchallenge.com.ansidrawing.helpers.FileHelper.PERMISSION_REQUEST_CODE_WRITE;
import static mykola.devchallenge.com.ansidrawing.helpers.ParametersScreen.KOEF_HEIGHT;
import static mykola.devchallenge.com.ansidrawing.helpers.ParametersScreen.KOEF_WIDTH;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        View.OnTouchListener, CallbackUpdate, CallbackColor, CallbackSymbol, CallbackSize,
        CallbackCrop, CallbackTool, CallbackFile, CallbackPreset {

    private static final String TAG = "MainActivity";
    private static final String COLOR_PICKER_DIALOG = "color_picker_dialog";
    private static final String SYMBOL_PICKER_DIALOG = "symbol_picker_dialog";
    private static final String TOOL_SIZE_PICKER_DIALOG = "tool_size_picker_dialog";
    private static final String SYMBOL_SIZE_PICKER_DIALOG = "symbol_size_picker_dialog";
    private static final String TOOL_PICKER_DIALOG = "tool_picker_dialog";
    private static final String SAVE_DIALOG = "save_dialog";
    private static final String OPEN_DIALOG = "open_dialog";
    private static final String PRESET_PICER_DIALOG = "preset_picker_dialog";

    private TextView tollSizeTextView;
    private TextView symbolSizeTextView;
    private ImageView tollTypeImage;
    private ImageView tollColorImage;
    private TextView tollSymbolTextView;

    private RelativeLayout layers;

    private CropView cropView;
    private PresetView presetView;
    private CustomTextView customTextView;

    private DrawHelper helper;

    private Context context;


    private ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onGlobalLayout() {
            float height = layers.getHeight();
            float width = layers.getWidth();

            int symbolSize = 10;
            int color = DataHelper.get(context).getColors()[0];
            int symbol = DataHelper.get(context).getSymbols()[0];
            int toolSize = 1;

            helper = new DrawHelper(context, symbolSize, color, symbol, toolSize, width, height);

            customTextView = new CustomTextView(context, helper.getSurface());
            customTextView.setOnTouchListener((View.OnTouchListener) context);

            layers.addView(customTextView);

            updateViews();

            layers.getViewTreeObserver().removeOnGlobalLayoutListener(this);

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        tollSizeTextView = (TextView) findViewById(R.id.tool_size_text);
        tollTypeImage = (ImageView) findViewById(R.id.tool_type_image);
        tollColorImage = (ImageView) findViewById(R.id.tool_color_image);
        tollSymbolTextView = (TextView) findViewById(R.id.tool_symbol_text);
        symbolSizeTextView = (TextView) findViewById(R.id.symbol_size_text);

        layers = (RelativeLayout) findViewById(R.id.layers);
        layers.getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_undo:
                helper.undo();
                return true;
            case R.id.action_redo:
                helper.redo();
                return true;
            case R.id.action_open:
                if (FileHelper.getInstance(this).checkPermissions(Manifest.permission.READ_EXTERNAL_STORAGE))
                    OpenDialog.newInstance().show(getSupportFragmentManager(), OPEN_DIALOG);
                else FileHelper.getInstance(this).requestReadPermissions();

                return true;
            case R.id.action_save:
                if (FileHelper.getInstance(this).checkPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE))
                    SaveDialog.newInstance().show(getSupportFragmentManager(), SAVE_DIALOG);
                else FileHelper.getInstance(this).requestWritePermissions();
                return true;

        }
        return false;
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
                SymbolPickerDialog.newInstance(helper.getSymbolTool())
                        .show(getSupportFragmentManager(), SYMBOL_PICKER_DIALOG);

                break;
            case R.id.tool_type_btn:
                ToolPickerDialog.newInstance(helper.getTool().getName())
                        .show(getSupportFragmentManager(), TOOL_PICKER_DIALOG);
                break;

            case R.id.preset_btn:
                if (!helper.isActivePreset()) {
                    PresetPickerDialog.newInstance().show(getSupportFragmentManager(), PRESET_PICER_DIALOG);
                }
                break;

            case R.id.resize_btn:
                if (!helper.isActiveCrop()) {
                    helper.prepareCrop();
                    cropView = new CropView(context, helper);
                    layers.addView(cropView);
                }
                break;

        }

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                helper.newHistoryNote();
                return true;
            case MotionEvent.ACTION_MOVE:

                float x = event.getX();
                float y = event.getY();

                int convertY = (int) (y / KOEF_HEIGHT);
                int convertX = (int) (x / KOEF_WIDTH);

                helper.draw(convertX, convertY);
                return true;

            case MotionEvent.ACTION_UP:

                helper.endHistoryNote();
                return true;
            default:
                return false;
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE_READ && grantResults.length == 1)
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                OpenDialog.newInstance().show(getSupportFragmentManager(), OPEN_DIALOG);
            else
                Toast.makeText(context, R.string.storange_perm_isnt_granted, Toast.LENGTH_SHORT).show();


        if (requestCode == PERMISSION_REQUEST_CODE_WRITE && grantResults.length == 1)
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                SaveDialog.newInstance().show(getSupportFragmentManager(), SAVE_DIALOG);
            else
                Toast.makeText(context, R.string.storange_perm_isnt_granted, Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //Щоб показити діалог після підптвердженя PERMISSION.
        //Якщо визвати метод супер класа, виникає:
        //java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
    }

    @Override
    public void updateCanvas() {
        int count = layers.getChildCount();
        for (int i = 0; i < count; i++) {
            View v = layers.getChildAt(i);
            v.invalidate();
        }

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

    @Override
    public void setSelectedPreset(Preset preset) {
        helper.preparePreset(preset);
        presetView = new PresetView(context, helper);
        layers.addView(presetView);

    }

    @Override
    public void confirmPreset() {
        layers.removeView(presetView);
    }

    @Override
    public void cancelPreset() {
        layers.removeView(presetView);
    }

    @Override
    public void save(String fileName) {
        helper.save(fileName);
    }

    @Override
    public void load(String fileName) {
        helper.load(fileName);
        customTextView.setSurface(helper.getSurface());
        updateCanvas();
    }

    @Override
    public void confirmCrop() {
        customTextView.setSurface(helper.getSurface());
        layers.removeView(cropView);
        updateCanvas();
    }

    @Override
    public void cancelCrop() {
        layers.removeView(cropView);
    }
}
