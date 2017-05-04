package mykola.devchallenge.com.ansidrawing.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.ViewGroup;
import android.widget.TextView;

import mykola.devchallenge.com.ansidrawing.helpers.DrawHelper;
import mykola.devchallenge.com.ansidrawing.models.ParametersScreen;
import mykola.devchallenge.com.ansidrawing.models.Pixel;

/**
 * Created by mykola on 01.05.17.
 */

public class CustomTextView extends TextView {

    private DrawHelper helper;

    public CustomTextView(Context context, DrawHelper helper) {
        super(context);
        this.helper = helper;
        setTypeface(Typeface.MONOSPACE);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Paint paint = new Paint();
        for (int i = 0; i < ParametersScreen.SCALE_WIDTH; i++) {
            for (int j = 0; j < ParametersScreen.SCALE_HEIGHT; j++) {
                Pixel p = helper.activeCanvas.getPixel(i, j);
                if (p != null) {
                    paint.setColor(p.getColor());
                    paint.setTextSize(p.getSize());
                    canvas.drawText(Character.toString((char) p.getSymbol()), i * 10, j * 10, paint);
                }

            }

        }

    }
}
