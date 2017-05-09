package mykola.devchallenge.com.ansidrawing.helpers;

import android.content.Context;

import mykola.devchallenge.com.ansidrawing.models.Pixel;
import mykola.devchallenge.com.ansidrawing.models.Preset;
import mykola.devchallenge.com.ansidrawing.models.Surface;
import mykola.devchallenge.com.ansidrawing.views.CustomTextView;


public class PresetHelper {

    public static final int D90 = 1;
    public static final int D360 = 4;

    public static final int SCALE_MIN = 0;
    public static final int SCALE_MAX = 4;

    private Surface presetSurface;

    private Preset preset;

    private int degress;
    private int scale;
    private int x, y;

    public Surface getPresetSurface() {
        return presetSurface;
    }

    public PresetHelper(Preset preset, ParametersScreen screen) {

        this.preset = preset.clone();

        presetSurface = new Surface(screen);

        this.x = screen.getSCALE_WIDTH() / 2;
        this.y = screen.getSCALE_HEIGHT() / 2;

        scalePlus();

    }

    public void move(int x, int y) {
        this.x = x;
        this.y = y;

        draw();

    }


    public void rotate() {
        degress++;

        if (degress > D360) ;
        degress = D90;

        preset.setPixels(rotateByDegrees(preset.getPixels(), degress));

        draw();

    }


    public void scalePlus() {
        this.scale++;
        if (this.scale <= SCALE_MAX)
            preset.setPixels(addPixelsScale(preset.getPixels()));
        else this.scale = SCALE_MAX;

        draw();


    }

    public void scaleMinus() {
        this.scale--;

        if (this.scale >= SCALE_MIN)
            preset.setPixels(removePixelsScale(preset.getPixels()));
        else this.scale = SCALE_MIN;

        draw();


    }

    private void draw() {

        normalizeCoordinates(x, y);
        preset.draw(x, y, presetSurface);

    }

    private Pixel[][] removePixelsScale(Pixel[][] modifiedPixels) {

       /* Pixel[][] result = new Pixel[modifiedPixels.length - 2 * scale][modifiedPixels[0].length - 2 * scale];

        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                result[i][j] = modifiedPixels[i + scale][j + scale];

            }

        }*/

        modifiedPixels = removeHorizontalPixels(modifiedPixels);
        modifiedPixels = removeVerticalPixels(modifiedPixels);
        return modifiedPixels;
    }

    /* private Pixel[][] addPixelsScale(Pixel[][] modifiedPixels) {


         Pixel[][] result = new Pixel[modifiedPixels.length + 2][modifiedPixels[0].length + 2];


         for (int i = 0; i < modifiedPixels.length; i++) {
             for (int j = 0; j < modifiedPixels[0].length; j++) {
                 result[i + 1][j + 1] = modifiedPixels[i][j];
                 if (pattern == null && modifiedPixels[i][j] != null)
                     pattern = modifiedPixels[i][j];
             }
         }
         for (int i = 0; i < result.length; i++) {
             for (int j = 0; j < result[0].length; j++) {
                 if (i == 0 || j == 0 || i == result.length - 1 || j == result[0].length - 1) {
                     result[i][j] = modifiedPixels[i][j];
                 }


             }
         }
         return result;

     }*/
    private Pixel[][] addPixelsScale(Pixel[][] modifiedPixels) {

        modifiedPixels = addHorizontalPixels(modifiedPixels);
        modifiedPixels = addVerticalPixels(modifiedPixels);

        return modifiedPixels;

    }

    private Pixel[][] addVerticalPixels(Pixel[][] modifiedPixels) {
        Pixel[][] result = new Pixel[modifiedPixels.length][modifiedPixels[0].length * 2];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                if (modifiedPixels[i][j / 2] != null)
                    result[i][j] = modifiedPixels[i][j / 2].clone();

            }

        }
        return result;
    }

    private Pixel[][] addHorizontalPixels(Pixel[][] modifiedPixels) {
        Pixel[][] result = new Pixel[modifiedPixels.length * 2][modifiedPixels[0].length];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                if (modifiedPixels[i / 2][j] != null)
                    result[i][j] = modifiedPixels[i / 2][j].clone();

            }

        }
        return result;
    }

    private Pixel[][] removeVerticalPixels(Pixel[][] modifiedPixels) {
        Pixel[][] result = new Pixel[modifiedPixels.length][modifiedPixels[0].length / 2];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                if (modifiedPixels[i][j * 2] != null)
                    result[i][j] = modifiedPixels[i][j * 2].clone();

            }

        }
        return result;
    }

    private Pixel[][] removeHorizontalPixels(Pixel[][] modifiedPixels) {
        Pixel[][] result = new Pixel[modifiedPixels.length / 2][modifiedPixels[0].length];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                if (modifiedPixels[i * 2][j] != null)
                    result[i][j] = modifiedPixels[i * 2][j].clone();

            }

        }
        return result;
    }

    private void normalizeCoordinates(int x, int y) {

        for (int i = 0; i < preset.getPixels().length; i++) {
            for (int j = 0; j < preset.getPixels()[0].length; j++) {
                Pixel p = preset.getPixels()[i][j];
                if (p != null) {
                    p.setX(i + x - preset.getPixels().length / 2);
                    p.setY(j + y - preset.getPixels()[0].length / 2);
                }
            }

        }
    }


    private Pixel[][] rotateByDegrees(Pixel[][] pixels, int degrees) {
        for (int i = 0; i < degrees; i++) {
            pixels = transposeMatrix(pixels);
            pixels = reverseRows(pixels);
        }

        return pixels;
    }

    private Pixel[][] transposeMatrix(Pixel[][] pixels) {
        Pixel[][] result = new Pixel[pixels[0].length][pixels.length];
        for (int i = 0; i < pixels.length; i++)
            for (int j = 0; j < pixels[0].length; j++)
                result[j][i] = pixels[i][j];
        return result;
    }

    private Pixel[][] reverseRows(Pixel[][] pixels) {
        Pixel[][] result = new Pixel[pixels.length][pixels[0].length];

        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[0].length; j++)
                result[i][j] = pixels[pixels.length - i - 1][j];

        }

        return result;
    }

}
