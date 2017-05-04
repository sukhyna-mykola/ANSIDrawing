package mykola.devchallenge.com.ansidrawing.models;

import android.graphics.Color;

/**
 * Created by mykola on 01.05.17.
 */

public class Pixel extends ParametersTool {

    public Pixel(int size, int color, int symbol, int x, int y) {
        super(size, color, symbol);
        this.x = x;
        this.y = y;
    }

    private int x,y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


}
