package mykola.devchallenge.com.ansidrawing.helpers;

import mykola.devchallenge.com.ansidrawing.models.Pixel;
import mykola.devchallenge.com.ansidrawing.models.Point;
import mykola.devchallenge.com.ansidrawing.models.Surface;

/**
 * Created by mykola on 07.05.17.
 */

public class CropHelper {

    private static final int DELTA = 30;
    private Point ltMarker;
    private Point rbMarker;


    public CropHelper(Point ltMarker, Point rbMarker) {
        this.ltMarker = ltMarker;
        this.rbMarker = rbMarker;
    }

    public Surface crop(Surface surface) {
        int newWidth = rbMarker.getX() - ltMarker.getX();
        int newHeight = rbMarker.getY() - ltMarker.getY();

        Pixel[][] pixels = new Pixel[newWidth][newHeight];

        ParametersScreen.SCALE_HEIGHT = newHeight;
        ParametersScreen.SCALE_WIDTH = newWidth;

        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[0].length; j++) {
                Pixel p = surface.getPixel(i + ltMarker.getY(), j + ltMarker.getY());
                if (p != null)
                    pixels[i][j] = p.clone();

            }
        }

        return new Surface(pixels);
    }


    public Point getLtMarker() {
        return ltMarker;
    }

    public Point getRbMarker() {
        return rbMarker;
    }

    public void checkCorrectCoordinates(Point p) {
        if (p == ltMarker) {
            if (ltMarker.getX() > rbMarker.getX() - DELTA)
                ltMarker.setX(rbMarker.getX() - DELTA);


            if (ltMarker.getY() > rbMarker.getY() - DELTA)
                ltMarker.setY(rbMarker.getY() - DELTA);


        }
        if (p == rbMarker) {

            if (ltMarker.getX() + DELTA > rbMarker.getX())
                rbMarker.setX(ltMarker.getX() + DELTA);


            if (ltMarker.getY() + DELTA > rbMarker.getY())
                rbMarker.setY(ltMarker.getY() + DELTA);


        }



    }
}
