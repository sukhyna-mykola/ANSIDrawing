package mykola.devchallenge.com.ansidrawing.models.tools;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import mykola.devchallenge.com.ansidrawing.R;
import mykola.devchallenge.com.ansidrawing.models.Canvas;
import mykola.devchallenge.com.ansidrawing.models.ParametersTool;
import mykola.devchallenge.com.ansidrawing.models.Pixel;

/**
 * Created by mykola on 01.05.17.
 */

public class EraserTool extends Tool {
    public static final String ERASER = "ERASER";

    public EraserTool(ParametersTool parametersTool) {
        super(parametersTool, R.drawable.eraser, ERASER);
    }

    public EraserTool() {
        super(R.drawable.eraser, ERASER);
    }

    @Override
    public List<Pixel> draw(int x, int y, Canvas canvas) {
        List<Pixel> result = new ArrayList<>();

        double delta = parametersTool.getSizeTool() / 2.0;

        for (int i = (int) -delta; i < delta; i++) {
            for (int j = (int) -delta; j < delta; j++) {
                try {
                    Pixel del = canvas.getPixel(x + i, y + j);
                    if (del != null) {
                        result.add(del);
                        canvas.setPixel(x + i, y + j, null);
                    }

                } catch (Exception e) {
                    Log.d(TAG, "IndexOutOfBoundsException");
                }

            }
        }
        return result;
    }
}
