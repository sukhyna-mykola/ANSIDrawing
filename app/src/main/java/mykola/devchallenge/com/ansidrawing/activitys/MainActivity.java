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
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import mykola.devchallenge.com.ansidrawing.R;
import mykola.devchallenge.com.ansidrawing.callbacks.CallbackColor;
import mykola.devchallenge.com.ansidrawing.callbacks.CallbackFile;
import mykola.devchallenge.com.ansidrawing.callbacks.CallbackSize;
import mykola.devchallenge.com.ansidrawing.callbacks.CallbackSymbol;
import mykola.devchallenge.com.ansidrawing.callbacks.CallbackTool;
import mykola.devchallenge.com.ansidrawing.callbacks.CallbackUpdate;
import mykola.devchallenge.com.ansidrawing.dialogs.ColorPickerDialog;
import mykola.devchallenge.com.ansidrawing.dialogs.OpenDialog;
import mykola.devchallenge.com.ansidrawing.dialogs.SaveDialog;
import mykola.devchallenge.com.ansidrawing.dialogs.SymbolPickerDialog;
import mykola.devchallenge.com.ansidrawing.dialogs.SymbolSizePickerDialog;
import mykola.devchallenge.com.ansidrawing.dialogs.ToolPickerDialog;
import mykola.devchallenge.com.ansidrawing.dialogs.ToolSizePickerDialog;
import mykola.devchallenge.com.ansidrawing.helpers.DataHelper;
import mykola.devchallenge.com.ansidrawing.helpers.DrawHelper;
import mykola.devchallenge.com.ansidrawing.helpers.FileHelper;
import mykola.devchallenge.com.ansidrawing.models.tools.Tool;
import mykola.devchallenge.com.ansidrawing.views.CustomTextView;

import static mykola.devchallenge.com.ansidrawing.helpers.FileHelper.PERMISSION_REQUEST_CODE;
import static mykola.devchallenge.com.ansidrawing.models.ParametersScreen.KOEF_HEIGHT;
import static mykola.devchallenge.com.ansidrawing.models.ParametersScreen.KOEF_WIDTH;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        View.OnTouchListener, CallbackUpdate, CallbackColor, CallbackSymbol, CallbackSize, CallbackTool, CallbackFile {

    private static final String TAG = "MainActivity";
    private static final String COLOR_PICKER_DIALOG = "color_picker_dialog";
    private static final String SYMBOL_PICKER_DIALOG = "symbol_picker_dialog";
    private static final String TOOL_SIZE_PICKER_DIALOG = "tool_size_picker_dialog";
    private static final String SYMBOL_SIZE_PICKER_DIALOG = "symbol_size_picker_dialog";
    private static final String TOOL_PICKER_DIALOG = "tool_picker_dialog";
    private static final String SAVE_DIALOG = "save_dialog";
    private static final String OPEN_DIALOG = "open_dialog";

    private TextView tollSizeTextView;
    private TextView symbolSizeTextView;
    private ImageView tollTypeImage;
    private ImageView tollColorImage;
    private TextView tollSymbolTextView;

    private FrameLayout layers;
    private GridLayout presetLayout;
    private DrawHelper helper;
    private DataHelper dataHelper;
    private CustomTextView surfase;

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
            int toolSize = 1;

            helper = new DrawHelper(context, symbolSize, color, symbol, toolSize, width, height);

            surfase = new CustomTextView(context, helper.getSurface(), 100);
            layers.addView(surfase);

            updateViews();

            layers.getViewTreeObserver().removeOnGlobalLayoutListener(this);

        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.length == 2) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, permissions[0], Toast.LENGTH_SHORT).show();
                //showExtDirFilesCount();
            }
            if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, permissions[1], Toast.LENGTH_SHORT).show();
                // showUnreadSmsCount();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


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

        presetLayout = (GridLayout) findViewById(R.id.presetLayout);

        layers = (FrameLayout) findViewById(R.id.layers);
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
                else FileHelper.getInstance(this).requestMultiplePermissions();

                return true;
            case R.id.action_save:
                if (FileHelper.getInstance(this).checkPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE))
                    SaveDialog.newInstance().show(getSupportFragmentManager(), SAVE_DIALOG);
                else FileHelper.getInstance(this).requestMultiplePermissions();
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
                SymbolPickerDialog.newInstance(helper.getSizeTool())
                        .show(getSupportFragmentManager(), SYMBOL_PICKER_DIALOG);

                break;
            case R.id.tool_type_btn:
                ToolPickerDialog.newInstance(helper.getTool().getName())
                        .show(getSupportFragmentManager(), TOOL_PICKER_DIALOG);

            case R.id.preset_btn:

                helper.preparePreset(DataHelper.get(context).getPresets()[0]);
                layers.addView(helper.getPresetView());
                break;
            case R.id.presetCancel:
                layers.removeView(helper.getPresetView());
                helper.presetCancel();
                break;
            case R.id.presetConfirm:
                layers.removeView(helper.getPresetView());
                helper.presetConfirm();
                break;
            case R.id.presetScaleMinus:
                helper.presetScaleMinus();
                break;
            case R.id.presetScalePlus:
                helper.presetScalePlus();
                break;
            case R.id.presetRotate:
                helper.presetRotate();
                break;

        }
    }


    private int oldX, oldY;

    @Override
    public boolean onTouch(View v, MotionEvent event) {


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (helper.isActivePreset()) ;
                else
                    helper.newHistoryNote();
                return true;
            case MotionEvent.ACTION_MOVE:

                float x = event.getX();
                float y = event.getY();
                int convertY = (int) (y / KOEF_HEIGHT);
                int convertX = (int) (x / KOEF_WIDTH);
                if (oldX != convertX || oldY != convertY) {
                    oldX = convertX;
                    oldY = convertY;

                    if (helper.isActivePreset())
                        helper.move(convertX, convertY);
                    else
                        helper.draw(convertX, convertY);
                    return true;
                }
                return false;
            case MotionEvent.ACTION_UP:
                if (helper.isActivePreset()) ;
                else
                    helper.endHistoryNote();
                return true;
            default:
                return false;
        }


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

        if (helper.isActivePreset()) {
            presetLayout.setVisibility(View.VISIBLE);
        } else presetLayout.setVisibility(View.GONE);
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
    public void save(String fileName) {
        helper.save(fileName);
    }

    @Override
    public void load(String fileName) {
        helper.load(fileName);
    }
}
