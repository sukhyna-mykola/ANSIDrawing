package mykola.devchallenge.com.ansidrawing.helpers;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import mykola.devchallenge.com.ansidrawing.callbacks.CallbackUpdate;
import mykola.devchallenge.com.ansidrawing.models.Canvas;
import mykola.devchallenge.com.ansidrawing.models.ParametersScreen;
import mykola.devchallenge.com.ansidrawing.models.ParametersTool;
import mykola.devchallenge.com.ansidrawing.models.tools.BrushTool;
import mykola.devchallenge.com.ansidrawing.models.tools.PencilTool;
import mykola.devchallenge.com.ansidrawing.models.tools.Tool;

/**
 * Created by mykola on 01.05.17.
 */

public class DrawHelper {
    private List<Canvas> canvasList;
    private int activeCanvaspPosition;
    public Canvas activeCanvas;
    private Tool tool;
    private ParametersTool parametersTool;
    private ParametersScreen parametersScreen;
    private CallbackUpdate updator;

    private StringBuilder s = new StringBuilder();
    ;


    public DrawHelper(Context c, int sizeSymbol, int color, int symbol, int sizeTool, float width, float height) {

        updator = (CallbackUpdate) c;
        parametersTool = new ParametersTool(sizeSymbol, color, symbol, sizeTool);
        parametersScreen = new ParametersScreen(width, height);

        tool = new BrushTool(parametersTool);

        canvasList = new ArrayList<>();
        activeCanvas = new Canvas(parametersScreen);
        canvasList.add(activeCanvas);


        for (int i = 0; i < parametersScreen.getSCALE_HEIGHT() * parametersScreen.getSCALE_WIDTH(); i++) {
            s.append(" ");
        }

    }

    public void draw(float x, float y) {
        int convertY = (int) (y / parametersScreen.getKOEF_HEIGHT());
        int convertX = (int) (x / parametersScreen.getKOEF_WIDTH());

        tool.draw(convertX, convertY, getActiveCanvas());

        updator.updateCanvas();
    }

    public Tool getTool() {
        return tool;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }

    public void prepareTool(Tool tool) {
        this.tool = tool;
        this.tool.setParametersTool(parametersTool);
    }

    public void setSizeTool(int size) {
        parametersTool.setSizeTool(size);
    }

    public void setSizeSymbol(int size) {
        parametersTool.setSizeSymbol(size);
    }

    public void setColorTool(int color) {
        parametersTool.setColor(color);
    }

    public void setSymbolTool(int symbol) {
        parametersTool.setSymbol(symbol);
    }

    public int getSizeSymbol() {
        return parametersTool.getSizeSymbol();
    }

    public int getSizeTool() {
        return parametersTool.getSizeTool();
    }

    public int getColorTool() {
        return parametersTool.getColor();
    }

    public int getSymbolTool() {
        return parametersTool.getSymbol();
    }


    private Canvas getActiveCanvas() {
        try {
            activeCanvas = canvasList.get(activeCanvaspPosition);

        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            activeCanvas = null;
        }
        return activeCanvas;
    }
}
