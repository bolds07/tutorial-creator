package com.tomatedigital.tutorialcreator;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tomatedigital.tutorialcreator.shape.CircleShape;
import com.tomatedigital.tutorialcreator.shape.NoShape;
import com.tomatedigital.tutorialcreator.shape.RectangleShape;
import com.tomatedigital.tutorialcreator.shape.Shape;
import com.tomatedigital.tutorialcreator.target.Target;
import com.tomatedigital.tutorialcreator.target.ViewTarget;

import java.util.ArrayList;
import java.util.List;

public class MaterialShowcaseView extends FrameLayout implements OnTouchListener, OnClickListener {
    @Nullable
    private IAnimationFactory mAnimationFactory;
    @Nullable
    private Bitmap mBitmap;
    @Nullable
    private Canvas mCanvas;
    private int mContentBottomMargin;
    private View mContentBox;
    private TextView mContentTextView;
    private int mContentTopMargin;
    private long mDelayInMillis;
    private IDetachedListener mDetachedListener;
    private TextView mDismissButton;
    private boolean mDismissOnTargetTouch;
    private boolean mDismissOnTouch;
    @Nullable
    private Paint mEraser;
    private long mFadeDurationInMillis;
    private int mGravity;
    @Nullable
    private Handler mHandler;


    @Nullable
    private List<IShowcaseListener> mListeners;
    private int mMaskColour;
    private int mOldHeight;
    private int mOldWidth;
    @Nullable
    private PrefsManager mPrefsManager;
    private boolean mRenderOverNav;
    private Shape mShape;
    private int mShapePadding;
    private boolean mShouldRender = false;
    private boolean mSingleUse;
    private Target mTarget;
    private boolean mTargetTouchable;
    private TextView mTitleTextView;
    private boolean mWasDismissed = false;
    private int mXPosition;
    private int mYPosition;
    @Nullable
    private ViewTreeObserver.OnGlobalLayoutListener observer;
    private Window window;

    public MaterialShowcaseView(@NonNull Context context) {
        super(context);
        init();
    }

