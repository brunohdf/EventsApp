package com.example.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eventsapp.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ffranca on 30/08/16.
 */
public class WelcomeFragment extends Fragment {

    private SignUp onSignUpListener;

    public void setOnSignUpListener(SignUp onSignUpListener) {
        this.onSignUpListener = onSignUpListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.button_signup)
    @SuppressWarnings("unused")
    void singUp(View view) {
        if(onSignUpListener != null) {
            onSignUpListener.onSignUp(getContext());
        }
    }

    public interface SignUp {
        void onSignUp(Context context);
    }
}
