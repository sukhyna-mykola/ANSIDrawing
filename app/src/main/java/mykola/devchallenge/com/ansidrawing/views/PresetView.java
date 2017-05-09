package mykola.devchallenge.com.ansidrawing.views;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import mykola.devchallenge.com.ansidrawing.R;
import mykola.devchallenge.com.ansidrawing.helpers.DrawHelper;
import mykola.devchallenge.com.ansidrawing.helpers.ParametersScreen;
import mykola.devchallenge.com.ansidrawing.helpers.PresetHelper;

import static mykola.devchallenge.com.ansidrawing.helpers.ParametersScreen.KOEF_HEIGHT;
import static mykola.devchallenge.com.ansidrawing.helpers.ParametersScreen.KOEF_WIDTH;


public class PresetView extends RelativeLayout implements View.OnTouchListener, View.OnClickListener {
    private DrawHelper drawHelper;
    private PresetHelper presetHelper;

    private ImageButton confirm, cancel, scalePlus, scaleMinus, rotate;
    private CustomTextView presetView;

    public PresetView(Context context, DrawHelper drawHelper) {
        super(context);

        this.drawHelper = drawHelper;
        this.presetHelper = drawHelper.getPresetHelper();

        presetView = new CustomTextView(context, presetHelper.getPresetSurface());
        presetView.setOnTouchListener(this);

        View v = LayoutInflater.from(context).inflate(R.layout.preset_menu, null);

        confirm = (ImageButton) v.findViewById(R.id.presetConfirm);
        cancel = (ImageButton) v.findViewById(R.id.presetCancel);
        scaleMinus = (ImageButton) v.findViewById(R.id.presetScaleMinus);
        scalePlus = (ImageButton) v.findViewById(R.id.presetScalePlus);
        rotate = (ImageButton) v.findViewById(R.id.presetRotate);

        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);
        scaleMinus.setOnClickListener(this);
        scalePlus.setOnClickListener(this);
        rotate.setOnClickListener(this);

        v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        setBackgroundColor(Color.TRANSPARENT);

        resizeScreen();

        addView(presetView);
        addView(v);

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_MOVE:

                float x = event.getX();
                float y = event.getY();

                int convertY = (int) (y / KOEF_HEIGHT);
                int convertX = (int) (x / KOEF_WIDTH);

                drawHelper.move(convertX, convertY);

                update();
                return true;

            default:
                return true;
        }

    }

    public void resizeScreen() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(drawHelper.getSurface().getWidth() * (ParametersScreen.KOEF_WIDTH), drawHelper.getSurface().getHeight() * (ParametersScreen.KOEF_HEIGHT));
        setLayoutParams(params);
    }


    public void update() {
        presetView.invalidate();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.presetCancel:
                drawHelper.presetCancel();
                break;
            case R.id.presetConfirm:
                drawHelper.presetConfirm();
                break;
            case R.id.presetScaleMinus:
                presetHelper.scaleMinus();
                update();
                break;
            case R.id.presetScalePlus:
                presetHelper.scalePlus();
                update();
                break;
            case R.id.presetRotate:
                presetHelper.rotate();
                update();
                break;
        }
    }
}