    public MaterialShowcaseView(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MaterialShowcaseView(@NonNull Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public MaterialShowcaseView(@NonNull Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public static void resetSingleUse(@NonNull Context context, String showcaseID) {
        PrefsManager.resetShowcase(context, showcaseID);
    }

    public static void resetAll(@NonNull Context context) {
        PrefsManager.resetAll(context);
    }

    private static int getSoftButtonsBarSizePort(Window window) {
        if (VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1)
            return 0;


//        window.
//        DisplayMetrics metrics = new DisplayMetrics();
//        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        int usableHeight = metrics.heightPixels;
//        activity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
//        int realHeight = metrics.heightPixels;
//        if (realHeight > usableHeight) {
//            return realHeight - usableHeight;
//        }
        return 0;
    }

    private void init() {
        setWillNotDraw(false);
        this.mListeners = new ArrayList<>();

        this.observer = () -> setTarget(mTarget);
        getViewTreeObserver().addOnGlobalLayoutListener(this.observer);
        setOnTouchListener(this);
        setVisibility(View.GONE);
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.showcase_content, this, true);
        this.mContentBox = contentView.findViewById(R.id.content_box);
        this.mTitleTextView = contentView.findViewById(R.id.tv_title);
        this.mContentTextView = contentView.findViewById(R.id.tv_content);
        this.mDismissButton = contentView.findViewById(R.id.tv_dismiss);
        this.mDismissButton.setOnClickListener(this);
        setConfig(new ShowcaseConfigBuilder().createShowcaseConfig());
    }

    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        if (this.mShouldRender) {
            int width = getMeasuredWidth();
            int height = getMeasuredHeight();
            if (width > 0) {
                if (height > 0) {
                    if (this.mBitmap == null || this.mCanvas == null || this.mOldHeight != height || this.mOldWidth != width) {
                        if (this.mBitmap != null) {
                            this.mBitmap.recycle();
                        }
                        this.mBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
                        this.mCanvas = new Canvas(this.mBitmap);
                    }
                    this.mOldWidth = width;
                    this.mOldHeight = height;
                    this.mCanvas.drawColor(0, Mode.CLEAR);
                    this.mCanvas.drawColor(this.mMaskColour);
                    if (this.mEraser == null) {
                        this.mEraser = new Paint();
                        this.mEraser.setColor(-1);
                        this.mEraser.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
                        this.mEraser.setFlags(1);
                    }
                    this.mShape.draw(this.mCanvas, this.mEraser, this.mXPosition, this.mYPosition, this.mShapePadding);
                    canvas.drawBitmap(this.mBitmap, 0.0f, 0.0f, null);
                }
            }
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (!(this.mWasDismissed || !this.mSingleUse || this.mPrefsManager == null)) {
            this.mPrefsManager.resetShowcase();
        }
        notifyOnDismissed();
    }

    public boolean onTouch(View v, @NonNull MotionEvent event) {
        if (this.mDismissOnTouch) {
            hide();
        }
        if (!this.mDismissOnTargetTouch || !this.mTarget.getBounds().contains((int) event.getX(), (int) event.getY())) {
            return true;
        }
        hide();
        return !this.mTargetTouchable;
    }

    private void notifyOnDisplayed() {
        if (this.mListeners != null) {
            for (IShowcaseListener listener : this.mListeners) {
                listener.onShowcaseDisplayed(this, -1);
            }
        }
    }

    private void notifyOnDismissed() {
        if (this.mListeners != null) {
            for (IShowcaseListener listener : this.mListeners) {
                listener.onShowcaseDismissed(this, -1);
            }
            this.mListeners.clear();
            this.mListeners = null;
        }
        if (this.mDetachedListener != null) {
            this.mDetachedListener.onShowcaseDetached(this, this.mWasDismissed);
        }
    }

    public void onClick(View v) {
        hide();
    }

    private Target getTarget() {
        return this.mTarget;
    }

    private void setTarget(Target target) {
        this.mTarget = target;
        updateDismissButton();
        if (this.mTarget != null && this.mTarget.isReady()) {
            if (!this.mRenderOverNav && VERSION.SDK_INT >= 21) {
                LayoutParams contentLP = (LayoutParams) getLayoutParams();
                if (contentLP != null) {
                    contentLP.bottomMargin = (int) this.window.getAttributes().verticalMargin;
                    contentLP.leftMargin = (int) this.window.getAttributes().horizontalMargin;
                    contentLP.rightMargin = (int) this.window.getAttributes().horizontalMargin;
                    contentLP.topMargin = (int) this.window.getAttributes().verticalMargin;
                }
            }
            Point targetPoint = this.mTarget.getPoint();
            Rect targetBounds = this.mTarget.getBounds();
            setPosition(targetPoint);
            int height = getMeasuredHeight();
            int midPoint = height / 2;
            int yPos = targetPoint.y;
            int radius = Math.max(targetBounds.height(), targetBounds.width()) / 2;
            if (this.mShape != null)
                radius = this.mShape.getHeight() / 2;

            if (yPos > midPoint) {
                this.mContentTopMargin = 0;
                this.mContentBottomMargin = ((height - yPos) + radius) + this.mShapePadding;
                this.mGravity = 80;
            } else {
                this.mContentTopMargin = (yPos + radius) + this.mShapePadding;
                this.mContentBottomMargin = 0;
                this.mGravity = 48;
            }
        }
        applyLayoutParams();
    }

    private void applyLayoutParams() {
        if (this.mContentBox != null && this.mContentBox.getLayoutParams() != null) {
            LayoutParams contentLP = (LayoutParams) this.mContentBox.getLayoutParams();
            boolean layoutParamsChanged = false;
            if (contentLP.bottomMargin != this.mContentBottomMargin) {
                contentLP.bottomMargin = this.mContentBottomMargin;
                layoutParamsChanged = true;
            }
            if (contentLP.topMargin != this.mContentTopMargin) {
                contentLP.topMargin = this.mContentTopMargin;
                layoutParamsChanged = true;
            }
            if (contentLP.gravity != this.mGravity) {
                contentLP.gravity = this.mGravity;
                layoutParamsChanged = true;
            }
            if (layoutParamsChanged) {
                this.mContentBox.setLayoutParams(contentLP);
            }
        }
    }

    private void setPosition(@NonNull Point point) {
        setPosition(point.x, point.y);
    }

    private void setPosition(int x, int y) {
        this.mXPosition = x;
        this.mYPosition = y;
    }

    private void setTitleText(@Nullable String contentText) {

        if (contentText != null && !"".equals(contentText)) {
            this.mTitleTextView.setVisibility(View.VISIBLE);
            this.mTitleTextView.setText(contentText);
        }


    }

    private void setContentText(@Nullable String contentText) {

        if (contentText != null && !contentText.equals("")) {
            this.mContentTextView.setVisibility(View.VISIBLE);
            this.mContentTextView.setText(contentText);
        }


    }

    private void setDismissText(@Nullable String dismissText) {

        if (dismissText != null && !dismissText.equals("")) {
            this.mDismissButton.setVisibility(View.VISIBLE);
            this.mDismissButton.setText(dismissText);
            updateDismissButton();
        }

        updateDismissButton();
    }

    private void setDismissStyle(Typeface dismissStyle) {
        this.mDismissButton.setTypeface(dismissStyle);
        updateDismissButton();
    }

    private void setTitleTextColor(int textColour) {
        this.mTitleTextView.setTextColor(textColour);
    }

    private void setDismissTextSize(int dismissTextSize) {
        if (dismissTextSize > 0) {
            this.mDismissButton.setTextSize(2, (float) dismissTextSize);
        }
    }

    private void setTitleTextSize(int titleTextSize) {
        if (titleTextSize > 0) {
            this.mTitleTextView.setTextSize(2, (float) titleTextSize);
        }
    }

    @Nullable
    public IAnimationFactory getAnimationFactory() {
        return this.mAnimationFactory;
    }

    private void setAnimationFactory(IAnimationFactory animationFactory) {
        this.mAnimationFactory = animationFactory;
    }

    private void setListeners(List<IShowcaseListener> mListeners) {
        this.mListeners = mListeners;
    }

    private void setContentTextSize(int contentTextSize) {
        if (contentTextSize > 0) {
            this.mContentTextView.setTextSize(2, (float) contentTextSize);
        }
    }

    private void setContentTextColor(int textColour) {
        this.mContentTextView.setTextColor(textColour);
    }

    private void setDismissTextColor(int textColour) {
        this.mDismissButton.setTextColor(textColour);
    }

    private void setShapePadding(int padding) {
        this.mShapePadding = padding;
    }

    private void setDismissOnTouch(boolean dismissOnTouch) {
        this.mDismissOnTouch = dismissOnTouch;
    }

    private void setShouldRender() {
        this.mShouldRender = true;
    }

    private void setMaskColour(int maskColour) {
        this.mMaskColour = maskColour;
    }

    private void setDelay(long delayInMillis) {
        this.mDelayInMillis = delayInMillis;
    }

    private void setFadeDuration(long fadeDurationInMillis) {
        this.mFadeDurationInMillis = fadeDurationInMillis;
    }

    private void setTargetTouchable(boolean targetTouchable) {
        this.mTargetTouchable = targetTouchable;
    }

    private void setDismissOnTargetTouch(boolean dismissOnTargetTouch) {
        this.mDismissOnTargetTouch = dismissOnTargetTouch;
    }

    public void addShowcaseListener(IShowcaseListener showcaseListener) {
        if (this.mListeners != null) {
            this.mListeners.add(showcaseListener);
        }
    }

    public void removeShowcaseListener(IShowcaseListener showcaseListener) {
        if (this.mListeners != null) {
            this.mListeners.remove(showcaseListener);
        }
    }

    void setDetachedListener(IDetachedListener detachedListener) {
        this.mDetachedListener = detachedListener;
    }

    private void setShape(Shape mShape) {
        this.mShape = mShape;
    }

    public void setConfig(@Nullable ShowcaseConfig config) {
        if (config == null)
            return;

        this.mDismissOnTargetTouch = config.isDismissOnTargetTouch();
        this.mTargetTouchable = config.isTargetTouchable();
        this.mSingleUse = config.isSingleUse();
        this.mRenderOverNav = config.isRenderOverNav();
        this.mDismissOnTouch = config.isDismissOnTouch();
        this.mDelayInMillis = config.getDelay();
        this.mFadeDurationInMillis = config.getFadeDuration();
        this.mShapePadding = config.getShapePadding();
        this.mMaskColour = config.getMaskColor();
        this.mAnimationFactory = config.getAnimationFactory();
        if (config.getShape() == ShowcaseConfig.Shape.CIRCLE)
            this.mShape = new CircleShape(this.mTarget);
        else if (config.getShape() == ShowcaseConfig.Shape.RECTANGLE)
            this.mShape = new RectangleShape(this.mTarget, false);
        else
            this.mShape = new NoShape();

        setContentTextColor(config.getContentTextColor());
        setContentTextSize(config.getContentTextSize());
        setDismissTextColor(config.getDismissTextColor());
        setDismissTextSize(config.getDismissTextSize());
        setDismissStyle(config.getDismissTextStyle());
        setTitleTextColor(config.getTitleTextColor());
        setTitleTextSize(config.getTitleTextSize());

    }

    private void updateDismissButton() {
        if (this.mDismissButton == null) {
            return;
        }
        if (TextUtils.isEmpty(this.mDismissButton.getText())) {
            this.mDismissButton.setVisibility(View.GONE);
        } else {
            this.mDismissButton.setVisibility(View.VISIBLE);
        }
    }

    public boolean hasFired() {
        return this.mPrefsManager.hasFired();
    }

    private void singleUse(String showcaseID) {
        this.mSingleUse = true;
        this.mPrefsManager = new PrefsManager(getContext(), showcaseID);
    }

    private void removeFromWindow() {
        if (getParent() != null && (getParent() instanceof ViewGroup)) {
            ((ViewGroup) getParent()).removeView(this);
        }
        if (this.mBitmap != null) {
            this.mBitmap.recycle();
            this.mBitmap = null;
        }
        this.mEraser = null;
        this.mAnimationFactory = null;
        this.mCanvas = null;
        this.mHandler = null;
        getViewTreeObserver().removeGlobalOnLayoutListener(this.observer);
        this.observer = null;
        if (this.mPrefsManager != null) {
            this.mPrefsManager.close();
        }
        this.mPrefsManager = null;
    }

    public void show(@NonNull Window window) {
        if (this.mSingleUse) {
            if (this.mPrefsManager.hasFired()) {
                return;
            }
            this.mPrefsManager.setFired();
        }
        this.window = window;
        ((ViewGroup) window.getDecorView()).addView(this);
        setShouldRender();
        this.mHandler = new Handler();
        this.mHandler.postDelayed(() -> animateIn(), this.mDelayInMillis);
        updateDismissButton();
    }

    private void hide() {
        this.mWasDismissed = true;
        if (this.mAnimationFactory != null) {
            animateOut();
        } else {
            removeFromWindow();
        }
    }

    private void animateIn() {
        setVisibility(View.INVISIBLE);
        this.mAnimationFactory.animateInView(this, this.mTarget.getPoint(), this.mFadeDurationInMillis, () -> {
            setVisibility(View.VISIBLE);
            notifyOnDisplayed();
        });
    }

    private void animateOut() {
        this.mAnimationFactory.animateOutView(this, this.mTarget.getPoint(), this.mFadeDurationInMillis, () -> {
            setVisibility(INVISIBLE);
            removeFromWindow();

        });
    }

    public void resetSingleUse() {
        if (this.mSingleUse && this.mPrefsManager != null) {
            this.mPrefsManager.resetShowcase();
        }
    }

    private void setRenderOverNavigationBar(boolean mRenderOverNav) {
        this.mRenderOverNav = mRenderOverNav;
    }


    public static class MaterialShowcaseViewBuilder {
        private static final int CIRCLE_SHAPE = 0;
        private static final int NO_SHAPE = 2;
        private static final int RECTANGLE_SHAPE = 1;
        private final Activity activity;
        private final List<IShowcaseListener> listeners = new ArrayList<>();
        private ShowcaseConfig config = new ShowcaseConfig();
        private String contentText;
        private Typeface dismissStyle;
        private String dismissText;
        private boolean fullWidth = false;
        private int shapeType = 0;
        private Target target;
        private String titleText;

        public MaterialShowcaseViewBuilder(Activity activity) {
            this.activity = activity;
        }

        @NonNull
        public MaterialShowcaseViewBuilder withConfig(ShowcaseConfig c) {
            this.config = c;
            return this;
        }

        @NonNull
        public MaterialShowcaseViewBuilder withDismissOnTouch(boolean mDismissOnTouch) {
            this.config.setDismissOnTouch(mDismissOnTouch);
            return this;
        }

        @NonNull
        public MaterialShowcaseViewBuilder withRenderOverNav(boolean mRenderOverNav) {
            this.config.setRenderOverNav(mRenderOverNav);
            return this;
        }

        @NonNull
        public MaterialShowcaseViewBuilder withMaskColour(@ColorRes int mMaskColour) {
            this.config.setMaskColor(this.activity.getResources().getColor(mMaskColour));
            return this;
        }

        @NonNull
        public MaterialShowcaseViewBuilder withFadeDurationInMillis(long mFadeDurationInMillis) {
            this.config.setFadeDuration(mFadeDurationInMillis);
            return this;
        }

        @NonNull
        public MaterialShowcaseViewBuilder withDelayInMillis(long mDelayInMillis) {
            this.config.setDelay(mDelayInMillis);
            return this;
        }

        @NonNull
        public MaterialShowcaseViewBuilder withTargetTouchable(boolean mTargetTouchable) {
            this.config.setTargetTouchable(mTargetTouchable);
            return this;
        }

        @NonNull
        public MaterialShowcaseViewBuilder withDismissOnTargetTouch(boolean mDismissOnTargetTouch) {
            this.config.setDismissOnTouch(mDismissOnTargetTouch);
            return this;
        }

        @NonNull
        public MaterialShowcaseViewBuilder setTarget(Target target) {
            this.target = target;
            return this;
        }

        @NonNull
        public MaterialShowcaseViewBuilder setTarget(View view) {
            this.target = new ViewTarget(view);
            return this;
        }

        @NonNull
        public MaterialShowcaseViewBuilder setDismissText(int resId) {
            return setDismissText(this.activity.getString(resId));
        }

        @NonNull
        public MaterialShowcaseViewBuilder setDismissText(String dismissText) {
            this.dismissText = dismissText;
            return this;
        }

        @NonNull
        public MaterialShowcaseViewBuilder setDismissStyle(Typeface dismissStyle) {
            this.dismissStyle = dismissStyle;
            return this;
        }

        @NonNull
        public MaterialShowcaseViewBuilder setContentText(int resId) {
            return setContentText(this.activity.getString(resId));
        }

        @NonNull
        public MaterialShowcaseViewBuilder setContentText(String text) {
            this.contentText = text;
            return this;
        }

        @NonNull
        public MaterialShowcaseViewBuilder setTitleText(int resId) {
            return setTitleText(this.activity.getString(resId));
        }

        @NonNull
        public MaterialShowcaseViewBuilder setTitleText(String text) {
            this.titleText = text;
            return this;
        }

        @NonNull
        public MaterialShowcaseViewBuilder setListener(IShowcaseListener listener) {
            addShowcaseListener(listener);
            return this;
        }

        private void addShowcaseListener(IShowcaseListener listener) {
            this.listeners.add(listener);
        }

        @NonNull
        public MaterialShowcaseViewBuilder withCircleShape() {
            this.shapeType = 0;
            return this;
        }

        @NonNull
        public MaterialShowcaseViewBuilder withoutShape() {
            this.shapeType = 2;
            return this;
        }

        @NonNull
        public MaterialShowcaseViewBuilder withRectangleShape() {
            return withRectangleShape(false);
        }

        @NonNull
        MaterialShowcaseViewBuilder withRectangleShape(boolean fullWidth) {
            this.shapeType = 1;
            this.fullWidth = fullWidth;
            return this;
        }

        @NonNull
        public MaterialShowcaseView build() {
            MaterialShowcaseView result = new MaterialShowcaseView(this.activity);
            result.setConfig(this.config);
            result.setTarget(this.target);
            result.setListeners(this.listeners);
            result.setTitleText(this.titleText);
            result.setContentText(this.contentText);
            result.setDismissStyle(this.dismissStyle);
            result.setDismissText(this.dismissText);

            switch (this.shapeType) {
                case 0:
                    result.setShape(new CircleShape(result.getTarget()));
                    break;
                case 1:
                    result.setShape(new RectangleShape(result.getTarget(), this.fullWidth));
                    break;
                default:
                    result.setShape(new NoShape());
                    break;
            }
            result.setAnimationFactory(this.config.getAnimationFactory());
            return result;
        }

        @NonNull
        public MaterialShowcaseViewBuilder setConfig(ShowcaseConfig config) {
            this.config = config;
            return this;
        }
    }


}
