package com.tomatedigital.tutorialcreator;

import android.graphics.Typeface;

import androidx.annotation.ColorInt;

public class ShowcaseConfig {


    private int dismissTextSize = 0;
    private IAnimationFactory mAnimationFactory = new FadeAnimationFactory();
    private int mContentTextColor = -1;
    private long mDelay = 500;
    private boolean mDismissOnTargetTouch = true;
    private boolean mDismissOnTouch = true;
    private int mDismissTextColor = -7829368;
    private Typeface mDismissTextStyle = Typeface.DEFAULT_BOLD;
    private long mFadeDuration = 100;
    private int mMaskColor = -3355444;
    private int mShapePadding = 1;
    private boolean mSingleUse = false;
    private boolean mTargetTouchable = false;
    private int messageTextSize = 0;
    private boolean renderOverNav = false;
    private Shape shape = Shape.CIRCLE;
    private int titleTextColor = -1;
    private int titleTextSize = 0;

    public ShowcaseConfig() {

    }

    public ShowcaseConfig(long mDelay, int mMaskColour, Typeface mDismissTextStyle, int mContentTextColor, int mDismissTextColor, int titleTextColor, int titleTextSize, int messageTextSize, int dismissTextSize, long mFadeDuration, Shape mShape, int mShapePadding, boolean renderOverNav, IAnimationFactory mAnimationFactory, boolean mDismissOnTouch, boolean mSingleUse, boolean mTargetTouchable, boolean mDismissOnTargetTouch) {
        this.mDelay = mDelay;
        this.mMaskColor = mMaskColour;
        this.mDismissTextStyle = mDismissTextStyle;
        this.mContentTextColor = mContentTextColor;
        this.mDismissTextColor = mDismissTextColor;
        this.titleTextColor = titleTextColor;
        this.titleTextSize = titleTextSize;
        this.messageTextSize = messageTextSize;
        this.dismissTextSize = dismissTextSize;
        this.mFadeDuration = mFadeDuration;
        this.shape = mShape;
        this.mShapePadding = mShapePadding;
        this.renderOverNav = renderOverNav;
        this.mAnimationFactory = mAnimationFactory;
        this.mDismissOnTouch = mDismissOnTouch;
        this.mSingleUse = mSingleUse;
        this.mTargetTouchable = mTargetTouchable;
        this.mDismissOnTargetTouch = mDismissOnTargetTouch;
    }

    public long getDelay() {
        return this.mDelay;
    }

    public void setDelay(long mDelay) {
        this.mDelay = mDelay;
    }

    public int getMaskColor() {
        return this.mMaskColor;
    }

    public void setMaskColor(@ColorInt int color) {
        this.mMaskColor = color;
    }

    public Typeface getDismissTextStyle() {
        return this.mDismissTextStyle;
    }

    public void setDismissTextStyle(Typeface mDismissTextStyle) {
        this.mDismissTextStyle = mDismissTextStyle;
    }

    public int getContentTextColor() {
        return this.mContentTextColor;
    }

    public void setContentTextColor(int mContentTextColor) {
        this.mContentTextColor = mContentTextColor;
    }

    public int getDismissTextColor() {
        return this.mDismissTextColor;
    }

    public void setDismissTextColor(int mDismissTextColor) {
        this.mDismissTextColor = mDismissTextColor;
    }

    public int getTitleTextColor() {
        return this.titleTextColor;
    }

    public void setTitleTextColor(int titleTextColor) {
        this.titleTextColor = titleTextColor;
    }

    public int getTitleTextSize() {
        return this.titleTextSize;
    }

    public void setTitleTextSize(int titleTextSize) {
        this.titleTextSize = titleTextSize;
    }

    public int getContentTextSize() {
        return this.messageTextSize;
    }

    public void setMessageTextSize(int messageTextSize) {
        this.messageTextSize = messageTextSize;
    }

    public int getDismissTextSize() {
        return this.dismissTextSize;
    }

    public void setDismissTextSize(int dismissTextSize) {
        this.dismissTextSize = dismissTextSize;
    }

    public long getFadeDuration() {
        return this.mFadeDuration;
    }

    public void setFadeDuration(long mFadeDuration) {
        this.mFadeDuration = mFadeDuration;
    }

    public Shape getShape() {
        return this.shape;
    }

    public void setShape(Shape mShape) {
        this.shape = mShape;
    }

    public int getShapePadding() {
        return this.mShapePadding;
    }

    public void setShapePadding(int mShapePadding) {
        this.mShapePadding = mShapePadding;
    }

    public boolean isRenderOverNav() {
        return this.renderOverNav;
    }

    public void setRenderOverNav(boolean renderOverNav) {
        this.renderOverNav = renderOverNav;
    }

    public IAnimationFactory getAnimationFactory() {
        return this.mAnimationFactory;
    }

    public void setAnimationFactory(IAnimationFactory mAnimationFactory) {
        this.mAnimationFactory = mAnimationFactory;
    }

    public boolean isDismissOnTouch() {
        return this.mDismissOnTouch;
    }

    public void setDismissOnTouch(boolean mDismissOnTouch) {
        this.mDismissOnTouch = mDismissOnTouch;
    }

    public boolean isSingleUse() {
        return this.mSingleUse;
    }

    public void setSingleUse(boolean mSingleUse) {
        this.mSingleUse = mSingleUse;
    }

    public boolean isTargetTouchable() {
        return this.mTargetTouchable;
    }

    public void setTargetTouchable(boolean mTargetTouchable) {
        this.mTargetTouchable = mTargetTouchable;
    }

    public boolean isDismissOnTargetTouch() {
        return this.mDismissOnTargetTouch;
    }

    public void setDismissOnTargetTouch(boolean mDismissOnTargetTouch) {
        this.mDismissOnTargetTouch = mDismissOnTargetTouch;
    }

    public enum Shape {
        NO_SHAPE,
        CIRCLE,
        RECTANGLE
    }
}
