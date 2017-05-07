package mykola.devchallenge.com.ansidrawing.helpers;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONException;

import mykola.devchallenge.com.ansidrawing.callbacks.CallbackCrop;
import mykola.devchallenge.com.ansidrawing.callbacks.CallbackPreset;
import mykola.devchallenge.com.ansidrawing.callbacks.CallbackUpdate;
import mykola.devchallenge.com.ansidrawing.models.HistoryNote;
import mykola.devchallenge.com.ansidrawing.models.ParametersTool;
import mykola.devchallenge.com.ansidrawing.models.Pixel;
import mykola.devchallenge.com.ansidrawing.models.Point;
import mykola.devchallenge.com.ansidrawing.models.Preset;
import mykola.devchallenge.com.ansidrawing.models.Surface;
import mykola.devchallenge.com.ansidrawing.models.tools.ColorizeTool;
import mykola.devchallenge.com.ansidrawing.models.tools.PencilTool;
import mykola.devchallenge.com.ansidrawing.models.tools.Tool;

/**
 * Created by mykola on 01.05.17.
 */

public class DrawHelper {

    private HistoryNote activeNote;
    private Tool tool;

    private PresetHelper presetHelper;
    private FileHelper fileHelper;
    private CropHelper cropHelper;

    private ParametersTool parametersTool;
    private ParametersScreen parametersScreen;

    private CallbackUpdate updator;
    private CallbackPreset callbackPreset;
    private CallbackCrop callbackCrop;

    private Context context;

    private Surface surface;

    private int oldX, oldY;


    public Surface getSurface() {
        return surface;
    }

    public DrawHelper(Context c, int sizeSymbol, int color, int symbol, int sizeTool, float width, float height) {
        this.context = c;


        updator = (CallbackUpdate) c;
        callbackCrop = (CallbackCrop) c;
        callbackPreset = (CallbackPreset) c;


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

    public void draw(int x, int y) {

        if (oldX != x || oldY != x) {

            oldX = x;
            oldY = x;

            Pixel pixel = tool.draw(x, y, surface);

            if (pixel != null && tool.getName().equals(ColorizeTool.COLORIZE)) {
                setColorTool(pixel.getColor());
                updator.updateViews();
            }
            updator.updateCanvas();
        }
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
        presetHelper = new PresetHelper(preset, parametersScreen);
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

        presetHelper = null;
        callbackPreset.confirmPreset();
        updator.updateCanvas();

    }

    public void presetCancel() {
        presetHelper = null;
        callbackPreset.cancelPreset();
    }


    public void move(int x, int y) {
        if (oldX != x || oldY != x) {

            oldX = x;
            oldY = x;

            presetHelper.move(x, y);
        }

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
            surface = fileHelper.load(s);

            ParametersScreen.SCALE_HEIGHT = surface.getHeight();
            ParametersScreen.SCALE_WIDTH = surface.getWidth();

            activeNote = new HistoryNote("OPEN", surface);

            HistoryHelper.get().addNote(activeNote.clone());
            updator.updateCanvas();

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
        }
    }

    public CropHelper getCropHelper() {
        return cropHelper;
    }


    public void prepareCrop() {
        cropHelper = new CropHelper(new Point(1, 1), new Point(surface.getWidth() - 1, surface.getHeight() - 1));
    }

    public void confirmCrop() {
        surface = cropHelper.crop(surface);
        cropHelper = null;

        callbackCrop.confirmCrop();
    }

    public void cancelCrop() {

        cropHelper = null;
        callbackCrop.cancelCrop();
    }


    public PresetHelper getPresetHelper() {
        return presetHelper;
    }

    public boolean isActivePreset() {
        return presetHelper != null;
    }


    public boolean isActiveCrop() {
        return cropHelper != null;
    }
}
