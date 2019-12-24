package com.tomatedigital.tutorialcreator;

import android.graphics.Typeface;

import androidx.annotation.NonNull;

public class ShowcaseConfigBuilder {
    private int dismissTextSize;
    private IAnimationFactory mAnimationFactory;
    private int mContentTextColor;
    private long mDelay;
    private boolean mDismissOnTargetTouch;
    private boolean mDismissOnTouch;
    private int mDismissTextColor;
    private Typeface mDismissTextStyle;
    private long mFadeDuration;
    private int mMaskColour;
    private ShowcaseConfig.Shape mShape;
    private int mShapePadding;
    private boolean mSingleUse;
    private boolean mTargetTouchable;
    private int messageTextSize;
    private boolean renderOverNav;
    private int titleTextColor;
    private int titleTextSize;

    @NonNull
    public ShowcaseConfigBuilder setmDelay(long mDelay) {
        this.mDelay = mDelay;
        return this;
    }

    @NonNull
    public ShowcaseConfigBuilder setmMaskColour(int mMaskColour) {
        this.mMaskColour = mMaskColour;
        return this;
    }

    @NonNull
    public ShowcaseConfigBuilder setmDismissTextStyle(Typeface mDismissTextStyle) {
        this.mDismissTextStyle = mDismissTextStyle;
        return this;
    }

    @NonNull
    public ShowcaseConfigBuilder setmContentTextColor(int mContentTextColor) {
        this.mContentTextColor = mContentTextColor;
        return this;
    }

    @NonNull
    public ShowcaseConfigBuilder setmDismissTextColor(int mDismissTextColor) {
        this.mDismissTextColor = mDismissTextColor;
        return this;
    }

    @NonNull
    public ShowcaseConfigBuilder setTitleTextColor(int titleTextColor) {
        this.titleTextColor = titleTextColor;
        return this;
    }

    @NonNull
    public ShowcaseConfigBuilder setTitleTextSize(int titleTextSize) {
        this.titleTextSize = titleTextSize;
        return this;
    }

    @NonNull
    public ShowcaseConfigBuilder setMessageTextSize(int messageTextSize) {
        this.messageTextSize = messageTextSize;
        return this;
    }

    @NonNull
    public ShowcaseConfigBuilder setDismissTextSize(int dismissTextSize) {
        this.dismissTextSize = dismissTextSize;
        return this;
    }

    @NonNull
    public ShowcaseConfigBuilder setmFadeDuration(long mFadeDuration) {
        this.mFadeDuration = mFadeDuration;
        return this;
    }

    @NonNull
    public ShowcaseConfigBuilder setmShape(ShowcaseConfig.Shape mShape) {
        this.mShape = mShape;
        return this;
    }

    @NonNull
    public ShowcaseConfigBuilder setmShapePadding(int mShapePadding) {
        this.mShapePadding = mShapePadding;
        return this;
    }

    @NonNull
    public ShowcaseConfigBuilder setRenderOverNav(boolean renderOverNav) {
        this.renderOverNav = renderOverNav;
        return this;
    }

    @NonNull
    public ShowcaseConfigBuilder setmAnimationFactory(IAnimationFactory mAnimationFactory) {
        this.mAnimationFactory = mAnimationFactory;
        return this;
    }

    @NonNull
    public ShowcaseConfigBuilder setmDismissOnTouch(boolean mDismissOnTouch) {
        this.mDismissOnTouch = mDismissOnTouch;
        return this;
    }

    @NonNull
    public ShowcaseConfigBuilder setmSingleUse(boolean mSingleUse) {
        this.mSingleUse = mSingleUse;
        return this;
    }

    @NonNull
    public ShowcaseConfigBuilder setmTargetTouchable(boolean mTargetTouchable) {
        this.mTargetTouchable = mTargetTouchable;
        return this;
    }

    @NonNull
    public ShowcaseConfigBuilder setmDismissOnTargetTouch(boolean mDismissOnTargetTouch) {
        this.mDismissOnTargetTouch = mDismissOnTargetTouch;
        return this;
    }

    @NonNull
    public ShowcaseConfig createShowcaseConfig() {
        return new ShowcaseConfig(this.mDelay, this.mMaskColour, this.mDismissTextStyle, this.mContentTextColor, this.mDismissTextColor, this.titleTextColor, this.titleTextSize, this.messageTextSize, this.dismissTextSize, this.mFadeDuration, this.mShape, this.mShapePadding, this.renderOverNav, this.mAnimationFactory, this.mDismissOnTouch, this.mSingleUse, this.mTargetTouchable, this.mDismissOnTargetTouch);
    }
}
