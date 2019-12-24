package com.tomatedigital.tutorialcreator.shape;

import android.graphics.Canvas;
import android.graphics.Paint;

public interface Shape {
    void draw(Canvas canvas, Paint paint, int i, int i2, int i3);

    int getHeight();

    int getWidth();


}
