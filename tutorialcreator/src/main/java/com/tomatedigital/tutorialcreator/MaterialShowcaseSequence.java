package com.tomatedigital.tutorialcreator;

import android.app.Activity;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;

import java.util.ArrayDeque;
import java.util.Queue;

public class MaterialShowcaseSequence implements IDetachedListener {
    private final Activity mActivity;
    @NonNull
    private final Queue<MaterialShowcaseView> mShowcaseQueue;
    private final String sequenceName;
    private ShowcaseConfig mConfig;
    private PrefsManager mPrefsManager;
    private int mSequencePosition = 0;
    private boolean mSingleUse = false;
    private IShowcaseListener sequenceListener;
    private Window window;


    public MaterialShowcaseSequence(Activity activity) {
        this(activity, "");
    }

    public MaterialShowcaseSequence(Activity activity, String name) {
        this.mActivity = activity;
        this.mShowcaseQueue = new ArrayDeque<>(10);
        this.sequenceName = name;
    }

    public String getSequenceName() {
        return this.sequenceName;
    }

    public void setWindow(Window window) {

        this.window = window;
    }

    public void setSequenceListener(IShowcaseListener s) {
        this.sequenceListener = s;
    }

    public void addSequenceItem(View targetView, String title, String content) {
        addSequenceItem(targetView, title, content, "");
    }

    private void addSequenceItem(View targetView, String title, String content, String dismissText) {
        MaterialShowcaseView sequenceItem = new MaterialShowcaseView.MaterialShowcaseViewBuilder(this.mActivity).setTarget(targetView).setTitleText(title).setDismissText(dismissText).setContentText(content).build();
        if (this.mConfig != null) {
            sequenceItem.setConfig(this.mConfig);
        }
        this.mShowcaseQueue.add(sequenceItem);
    }

    public void addSequenceItem(MaterialShowcaseView sequenceItem) {
        this.mShowcaseQueue.add(sequenceItem);
    }

    @NonNull
    public MaterialShowcaseSequence singleUse(String sequenceID) {
        this.mSingleUse = true;
        this.mPrefsManager = new PrefsManager(this.mActivity, sequenceID);
        return this;
    }

    private boolean hasFired() {
        return this.mPrefsManager.getSequenceStatus() == PrefsManager.SEQUENCE_FINISHED;
    }

    public void start() {
        if (this.mSingleUse) {
            if (!hasFired()) {
                this.mSequencePosition = this.mPrefsManager.getSequenceStatus();
                if (this.mSequencePosition > 0) {
                    for (int i = 0; i < this.mSequencePosition; i++) {
                        this.mShowcaseQueue.poll();
                    }
                }
            } else {
                return;
            }
        }
        if (this.sequenceListener != null) {
            this.sequenceListener.onStart(this);
        }
        if (this.mShowcaseQueue.size() > 0) {
            showNextItem();
        }
    }

    private void showNextItem() {
        if (this.mShowcaseQueue.size() <= 0 || this.mActivity.isFinishing()) {
            if (this.sequenceListener != null) {
                this.sequenceListener.onFinish(this);
            }
            if (this.mSingleUse) {
                this.mPrefsManager.setFired();
                return;
            }
            return;
        }
        MaterialShowcaseView sequenceItem = this.mShowcaseQueue.remove();

        sequenceItem.setDetachedListener(this);
        if (this.window == null)
            sequenceItem.show(this.mActivity.getWindow());
        else
            sequenceItem.show(this.window);

        if (this.sequenceListener != null) {
            this.sequenceListener.onShowcaseDisplayed(sequenceItem, this.mSequencePosition);
        }
    }

    public void onShowcaseDetached(@NonNull MaterialShowcaseView showcaseView, boolean wasDismissed) {
        showcaseView.setDetachedListener(null);
        if (wasDismissed) {
            if (this.sequenceListener != null) {
                this.sequenceListener.onShowcaseDismissed(showcaseView, this.mSequencePosition);
            }
            this.mSequencePosition++;
            if (this.mPrefsManager != null) {
                this.mPrefsManager.setSequenceStatus(this.mSequencePosition);
            }
            showNextItem();
        }
    }

    public void setConfig(ShowcaseConfig config) {
        this.mConfig = config;
    }

}
