package mykola.devchallenge.com.ansidrawing.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
        resizeScreen();
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
    }

    public void setSurface(Surface surface) {
        this.surface = surface;
        resizeScreen();
    }


    public void resizeScreen() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(surface.getWidth() * (ParametersScreen.KOEF_WIDTH), surface.getHeight() * (ParametersScreen.KOEF_HEIGHT));
        setLayoutParams(params);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(1,1,getWidth(),getHeight(),paint);

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
