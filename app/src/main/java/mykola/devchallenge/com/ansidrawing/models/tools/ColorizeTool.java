package mykola.devchallenge.com.ansidrawing.models.tools;

import java.util.ArrayList;
import java.util.List;

import mykola.devchallenge.com.ansidrawing.R;
import mykola.devchallenge.com.ansidrawing.models.Surface;
import mykola.devchallenge.com.ansidrawing.models.ChangedPixels;
import mykola.devchallenge.com.ansidrawing.models.ParametersTool;
import mykola.devchallenge.com.ansidrawing.models.Pixel;

/**
 * Created by mykola on 01.05.17.
 */

public class ColorizeTool extends Tool {
    public static final String COLORIZE = "COLORIZE";

    public ColorizeTool(ParametersTool parametersTool) {
        super(parametersTool, R.drawable.colorize, COLORIZE);
    }

    public ColorizeTool() {
        super(R.drawable.colorize, COLORIZE);
    }

    @Override
    public ChangedPixels draw(int x, int y, Surface surface) {
        List<Pixel> result = new ArrayList<>();

        Pixel p = surface.getPixel(x,y);

        result.add(p);

        return new ChangedPixels(result,result);
    }
}
