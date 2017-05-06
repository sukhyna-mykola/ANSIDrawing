package mykola.devchallenge.com.ansidrawing.helpers;

import android.content.Context;

import org.json.JSONException;

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

    private HistoryNote activeNote;
    private Tool tool;

    private PresetHelper presetHelper;
    private FileHelper fileHelper;
    private ParametersTool parametersTool;
    private ParametersScreen parametersScreen;
    private CallbackUpdate updator;
    private Context context;

    private Surface surface;

    private StringBuilder s = new StringBuilder();
    ;


    public Surface getSurface() {
        return surface;
    }

    public DrawHelper(Context c, int sizeSymbol, int color, int symbol, int sizeTool, float width, float height) {
        this.context = c;
        updator = (CallbackUpdate) c;

        parametersTool = new ParametersTool(sizeSymbol, color, symbol, sizeTool);
        parametersScreen = new ParametersScreen(width, height);

        fileHelper = FileHelper.getInstance(context);

        tool = new PencilTool(parametersTool);

        surface = new Surface(parametersScreen);

        activeNote = new HistoryNote("INIT", surface);

        HistoryHelper.get().addNote(activeNote.clone());

    }

    public void newHistoryNote() {
        activeNote = new HistoryNote(tool.getName(), surface);
    }

    public void endHistoryNote() {
        HistoryHelper.get().addNote(activeNote.clone());
    }

    public void undo() {
        HistoryHelper.get().undo(surface);
        updator.updateCanvas();
    }

    public void redo() {
        HistoryHelper.get().redo(surface);
        updator.updateCanvas();
    }

    public void draw(float x, float y) {

        tool.draw((int) x, (int) y, surface);
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


    public void preparePreset(Preset preset) {
        presetHelper = new PresetHelper(preset, parametersScreen, context);

        updator.updateViews();
        updator.updateCanvas();
    }

    public void presetConfirm() {

        Surface presetSurface = presetHelper.getPresetSurface();
        for (int i = 0; i < presetSurface.getWidth(); i++) {
            for (int j = 0; j < presetSurface.getHeight(); j++) {
                if (presetSurface.getPixel(i, j) != null && surface.getPixel(i, j) == null)
                    surface.setPixel(i, j, presetSurface.getPixel(i, j));
            }

        }

        activeNote = new HistoryNote("PRESET", surface);

        HistoryHelper.get().addNote(activeNote.clone());

        presetHelper.isActive = false;

        updator.updateViews();
        updator.updateCanvas();

    }


    public CustomTextView getPresetView() {
        if (presetHelper != null)
            return presetHelper.getPresetView();
        else return null;
    }

    public boolean isActivePreset() {
        return presetHelper.isActive;
    }

    public void presetCancel() {
        presetHelper.isActive = false;

        updator.updateViews();
        updator.updateCanvas();

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


    public void save(String fileName) {
        try {
            fileHelper.save(surface, fileName);
        } catch (JSONException e) {
            e.printStackTrace();

        }
    }

    public void load(String s) {
        try {
            Surface loaded = fileHelper.load(s);
            for (int i = 0; i < surface.getWidth(); i++) {
                for (int j = 0; j < surface.getHeight(); j++) {
                    surface.setPixel(i, j, loaded.getPixel(i, j));
                }

            }
            activeNote = new HistoryNote("IMPORT", surface);

            HistoryHelper.get().addNote(activeNote.clone());
            updator.updateCanvas();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
