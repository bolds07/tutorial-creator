package com.tomatedigital.tutorialcreator.target;

import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;

public class ViewTarget implements Target {
    private final View mView;

    public ViewTarget(View view) {
        this.mView = view;
    }

    public boolean isReady() {
        return this.mView != null && !getBounds().isEmpty();
    }

    @NonNull
    public Point getPoint() {
        if (this.mView == null)
            return new Point(0, 0);

        int[] location = new int[2];
        this.mView.getLocationInWindow(location);
        return new Point(location[0] + (this.mView.getWidth() / 2), location[1] + (this.mView.getHeight() / 2));
    }

    @NonNull
    public Rect getBounds() {
        if (this.mView == null)
            return new Rect(0, 0, 0, 0);

        int[] location = new int[2];
        this.mView.getLocationInWindow(location);

        return new Rect(location[0], location[1], location[0] + this.mView.getMeasuredWidth(), location[1] + this.mView.getMeasuredHeight());
    }
}
