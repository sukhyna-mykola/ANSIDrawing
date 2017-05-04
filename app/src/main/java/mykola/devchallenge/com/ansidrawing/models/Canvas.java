package mykola.devchallenge.com.ansidrawing.models;

/**
 * Created by mykola on 01.05.17.
 */

public class Canvas {
    private Pixel[][] pixels;

    public Canvas(Pixel[][] pixels) {
        this.pixels = pixels;
    }

    public Canvas() {
    }
    public Canvas(ParametersScreen parametersScreen) {
        pixels = new Pixel[parametersScreen.getSCALE_WIDTH()][parametersScreen.getSCALE_HEIGHT()];
    }

    public void setPixel(int x, int y, Pixel newPixel) {
        pixels[x][y] = newPixel;
    }

    public Pixel getPixel(int x, int y) {
        try {
            return pixels[x][y];
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }
}
