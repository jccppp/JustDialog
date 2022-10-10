package com.jccppp.dialog;

import android.view.View;


public abstract class OnDoubleClickListener implements View.OnClickListener {

    private long lastTime = 0;

    private long period = 500;


    public OnDoubleClickListener() {
    }

    public OnDoubleClickListener(final long duration) {
        period = duration;
    }

    public abstract void onDebouncingClick(View v);

    @Override
    public final void onClick(View v) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTime > period) {
            lastTime = currentTime;
            onDebouncingClick(v);
        }
    }


}
