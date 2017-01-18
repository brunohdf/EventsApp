package com.example.events;

import android.support.v4.app.Fragment;

public class ReplaceFragmentEvent {

    private Fragment fragment;

    private boolean animate;

    public Fragment getFragment() {
        return fragment;
    }

    public boolean isAnimate() {
        return animate;
    }

    public ReplaceFragmentEvent(Fragment fragment, boolean animate) {
        this.fragment = fragment;
        this.animate = animate;
    }
}
