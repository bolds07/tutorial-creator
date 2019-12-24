package com.tomatedigital.tutorialcreator.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.annotation.NonNull;

import com.tomatedigital.tutorialcreator.target.Target;

@SuppressWarnings("unused")
public class CircleShape implements Shape {

    private int radius;
    private Target target;


    private CircleShape(int radius) {
        this.radius = radius;
    }


    public CircleShape(Target target) {
        this(-1);
        this.target = target;
    }

    private static int getPreferredRadius(@NonNull Rect bounds) {
        return Math.max(bounds.width(), bounds.height()) / 2;
    }

    public int getRadius() {
        return this.radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void draw(@NonNull Canvas canvas, @NonNull Paint paint, int x, int y, int padding) {
        if (this.radius > 0)
            canvas.drawCircle(x, y, (this.radius + padding), paint);
        else if (this.target != null) {
            refreshTarget();
            if (this.radius > 0)
                canvas.drawCircle(x, y, (this.radius + padding), paint);
        }

    }

    private void refreshTarget() {
        this.radius = getPreferredRadius(this.target.getBounds());

    }

    public int getWidth() {
        return this.radius * 2;
    }

    public int getHeight() {
        return this.radius * 2;
    }
}
