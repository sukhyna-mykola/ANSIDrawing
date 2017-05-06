package mykola.devchallenge.com.ansidrawing.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.ViewGroup;
import android.widget.TextView;

import mykola.devchallenge.com.ansidrawing.helpers.ParametersScreen;
import mykola.devchallenge.com.ansidrawing.models.Pixel;
import mykola.devchallenge.com.ansidrawing.models.Surface;

/**
 * Created by mykola on 01.05.17.
 */

public class CustomTextView extends TextView {
    private Surface surface;

    public CustomTextView(Context context, Surface surface) {
        super(context);
        this.surface = surface;

        setOnTouchListener((OnTouchListener) context);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
    }

    public void setSurface(Surface surface) {
        this.surface = surface;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Paint paint = new Paint();
        for (int i = 0; i < ParametersScreen.SCALE_WIDTH; i++) {
            for (int j = 0; j < ParametersScreen.SCALE_HEIGHT; j++) {
                Pixel p = surface.getPixel(i, j);
                if (p != null) {
                    paint.setColor(p.getColor());
                    paint.setTextSize(p.getSizeSymbol());
                    canvas.drawText(Character.toString((char) p.getSymbol()),
                            i * ParametersScreen.KOEF_WIDTH, j * ParametersScreen.KOEF_HEIGHT, paint);
                }

            }

        }

    }
}
