package mykola.devchallenge.com.ansidrawing.models;

import mykola.devchallenge.com.ansidrawing.models.tools.Tool;


public class Preset extends Tool {
    private Pixel[][] pixels;

    public Preset(Pixel[][] pixels, int image, String name) {
        super(image, name);
        this.pixels = pixels;
    }

    public void setPixels(Pixel[][] pixels) {
        this.pixels = pixels;
    }

    public Pixel[][] getPixels() {
        return pixels;
    }


    @Override
    public Pixel draw(int x, int y, Surface surface) {

        surface.clear();

        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[0].length; j++) {
                Pixel p = pixels[i][j];
                if (p != null) {
                    surface.setPixel(p.getX(), p.getY(), p);
                }

            }

        }

        return null;
    }

    public Preset clone() {

        int width = pixels.length;
        int height = pixels[0].length;

        Pixel[][] newPixels = new Pixel[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (pixels[i][j] != null)
                    newPixels[i][j] = pixels[i][j].clone();
            }

        }
        return new Preset(newPixels,image,name);
    }
}
