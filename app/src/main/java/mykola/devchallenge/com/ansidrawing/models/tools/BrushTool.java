package mykola.devchallenge.com.ansidrawing.models.tools;

import android.util.Log;

import mykola.devchallenge.com.ansidrawing.R;
import mykola.devchallenge.com.ansidrawing.models.ParametersTool;
import mykola.devchallenge.com.ansidrawing.models.Pixel;
import mykola.devchallenge.com.ansidrawing.models.Surface;


public class BrushTool extends Tool {
    public static final String BRUSH = "BRUSH";

    public BrushTool(ParametersTool parametersTool) {
        super(parametersTool, R.drawable.brush, BRUSH);
        this.parametersTool = parametersTool;
    }

    public BrushTool() {
        super(R.drawable.brush, BRUSH);
    }

    @Override
    public Pixel draw(int x, int y, Surface surface) {

        double delta = parametersTool.getSizeTool() / 4.0;
        double deltaOut = parametersTool.getSizeTool() / 2.0;

        for (int i = (int) -deltaOut; i < deltaOut; i++) {
            for (int j = (int) -deltaOut; j < deltaOut; j++) {
                try {
                    Pixel oldPixel = surface.getPixel(x + i, y + j);
                    if (Math.abs(j) >= delta || Math.abs(i) >= delta) {


                        String colorS = Integer.toHexString(parametersTool.getColor());
                        int a = (int) ((deltaOut - Math.max(Math.abs(i), Math.abs(j))) * (256.0 / deltaOut));

                        if (oldPixel != null) {
                            String colorold = Integer.toHexString(oldPixel.getColor());
                            String colornew = colorS;
                            String aOld = colorold.substring(0, 2);
                            String cOld = colorold.substring(2);
                            String cNew = colornew.substring(2);
                            if (cOld.equals(cNew)) {
                                if (a < Integer.parseInt(aOld, 16)) {
                                    continue;
                                }
                            }
                        }


                        String ff = Integer.toHexString(255);
                        String as = Integer.toHexString(a);
                        colorS = new String(colorS.replaceFirst(ff, as));

                        Pixel newPixel = new Pixel(parametersTool.getSizeSymbol(), Integer.parseInt(colorS, 16), parametersTool.getSymbol(), x + i, y + j);
                        surface.setPixel(x + i, y + j, newPixel);


                    } else {

                        Pixel newPixel = new Pixel(parametersTool.getSizeSymbol(), parametersTool.getColor(), parametersTool.getSymbol(), x + i, y + j);
                        surface.setPixel(x + i, y + j, newPixel);

                    }
                } catch (Exception e) {
                    Log.d(TAG, "IndexOutOfBoundsException");
                }

            }
        }
        return surface.getPixel(x, y);
    }
}
