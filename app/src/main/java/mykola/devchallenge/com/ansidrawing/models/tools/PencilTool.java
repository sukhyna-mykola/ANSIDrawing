package mykola.devchallenge.com.ansidrawing.models.tools;

import android.util.Log;

import mykola.devchallenge.com.ansidrawing.R;
import mykola.devchallenge.com.ansidrawing.models.ParametersTool;
import mykola.devchallenge.com.ansidrawing.models.Pixel;
import mykola.devchallenge.com.ansidrawing.models.Surface;

public class PencilTool extends Tool {
    public static final String PENCIL = "PENCIL";


    public PencilTool(ParametersTool parametersTool) {
        super(parametersTool, R.drawable.pencil, PENCIL);

    }

    public PencilTool() {
        super(R.drawable.pencil, PENCIL);
    }

    @Override
    public Pixel draw(int x, int y, Surface surface) {


        double delta = parametersTool.getSizeTool() / 2.0;

        for (int i = (int) -delta; i < delta; i++) {
            for (int j = (int) -delta; j < delta; j++) {
                try {
                    Pixel newPixel = new Pixel(parametersTool.getSizeSymbol(), parametersTool.getColor(), parametersTool.getSymbol(), x + i, y + j);
                    surface.setPixel(x + i, y + j, newPixel);

                } catch (Exception e) {
                    Log.d(TAG, "IndexOutOfBoundsException");
                }

            }
        }
        return surface.getPixel(x,y);
    }
}
