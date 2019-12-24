package com.tomatedigital.tutorialcreator;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.graphics.Point;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;

import androidx.annotation.NonNull;


public class CircularRevealAnimationFactory implements IAnimationFactory {

    private static final String ALPHA = "alpha";
    private static final float INVISIBLE = 0f;
    private static final float VISIBLE = 1f;


    public CircularRevealAnimationFactory() {

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void animateInView(@NonNull View target, @NonNull Point point, long duration, @NonNull final AnimationStartListener listener) {
        Animator animator = ViewAnimationUtils.createCircularReveal(target, point.x, point.y, 0,
                target.getWidth() > target.getHeight() ? target.getWidth() : target.getHeight());
        animator.setDuration(duration).addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                listener.onAnimationStart();
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animator.start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void animateOutView(@NonNull View target, @NonNull Point point, long duration, @NonNull final AnimationEndListener listener) {
        Animator animator = ViewAnimationUtils.createCircularReveal(target, point.x, point.y,
                target.getWidth() > target.getHeight() ? target.getWidth() : target.getHeight(), 0);
        animator.setDuration(duration).addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                listener.onAnimationEnd();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animator.start();
    }


}
