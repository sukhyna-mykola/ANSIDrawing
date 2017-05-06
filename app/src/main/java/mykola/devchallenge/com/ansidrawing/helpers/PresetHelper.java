package mykola.devchallenge.com.ansidrawing.helpers;

import android.content.Context;

import mykola.devchallenge.com.ansidrawing.models.ParametersScreen;
import mykola.devchallenge.com.ansidrawing.models.Pixel;
import mykola.devchallenge.com.ansidrawing.models.Preset;
import mykola.devchallenge.com.ansidrawing.models.Surface;
import mykola.devchallenge.com.ansidrawing.models.tools.Tool;
import mykola.devchallenge.com.ansidrawing.views.CustomTextView;

/**
 * Created by mykola on 05.05.17.
 */

public class PresetHelper {

    public static final int D90 = 1;
    public static final int D360 = 4;

    public static final int SCALE_MIN = 0;
    public static final int SCALE_MAX = 4;

    private CustomTextView presetView;
    private Tool tool;
    public static boolean isActive;
    private Surface presetSurface;
    private Pixel[][] modifiedPixels;

    private int degress;
    private int scale;
    private int x, y;

    public Surface getPresetSurface() {
        return presetSurface;
    }

    public PresetHelper(Preset preset, ParametersScreen screen, Context context) {
        modifiedPixels = preset.getPixels();
        tool = preset.getTool();
        presetSurface = new Surface(screen);
        presetView = new CustomTextView(context, presetSurface, CustomTextView.PRESET_ID);


        this.x = this.y = 30;
        isActive = true;
        draw();

    }

    public CustomTextView getPresetView() {
        return presetView;
    }

    public void draw() {


        presetSurface.clear();

        normalizeCoordinates(x, y);

        for (int i = 0; i < modifiedPixels.length; i++) {
            for (int j = 0; j < modifiedPixels[0].length; j++) {
                Pixel p = modifiedPixels[i][j];
                if (p != null) {
                    presetSurface.setPixel(p.getX(), p.getY(), p);
                }

            }

        }

        presetView.invalidate();

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

        modifiedPixels = rotateByDegrees(modifiedPixels, degress);

        draw();

    }


    public void scalePlus() {
        this.scale++;
        if (this.scale <= SCALE_MAX)
            modifiedPixels = addPixelsScale(modifiedPixels);
        else this.scale = SCALE_MAX;

        draw();


    }

    public void scaleMinus() {
        this.scale--;

        if (this.scale >= SCALE_MIN)
            modifiedPixels = removePixelsScale(modifiedPixels);
        else this.scale = SCALE_MIN;

        draw();


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
        for (int i = 0; i < modifiedPixels.length; i++) {
            for (int j = 0; j < modifiedPixels[0].length; j++) {
                Pixel p = modifiedPixels[i][j];
                if (p != null) {
                    p.setX(i + x);
                    p.setY(j + y);
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
