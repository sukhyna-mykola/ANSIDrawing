package mykola.devchallenge.com.ansidrawing.models.tools;

import mykola.devchallenge.com.ansidrawing.models.Pixel;
import mykola.devchallenge.com.ansidrawing.models.Surface;
import mykola.devchallenge.com.ansidrawing.models.ParametersTool;

/**
 * Created by mykola on 01.05.17.
 */

public abstract class Tool {
    protected ParametersTool parametersTool;
    protected final String TAG = "Tool";

    protected int image;
    protected String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {

        return name;
    }

    public Tool(ParametersTool parametersTool, int image, String name) {

        this.parametersTool = parametersTool;
        this.image = image;
        this.name = name;
    }

    public Tool(int image, String name) {
        this.image = image;
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setParametersTool(ParametersTool parametersTool) {
        this.parametersTool = parametersTool;
    }

    public ParametersTool getParametersTool() {
        return parametersTool;
    }

    public Tool(ParametersTool parametersTool) {
        this.parametersTool = parametersTool;
    }

    public abstract Pixel draw(int x, int y, Surface surface);
}
