package mykola.devchallenge.com.ansidrawing.models;

/**
 * Created by mykola on 01.05.17.
 */

public class ParametersTool {
    protected int color;
    protected int size;
    protected int symbol;

    public ParametersTool(int size, int color, int symbol) {
        this.color = color;
        this.size = size;
        this.symbol = symbol;
    }

    public ParametersTool() {
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSymbol() {
        return symbol;
    }

    public void setSymbol(int symbol) {
        this.symbol = symbol;
    }


}
