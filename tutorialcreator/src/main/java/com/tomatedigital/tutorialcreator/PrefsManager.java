package com.tomatedigital.tutorialcreator;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

class PrefsManager {
    public static final int SEQUENCE_FINISHED = -1;
    private static final String PREFS_NAME = "material_showcaseview_prefs";
    private static final int SEQUENCE_NEVER_STARTED = 0;
    private static final String STATUS = "status_";
    private final String showcaseID;
    @Nullable
    private Context context;

    public PrefsManager(@Nullable Context context, String showcaseID) {
        this.context = context;
        this.showcaseID = showcaseID;
    }

    static void resetShowcase(@NonNull Context context, String showcaseID) {
        Editor edit = context.getSharedPreferences(PREFS_NAME, 0).edit();
        String stringBuilder = STATUS + showcaseID;
        edit.putInt(stringBuilder, SEQUENCE_NEVER_STARTED).apply();
    }

    public static void resetAll(@NonNull Context context) {
        context.getSharedPreferences(PREFS_NAME, 0).edit().clear().apply();
    }

    boolean hasFired() {
        return getSequenceStatus() == SEQUENCE_FINISHED;
    }

    void setFired() {
        setSequenceStatus(SEQUENCE_FINISHED);
    }

    int getSequenceStatus() {
        SharedPreferences sharedPreferences = this.context.getSharedPreferences(PREFS_NAME, 0);
        String stringBuilder = STATUS + this.showcaseID;
        return sharedPreferences.getInt(stringBuilder, SEQUENCE_NEVER_STARTED);
    }

    void setSequenceStatus(int status) {
        Editor edit = this.context.getSharedPreferences(PREFS_NAME, 0).edit();
        String stringBuilder = STATUS + this.showcaseID;
        edit.putInt(stringBuilder, status).apply();
    }

    public void resetShowcase() {
        resetShowcase(this.context, this.showcaseID);
    }

    public void close() {
        this.context = null;
    }
}
