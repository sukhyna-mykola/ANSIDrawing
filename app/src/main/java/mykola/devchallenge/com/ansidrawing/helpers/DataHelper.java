package mykola.devchallenge.com.ansidrawing.helpers;

import android.content.Context;
import android.graphics.Color;

import mykola.devchallenge.com.ansidrawing.R;
import mykola.devchallenge.com.ansidrawing.models.Pixel;
import mykola.devchallenge.com.ansidrawing.models.Preset;
import mykola.devchallenge.com.ansidrawing.models.tools.BrushTool;
import mykola.devchallenge.com.ansidrawing.models.tools.ColorizeTool;
import mykola.devchallenge.com.ansidrawing.models.tools.EraserTool;
import mykola.devchallenge.com.ansidrawing.models.tools.PencilTool;
import mykola.devchallenge.com.ansidrawing.models.tools.Tool;

/**
 * Created by mykola on 04.05.17.
 */

public class DataHelper {

    private Pixel[][] rti;
    private Context context;

    private int[] colors;
    private int[] symbols;
    private Tool[] tools;
    private Preset[] presets;


    public static DataHelper dataHelper;

    public static DataHelper get(Context context) {
        if (dataHelper == null)
            dataHelper = new DataHelper(context);
        return dataHelper;
    }

    private DataHelper(Context context) {
        this.context = context;
        loadResourses();
    }

    public int[] getColors() {
        return colors;
    }

    public int[] getSymbols() {
        return symbols;
    }

    public Tool[] getTools() {
        return tools;
    }

    public void loadResourses() {
        colors = context.getResources().getIntArray(R.array.colors);

        symbols = context.getResources().getIntArray(R.array.symbols);

        tools = new Tool[]{new PencilTool(), new EraserTool(), new BrushTool(), new ColorizeTool()};

        initPresets();


    }

    public Preset[] getPresets() {
        return presets;
    }

    private void initPresets() {

        rti = new Pixel[3][5];
        rti[0][2] = new Pixel(10, Color.RED, 57, 0, 2);
        rti[1][1] = new Pixel(10, Color.RED, 57, 1, 1);
        rti[1][2] = new Pixel(10, Color.RED, 57, 1, 2);
        rti[1][3] = new Pixel(10, Color.RED, 57, 1, 3);
        rti[2][0] = new Pixel(10, Color.RED, 57, 2, 0);
        rti[2][1] = new Pixel(10, Color.RED, 57, 2, 1);
        rti[2][2] = new Pixel(10, Color.RED, 57, 2, 2);
        rti[2][3] = new Pixel(10, Color.RED, 57, 2, 3);
        rti[2][4] = new Pixel(10, Color.RED, 57, 2, 4);

        presets = new Preset[]{new Preset(rti, new PencilTool())};
    }
}
