package com.tomatedigital.tutorialcreator.shape;

import android.graphics.Canvas;
import android.graphics.Paint;

public class NoShape implements Shape {


    public void draw(Canvas canvas, Paint paint, int x, int y, int padding) {
    }

    public int getWidth() {
        return 0;
    }

    public int getHeight() {
        return 0;
    }
}
