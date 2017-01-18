package com.example.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.events.ReplaceFragmentEvent;
import com.example.eventsapp.R;
import com.example.ui.fragments.WelcomeFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements WelcomeFragment.SignUp {

    @BindView(R.id.backgroung_imageview)
    ImageView backgroundImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        WelcomeFragment fragment = new WelcomeFragment();
        fragment.setOnSignUpListener(this);

        replaceFragment(R.id.framelayout_splash_container, fragment, false, false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @SuppressWarnings("unused")
    public void onMessageEvent(ReplaceFragmentEvent event) {
        replaceFragment(R.id.framelayout_splash_container, event.getFragment(), event.isAnimate(),
                true);
    }

    @Override
    public void onSignUp(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        startActivity(intent);
    }
}
