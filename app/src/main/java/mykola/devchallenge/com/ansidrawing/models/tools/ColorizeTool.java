package mykola.devchallenge.com.ansidrawing.models.tools;

import mykola.devchallenge.com.ansidrawing.R;
import mykola.devchallenge.com.ansidrawing.models.ParametersTool;
import mykola.devchallenge.com.ansidrawing.models.Pixel;
import mykola.devchallenge.com.ansidrawing.models.Surface;


public class ColorizeTool extends Tool {
    public static final String COLORIZE = "COLORIZE";

    public ColorizeTool(ParametersTool parametersTool) {
        super(parametersTool, R.drawable.colorize, COLORIZE);
    }

    public ColorizeTool() {
        super(R.drawable.colorize, COLORIZE);
    }

    @Override
    public Pixel draw(int x, int y, Surface surface) {
        return surface.getPixel(x, y);
    }
}
