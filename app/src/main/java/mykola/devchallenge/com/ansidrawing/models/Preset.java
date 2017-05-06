package mykola.devchallenge.com.ansidrawing.models;

import mykola.devchallenge.com.ansidrawing.models.tools.Tool;

/**
 * Created by mykola on 05.05.17.
 */

public class Preset extends Tool {
    private Pixel[][] pixels;

    public Preset(Pixel[][] pixels, int image, String name) {
        super(image, name);
        this.pixels = pixels;
    }


    public Pixel[][] getPixels() {
        return pixels;
    }


    @Override
    public Pixel draw(int x, int y, Surface surface) {
        return null;
    }
}
