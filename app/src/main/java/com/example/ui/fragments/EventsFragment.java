package com.example.ui.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.adapter.EventsAdapter;
import com.example.custom.DividerItemDecoration;
import com.example.eventsapp.R;
import com.example.model.EventModel;
import com.example.repository.ResultSet;
import com.example.tasks.HomeTask;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventsFragment extends Fragment implements HomeTask.HomeTaskListener {

    private List<EventModel> eventList = new ArrayList<>();
    private EventsAdapter mAdapter;

    @BindView(R.id.recyclerview_events)
    protected RecyclerView recyclerView;

    @BindView(R.id.progressbar)
    protected ProgressBar progressBar;

    @BindView(R.id.textview_loading)
    protected TextView textviewLoading;

    private ProgressDialog progressDialog;

    private OnClickItemView onClickItemView;


    private void setupProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.sending));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        ButterKnife.bind(this, view);

        mAdapter = new EventsAdapter(getContext(), eventList, onClickItemView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext()));
        recyclerView.setAdapter(mAdapter);
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        textviewLoading.setVisibility(View.VISIBLE);

        setupProgressDialog();

        if (eventList.size() == 0) {
            HomeTask task = new HomeTask(getContext());
            task.setListener(this);
            task.execute();
        } else {
            eventList.addAll(eventList);
            refreshList(eventList);
        }

        return view;
    }

    @Override
    public void onSuccess(ResultSet resultSet) {
        refreshList(resultSet.getData());
    }

    public void refreshList(List<EventModel> list) {
        eventList.addAll(list);
        mAdapter.notifyDataSetChanged();

        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        textviewLoading.setVisibility(View.GONE);
    }

    public interface OnClickItemView extends EventsAdapter.OnItemClickListener {
        @Override
        void onItemClick(EventModel item);
    }

    public void setOnClickItemView(OnClickItemView onClickItemView) {
        this.onClickItemView = onClickItemView;
    }
}
