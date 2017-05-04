package mykola.devchallenge.com.ansidrawing.models.tools;

import mykola.devchallenge.com.ansidrawing.models.Canvas;
import mykola.devchallenge.com.ansidrawing.models.ParametersTool;
import mykola.devchallenge.com.ansidrawing.models.Pixel;

/**
 * Created by mykola on 01.05.17.
 */

public abstract class Tool{
    protected ParametersTool parametersTool;

    public void setParametersTool(ParametersTool parametersTool) {
        this.parametersTool = parametersTool;
    }

    public ParametersTool getParametersTool() {
        return parametersTool;
    }

    public Tool(ParametersTool parametersTool) {
        this.parametersTool = parametersTool;
    }

    public abstract Pixel draw(int x, int y, Canvas canvas);
}
