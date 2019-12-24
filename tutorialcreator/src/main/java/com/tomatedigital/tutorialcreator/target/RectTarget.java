package com.tomatedigital.tutorialcreator.target;

import android.graphics.Point;
import android.graphics.Rect;

import androidx.annotation.NonNull;

public class RectTarget implements Target {
    private final int height;
    private final int width;

    private final int x;

    private final int y;

    public RectTarget(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }


    public boolean isReady() {
        return true;
    }

    @NonNull
    public Point getPoint() {
        return new Point(this.x, this.y);
    }

    @NonNull
    public Rect getBounds() {
        return new Rect(this.x, this.y, this.width + this.x, this.height + this.y);
    }
}
