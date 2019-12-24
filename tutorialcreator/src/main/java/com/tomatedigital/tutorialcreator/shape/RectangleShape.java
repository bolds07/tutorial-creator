package com.tomatedigital.tutorialcreator.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.annotation.NonNull;

import com.tomatedigital.tutorialcreator.target.Target;

@SuppressWarnings("ALL")
public class RectangleShape implements Shape {

    private boolean fullWidth;
    private int height;
    private Rect rect;
    private int width;
    private Target target;

    public RectangleShape() {
        this.fullWidth = false;
        this.width = 0;
        this.height = 0;
    }


    public RectangleShape(@NonNull Rect bounds, boolean fullWidth) {
        this.fullWidth = fullWidth;
        this.height = bounds.height();
        if (fullWidth) {
            this.width = Integer.MAX_VALUE;
        } else {
            this.width = bounds.width();
        }
        init();
    }

    public RectangleShape(Target t, boolean fullWidth) {
        this.rect = new Rect();
        this.target = t;
    }


    private void init() {
        this.rect = new Rect((-this.width) / 2, (-this.height) / 2, this.width / 2, this.height / 2);
    }

    public void draw(@NonNull Canvas canvas, @NonNull Paint paint, int x, int y, int padding) {
        if (!this.rect.isEmpty())
            canvas.drawRect(((this.rect.left + x) - padding), ((this.rect.top + y) - padding), ((this.rect.right + x) + padding), ((this.rect.bottom + y) + padding), paint);
        else if (this.target != null) {
            refreshTarget();
            if (!this.rect.isEmpty())
                canvas.drawRect(((this.rect.left + x) - padding), ((this.rect.top + y) - padding), ((this.rect.right + x) + padding), ((this.rect.bottom + y) + padding), paint);
        }
    }

    private void refreshTarget() {
        Rect bounds = this.target.getBounds();
        this.height = bounds.height();
        if (this.fullWidth) {
            this.width = Integer.MAX_VALUE;
        } else {
            this.width = bounds.width();
        }
        init();
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
