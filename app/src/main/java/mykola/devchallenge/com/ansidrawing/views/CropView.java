package mykola.devchallenge.com.ansidrawing.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import mykola.devchallenge.com.ansidrawing.R;
import mykola.devchallenge.com.ansidrawing.helpers.CropHelper;
import mykola.devchallenge.com.ansidrawing.helpers.DrawHelper;
import mykola.devchallenge.com.ansidrawing.helpers.ParametersScreen;
import mykola.devchallenge.com.ansidrawing.models.Point;

import static mykola.devchallenge.com.ansidrawing.helpers.ParametersScreen.KOEF_HEIGHT;
import static mykola.devchallenge.com.ansidrawing.helpers.ParametersScreen.KOEF_WIDTH;

/**
 * Created by mykola on 07.05.17.
 */

public class CropView extends FrameLayout implements View.OnTouchListener, View.OnClickListener {
    private DrawHelper drawHelper;
    private CropHelper cropHelper;

    private Point curentMarker;
    private Point rbMarker, ltMarker;
    private ImageButton perform, cancel;

    public CropView(Context context, DrawHelper drawHelper) {
        super(context);
        this.drawHelper = drawHelper;
        this.cropHelper = drawHelper.getCropHelper();

        View v = LayoutInflater.from(context).inflate(R.layout.crop_menu, null);
        perform = (ImageButton) v.findViewById(R.id.crop_conform);
        cancel = (ImageButton) v.findViewById(R.id.crop_cancel);

        perform.setOnClickListener(this);
        cancel.setOnClickListener(this);

        v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));


        rbMarker = cropHelper.getRbMarker();
        ltMarker = cropHelper.getLtMarker();

        curentMarker = rbMarker;

        setBackgroundColor(Color.TRANSPARENT);

        resizeScreen();

        setOnTouchListener(this);

        addView(v);

        update();
    }


    public void update() {
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();


        float left = ltMarker.getX() * ParametersScreen.KOEF_WIDTH;
        float top = ltMarker.getY() * ParametersScreen.KOEF_HEIGHT;
        float right = rbMarker.getX() * ParametersScreen.KOEF_WIDTH;
        float bottom = rbMarker.getY() * ParametersScreen.KOEF_HEIGHT;

        paint.setColor(Color.parseColor("#99ffffff"));
        paint.setStyle(Paint.Style.FILL);

        canvas.drawRect(left, top, right, bottom, paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setPathEffect(new DashPathEffect(new float[]{10, 20}, 0));
        paint.setColor(Color.BLACK);

        canvas.drawRect(left, top, right, bottom, paint);

        paint.setStyle(Paint.Style.FILL);
        if (curentMarker == ltMarker)
            paint.setColor(Color.RED);
        else
            paint.setColor(Color.BLACK);

        canvas.drawCircle(left, top, 10, paint);


        if (curentMarker == rbMarker)
            paint.setColor(Color.RED);
        else
            paint.setColor(Color.BLACK);

        canvas.drawCircle(right, bottom, 10, paint);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {


        float x = event.getX();
        float y = event.getY();

        if (x < 0)
            x = KOEF_WIDTH;
        if (x > getWidth())
            x = getWidth() -  KOEF_WIDTH;

        if (y < 0)
            y = KOEF_HEIGHT;
        if (y > getHeight())
            y = getHeight() -  KOEF_HEIGHT;

        int convertY = (int) (y / KOEF_HEIGHT);
        int convertX = (int) (x / KOEF_WIDTH);

        if (Math.abs(convertX - ltMarker.getX()) < 3 || Math.abs(convertX - ltMarker.getY()) < 3)
            curentMarker = ltMarker;

        if (Math.abs(convertY - rbMarker.getX()) < 3 || Math.abs(convertY - rbMarker.getY()) < 3)
            curentMarker = rbMarker;

        curentMarker.setX(convertX);
        curentMarker.setY(convertY);

        drawHelper.getCropHelper().checkCorrectCoordinates(curentMarker);

        update();

        return true;
    }

    public void resizeScreen() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(drawHelper.getSurface().getWidth() * (ParametersScreen.KOEF_WIDTH), drawHelper.getSurface().getHeight() * (ParametersScreen.KOEF_HEIGHT));
        setLayoutParams(params);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.crop_cancel:
                drawHelper.cancelCrop();
                break;
            case R.id.crop_conform:
                drawHelper.confirmCrop();
                break;
        }
    }
}
