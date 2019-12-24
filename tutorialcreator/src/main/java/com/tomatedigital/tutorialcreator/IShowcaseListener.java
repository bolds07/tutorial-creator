package com.tomatedigital.tutorialcreator;


@SuppressWarnings("EmptyMethod")
public interface IShowcaseListener {
    void onShowcaseDisplayed(MaterialShowcaseView showcaseView, int position);

    void onShowcaseDismissed(MaterialShowcaseView showcaseView, int position);

    void onStart(MaterialShowcaseSequence mss);

    void onFinish(MaterialShowcaseSequence mss);
}
