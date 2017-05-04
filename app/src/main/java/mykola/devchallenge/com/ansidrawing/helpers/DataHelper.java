package mykola.devchallenge.com.ansidrawing.helpers;

import android.content.Context;

import mykola.devchallenge.com.ansidrawing.R;
import mykola.devchallenge.com.ansidrawing.models.tools.BrushTool;
import mykola.devchallenge.com.ansidrawing.models.tools.ColorizeTool;
import mykola.devchallenge.com.ansidrawing.models.tools.EraserTool;
import mykola.devchallenge.com.ansidrawing.models.tools.PencilTool;
import mykola.devchallenge.com.ansidrawing.models.tools.Tool;

/**
 * Created by mykola on 04.05.17.
 */

public class DataHelper {
    private Context context;

    private int[] colors;
    private int[] symbols;
    private Tool [] tools;


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

        tools = new Tool[]{new PencilTool(),new EraserTool(),new BrushTool(),new ColorizeTool()};

    }
}
