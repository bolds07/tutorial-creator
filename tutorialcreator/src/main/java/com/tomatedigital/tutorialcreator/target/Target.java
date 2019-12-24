package com.tomatedigital.tutorialcreator.target;

import android.graphics.Point;
import android.graphics.Rect;


public interface Target {

    boolean isReady();

    Rect getBounds();

    Point getPoint();
}
