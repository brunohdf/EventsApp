package com.example.ui;

import android.os.Bundle;

import com.example.events.ReplaceFragmentEvent;
import com.example.eventsapp.R;
import com.example.model.EventModel;
import com.example.ui.fragments.DetailFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;


public class DetailActivity extends BaseActivity {

    public static String PARAM_EVENT = "EVENT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        ButterKnife.bind(this);

        DetailFragment fragment = new DetailFragment();
        fragment.setEvent((EventModel) getIntent().getParcelableExtra(PARAM_EVENT));
        replaceFragment(R.id.framelayout_home_container, fragment, false, false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @SuppressWarnings("unused")
    public void onMessageEvent(ReplaceFragmentEvent event) {
        replaceFragment(R.id.framelayout_home_container, event.getFragment(), event.isAnimate(),
                true);
    }

}
