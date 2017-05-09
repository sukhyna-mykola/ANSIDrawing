package mykola.devchallenge.com.ansidrawing.models.tools;

import android.util.Log;

import mykola.devchallenge.com.ansidrawing.R;
import mykola.devchallenge.com.ansidrawing.models.ParametersTool;
import mykola.devchallenge.com.ansidrawing.models.Pixel;
import mykola.devchallenge.com.ansidrawing.models.Surface;


public class EraserTool extends Tool {
    public static final String ERASER = "ERASER";

    public EraserTool(ParametersTool parametersTool) {
        super(parametersTool, R.drawable.eraser, ERASER);
    }

    public EraserTool() {
        super(R.drawable.eraser, ERASER);
    }

    @Override
    public Pixel draw(int x, int y, Surface surface) {


        double delta = parametersTool.getSizeTool() / 2.0;

        for (int i = (int) -delta; i < delta; i++) {
            for (int j = (int) -delta; j < delta; j++) {
                try {
                    surface.setPixel(x + i, y + j, null);
                } catch (Exception e) {
                    Log.d(TAG, "IndexOutOfBoundsException");
                }

            }
        }
        return surface.getPixel(x, y);
    }
}
