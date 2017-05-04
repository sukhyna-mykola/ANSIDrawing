package mykola.devchallenge.com.ansidrawing.models.tools;

import mykola.devchallenge.com.ansidrawing.models.Canvas;
import mykola.devchallenge.com.ansidrawing.models.ParametersTool;
import mykola.devchallenge.com.ansidrawing.models.Pixel;

/**
 * Created by mykola on 01.05.17.
 */

public class PencilTool extends Tool {


    public PencilTool(ParametersTool parametersTool) {
        super(parametersTool);

    }

    @Override
    public Pixel draw(int x, int y, Canvas canvas) {
        Pixel pixel = new Pixel(parametersTool.getSize(), parametersTool.getColor(), parametersTool.getSymbol(), x, y);
        canvas.setPixel(x, y, pixel);
        return pixel;
    }
}
