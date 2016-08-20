package com.gm.circley.util;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;

/**
 * Created by lgm on 2016/8/8.
 */
public class SelectorUtil {

    private static int shiftColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.9f;
        return Color.HSVToColor(hsv);
    }

    public static Drawable createSelector(int color, Shape shape) {
        ShapeDrawable shapeDrawable = new ShapeDrawable(shape);
        shapeDrawable.getPaint().setColor(color);

        ShapeDrawable darkShapeDrawable = new ShapeDrawable(shape);
        darkShapeDrawable.getPaint().setColor(shiftColor(color));

        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{-android.R.attr.state_pressed}, shapeDrawable);
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, darkShapeDrawable);
        return stateListDrawable;
    }

    public static Drawable createOvalShapeSelector(int color) {
        return createSelector(color, new OvalShape());
    }

    public static Drawable createRectShapeSelector(int color) {
        return createSelector(color, new RectShape());
    }

    public static Drawable createRoundRectShapeSelector(int color) {
        float[] roundRect = {8, 8, 8, 8, 8, 8, 8, 8};
        RoundRectShape roundRectShape = new RoundRectShape(roundRect, null, roundRect);
        return createSelector(color, roundRectShape);
    }
}
