package mykola.devchallenge.com.ansidrawing.models;

/**
 * Created by mykola on 01.05.17.
 */

public class ParametersTool {
    protected int color;
    protected int sizeSymbol;
    protected int symbol;
    protected int sizeTool;

    public ParametersTool(int sizeSymbol, int color, int symbol, int sizeTool) {
        this.color = color;
        this.sizeSymbol = sizeSymbol;
        this.symbol = symbol;
        this.sizeTool  = sizeTool;
    }

    public ParametersTool(int sizeSymbol, int color, int symbol) {
        this.color = color;
        this.sizeSymbol = sizeSymbol;
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

    public int getSizeSymbol() {
        return sizeSymbol;
    }

    public void setSizeSymbol(int sizeSymbol) {
        this.sizeSymbol = sizeSymbol;
    }

    public int getSizeTool() {
        return sizeTool;
    }

    public void setSizeTool(int sizeTool) {
        this.sizeTool = sizeTool;
    }

    public int getSymbol() {
        return symbol;
    }

    public void setSymbol(int symbol) {
        this.symbol = symbol;
    }


}
