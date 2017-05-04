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

public class PencilTool extends Tool {
    public static final String PENCIL = "PENCIL";


    public PencilTool(ParametersTool parametersTool) {
        super(parametersTool, R.drawable.pencil, PENCIL);

    }

    public PencilTool() {
        super(R.drawable.pencil, PENCIL);
    }

    @Override
    public List<Pixel> draw(int x, int y, Canvas canvas) {
        List<Pixel> result = new ArrayList<>();

        double delta = parametersTool.getSizeTool() / 2.0;

        for (int i = (int) -delta; i < delta; i++) {
            for (int j = (int) -delta; j < delta; j++) {
                try {
                    Pixel p = new Pixel(parametersTool.getSizeSymbol(), parametersTool.getColor(), parametersTool.getSymbol(), x + i, y + j);
                    canvas.setPixel(x + i, y + j, p);
                    result.add(p);
                } catch (Exception e) {
                    Log.d(TAG, "IndexOutOfBoundsException");
                }

            }
        }
        return result;
    }
}
