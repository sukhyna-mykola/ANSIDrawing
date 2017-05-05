package mykola.devchallenge.com.ansidrawing.helpers;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import mykola.devchallenge.com.ansidrawing.callbacks.CallbackUpdate;
import mykola.devchallenge.com.ansidrawing.models.HistoryNote;
import mykola.devchallenge.com.ansidrawing.models.ParametersScreen;
import mykola.devchallenge.com.ansidrawing.models.ParametersTool;
import mykola.devchallenge.com.ansidrawing.models.Preset;
import mykola.devchallenge.com.ansidrawing.models.Surface;
import mykola.devchallenge.com.ansidrawing.models.tools.PencilTool;
import mykola.devchallenge.com.ansidrawing.models.tools.Tool;
import mykola.devchallenge.com.ansidrawing.views.CustomTextView;

/**
 * Created by mykola on 01.05.17.
 */

public class DrawHelper {
    private List<Surface> surfaceList;
    private int activeCanvaspPosition;
    public Surface activeSurface;
    private HistoryNote activeNote;
    private Tool tool;

    private PresetHelper presetHelper;
    private ParametersTool parametersTool;
    private ParametersScreen parametersScreen;
    private CallbackUpdate updator;
    private Context context;

    private StringBuilder s = new StringBuilder();
    ;


    public DrawHelper(Context c, int sizeSymbol, int color, int symbol, int sizeTool, float width, float height) {
        this.context = c;
        updator = (CallbackUpdate) c;
        parametersTool = new ParametersTool(sizeSymbol, color, symbol, sizeTool);
        parametersScreen = new ParametersScreen(width, height);

        tool = new PencilTool(parametersTool);

        surfaceList = new ArrayList<>();

        activeSurface = new Surface(parametersScreen);
        activeNote = new HistoryNote("INIT", activeSurface);
        HistoryHelper.get().addNote(activeNote.clone());


        surfaceList.add(activeSurface);


        for (int i = 0; i < parametersScreen.getSCALE_HEIGHT() * parametersScreen.getSCALE_WIDTH(); i++) {
            s.append(" ");
        }

    }

    public void newHistoryNote() {
        activeNote = new HistoryNote(tool.getName(), activeSurface);
    }

    public void endHistoryNote() {
        HistoryHelper.get().addNote(activeNote.clone());
    }

    public void undo() {
        HistoryHelper.get().undo(getActiveSurface());
        updator.updateCanvas();
    }

    public void redo() {
        HistoryHelper.get().redo(getActiveSurface());
        updator.updateCanvas();
    }

    public void draw(float x, float y) {


        tool.draw((int) x, (int) y, getActiveSurface());

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


    private Surface getActiveSurface() {
        try {
            activeSurface = surfaceList.get(activeCanvaspPosition);

        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            activeSurface = null;
        }
        return activeSurface;
    }

    public CustomTextView preparePreset(Preset preset) {
        presetHelper = new PresetHelper(preset, parametersScreen, context);
        return presetHelper.getPresetView();
    }

    public void presetRotate() {
        presetHelper.rotate();

    }

    public void presetScalePlus() {
        presetHelper.scalePlus();

    }

    public void presetScaleMinus() {
        presetHelper.scaleMinus();

    }

    public void move(int x, int y) {
        presetHelper.move(x, y);

    }
}
