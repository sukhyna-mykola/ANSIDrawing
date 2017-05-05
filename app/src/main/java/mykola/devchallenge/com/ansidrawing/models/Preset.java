package mykola.devchallenge.com.ansidrawing.models;

import mykola.devchallenge.com.ansidrawing.models.tools.Tool;

/**
 * Created by mykola on 05.05.17.
 */

public class Preset {
    private Pixel[][] pixels;

    public Tool getTool() {
        return tool;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }

    public Preset(Pixel[][] pixels, Tool tool) {

        this.pixels = pixels;
        this.tool = tool;
    }

    private Tool tool;

    public Pixel[][] getPixels() {
        return pixels;
    }

    public void setPixels(Pixel[][] pixels) {
        this.pixels = pixels;
    }

}
