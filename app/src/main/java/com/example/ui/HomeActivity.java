package com.example.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.events.ReplaceFragmentEvent;
import com.example.eventsapp.R;
import com.example.model.EventModel;
import com.example.ui.fragments.EventsFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;


public class HomeActivity extends BaseActivity implements EventsFragment.OnClickItemView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_header);
        ButterKnife.bind(this);
        EventsFragment fragment = new EventsFragment();
        fragment.setOnClickItemView(this);
        replaceFragment(R.id.framelayout_home_container, fragment, false, false);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onItemClick(EventModel event) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.PARAM_EVENT, event);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            moveTaskToBack(true);
            finish();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    @SuppressWarnings("unused")
    public void onMessageEvent(ReplaceFragmentEvent event) {
        Log.d(TAG, "Replace fragment event");
        replaceFragment(R.id.framelayout_home_container, event.getFragment(), event.isAnimate(),
                true);
    }

}
