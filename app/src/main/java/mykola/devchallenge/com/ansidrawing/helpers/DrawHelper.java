package mykola.devchallenge.com.ansidrawing.helpers;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;

import java.util.ArrayList;
import java.util.List;

import mykola.devchallenge.com.ansidrawing.callbacks.CallbackUpdate;
import mykola.devchallenge.com.ansidrawing.models.Canvas;
import mykola.devchallenge.com.ansidrawing.models.ParametersScreen;
import mykola.devchallenge.com.ansidrawing.models.ParametersTool;
import mykola.devchallenge.com.ansidrawing.models.tools.PencilTool;
import mykola.devchallenge.com.ansidrawing.models.tools.Tool;

/**
 * Created by mykola on 01.05.17.
 */

public class DrawHelper {
    private List<Canvas> canvasList;
    private int activeCanvaspPosition;
    public  Canvas activeCanvas;
    private Tool tool;
    private ParametersTool parametersTool;
    private ParametersScreen parametersScreen;
    private CallbackUpdate updator;

    private StringBuilder s = new StringBuilder();
    ;


    public DrawHelper(Context c, int size, int color, int symbol, float width, float height) {

        updator = (CallbackUpdate) c;
        parametersTool = new ParametersTool(size, color, symbol);
        parametersScreen = new ParametersScreen(width, height);

        tool = new PencilTool(parametersTool);

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

        updator.update();
    }



    public void setSizeTool(int size) {
        parametersTool.setSize(size);
    }

    public void setColorTool(int color) {
        parametersTool.setColor(color);
    }

    public void setSymbolTool(int symbol) {
        parametersTool.setSymbol(symbol);
    }

    public int getSizeTool() {
        return parametersTool.getSize();
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
