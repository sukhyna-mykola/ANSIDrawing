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


public class DataHelper {

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

        Pixel[][] tmp = new Pixel[3][5];
        tmp[0][2] = new Pixel(10, Color.RED, 57, 0, 2);
        tmp[1][1] = new Pixel(10, Color.RED, 57, 1, 1);
        tmp[1][2] = new Pixel(10, Color.RED, 57, 1, 2);
        tmp[1][3] = new Pixel(10, Color.RED, 57, 1, 3);
        tmp[2][0] = new Pixel(10, Color.RED, 57, 2, 0);
        tmp[2][1] = new Pixel(10, Color.RED, 57, 2, 1);
        tmp[2][2] = new Pixel(10, Color.RED, 57, 2, 2);
        tmp[2][3] = new Pixel(10, Color.RED, 57, 2, 3);
        tmp[2][4] = new Pixel(10, Color.RED, 57, 2, 4);
        Preset triangle = new Preset(tmp, R.drawable.triangle, context.getString(R.string.triangle));

        tmp = new Pixel[2][2];
        tmp[0][0] = new Pixel(10, Color.RED, 60, 0, 0);
        tmp[0][1] = new Pixel(10, Color.GREEN, 60, 0, 1);
        tmp[1][0] = new Pixel(10, Color.BLACK, 60, 1, 0);
        tmp[1][1] = new Pixel(10, Color.BLUE, 60, 1, 1);
        Preset rectangle = new Preset(tmp, R.drawable.rectangle, context.getString(R.string.rectangle));

        tmp = new Pixel[3][3];
        tmp[0][1] = new Pixel(10, Color.BLUE, 63, 0, 1);
        tmp[1][0] = new Pixel(10, Color.BLACK, 63, 1, 0);
        tmp[1][1] = new Pixel(10, Color.BLUE, 60, 1, 1);
        tmp[1][2] = new Pixel(10, Color.YELLOW, 63, 1, 2);
        tmp[2][1] = new Pixel(10, Color.GRAY, 63, 2, 1);
        Preset plus = new Preset(tmp, R.drawable.plus, context.getString(R.string.plus));

        tmp = new Pixel[5][5];
        tmp[0][0] = new Pixel(10, Color.RED, 63, 0, 0);
        tmp[1][1] = new Pixel(10, Color.BLUE, 60, 1, 1);
        tmp[2][2] = new Pixel(10, Color.GREEN, 63, 2, 2);

        tmp[0][4] = new Pixel(10, Color.RED, 63, 0, 4);
        tmp[1][3] = new Pixel(10, Color.BLUE, 60, 1, 3);
        tmp[3][1] = new Pixel(10, Color.GREEN, 63, 3, 1);

        tmp[4][0] = new Pixel(10, Color.RED, 63, 4, 0);
        tmp[3][3] = new Pixel(10, Color.BLUE, 60, 3, 3);
        tmp[4][4] = new Pixel(10, Color.GREEN, 63, 4, 4);

        Preset cross = new Preset(tmp, R.drawable.cancel, context.getString(R.string.cross));


        presets = new Preset[]{triangle, rectangle, plus, cross};
    }
}
