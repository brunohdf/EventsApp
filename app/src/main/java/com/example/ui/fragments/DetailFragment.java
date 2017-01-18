package com.example.ui.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.custom.RegisterDialog;
import com.example.events.ReplaceFragmentEvent;
import com.example.eventsapp.R;
import com.example.model.EventModel;
import com.example.model.ScheduleModel;
import com.example.tasks.SendTask;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailFragment extends Fragment implements SendTask.SendTaskListener, RegisterDialog.RegisterTaskListener {

    EventModel event;

    @BindView(R.id.textview_title)
    TextView title;

    @BindView(R.id.textview_enrolled_users)
    TextView enrolledUsers;

    @BindView(R.id.textview_description)
    TextView description;

    @BindView(R.id.textview_location)
    TextView location;

    @BindView(R.id.textview_date)
    TextView date;

    @BindView(R.id.textview_time)
    TextView time;

    @BindView(R.id.button_register)
    TextView register;

    @BindView(R.id.linearlayout_schedule)
    LinearLayout scheduleContainer;

    private ProgressDialog progressDialog;

    public void setEvent(EventModel event) {
        this.event = event;
    }

    private void setupProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.sending));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    private  int mRegistrationNumber;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);

        setupProgressDialog();

        title.setText(event.getTitle());
        mRegistrationNumber = event.getEnrolledUsers();
        setupEnrolledUsers(mRegistrationNumber);
        description.setText(event.getDescription());
        location.setText(getContext().getString(R.string.event_location, event.getLocation()));
        date.setText(getContext().getString(R.string.event_date, event.getDate()));
        time.setText(getContext().getString(R.string.event_time, event.getTime()));

        List<ScheduleModel> list = event.getSchedule();
        for (ScheduleModel schedule: list) {
            scheduleContainer.addView(createScheduleItemView(getContext(), schedule));
        }

        return view;
    }

    private void setupEnrolledUsers(int qt) {
        if (qt > 0) {
            enrolledUsers.setVisibility(View.VISIBLE);
            enrolledUsers.setText(String.format(getContext().getResources().getQuantityString(R.plurals.enrolled_users_number, qt, qt)));
        }
    }


    public View createScheduleItemView(Context context, ScheduleModel schedule) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View scheduleItem = inflater.inflate(R.layout.schedule_item_view, null, false);
        TextView title = (TextView) scheduleItem.findViewById(R.id.textview_schedule_title);
        TextView presenter = (TextView) scheduleItem.findViewById(R.id.textview_schedule_presenter);
        title.setText(schedule.getTitle());
        presenter.setText(schedule.getPresenter());

        return scheduleItem;
    }

    @OnClick(R.id.button_register)
    @SuppressWarnings("unused")
    public void register() {
        RegisterDialog dialog = new RegisterDialog();
        dialog.setRegisterTaskListener(this);
        dialog.show(getActivity().getSupportFragmentManager(), "RegisterDialog");
    }


    @Override
    public void onSuccess() {
        progressDialog.dismiss();
        FinishFragment fragment = new FinishFragment();
        EventBus.getDefault().post(new ReplaceFragmentEvent(fragment, true));
    }

    @Override
    public void afterRegister() {
        Toast.makeText(getContext(), "Obrigado!", Toast.LENGTH_SHORT).show();
        register.setEnabled(false);
        register.setAlpha(.5f);
        setupEnrolledUsers(mRegistrationNumber + 1);
    }
}
