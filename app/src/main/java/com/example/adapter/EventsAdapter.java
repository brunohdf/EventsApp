package com.example.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.eventsapp.R;
import com.example.model.EventModel;

import java.util.List;


public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsViewHolder> {
 
    private Context mContext;
    private final List<EventModel> eventList;
    private final OnItemClickListener onClickItemView;
    private View mItemView;

    public class EventsViewHolder extends RecyclerView.ViewHolder {

        private TextView date;
        private TextView hour;
        private TextView title;
        private TextView subtitle;
        private RelativeLayout container;
        private LinearLayout schedule;
        private TextView registrations;

        public EventsViewHolder(View view) {
            super(view);
            date = (TextView) view.findViewById(R.id.textview_date);
            hour = (TextView) view.findViewById(R.id.textview_hour);
            title = (TextView) view.findViewById(R.id.textview_title);
            subtitle = (TextView) view.findViewById(R.id.textview_subtitle);
            container = (RelativeLayout) view.findViewById(R.id.relativelayout_container);
            schedule = (LinearLayout) view.findViewById(R.id.linearlayout_schedule);
            registrations = (TextView) view.findViewById(R.id.textview_enrolled_users);
        }
    }
 
 
    public EventsAdapter(Context context, List<EventModel> moviesList, OnItemClickListener listener) {
        this.eventList = moviesList;
        this.mContext = context;
        this.onClickItemView = listener;
    }
 
    @Override
    public EventsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mItemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_item, parent, false);

        return new EventsViewHolder(mItemView);
    }
 
    @Override
    public void onBindViewHolder(EventsViewHolder holder, int position) {
        final EventModel eventModel = eventList.get(position);
        holder.title.setText(eventModel.getTitle());
        holder.subtitle.setText(eventModel.getLocation());
        holder.date.setText(eventModel.getDate());
        holder.hour.setText(eventModel.getTime());

        int rNumber = eventModel.getEnrolledUsers();
        if (rNumber > 0) {
            if (rNumber > 0) {
                holder.registrations.setVisibility(View.VISIBLE);
                holder.registrations.setText(String.format(mContext.getResources().getQuantityString(R.plurals.enrolled_users_number, rNumber, rNumber)));
            }
        } else {
            holder.registrations.setVisibility(View.GONE);
        }

        if (position % 2 ==0) {
            holder.container.setBackgroundColor(ContextCompat.getColor(mContext, R.color.ligth_gray));
            holder.schedule.setBackground(mContext.getDrawable(R.drawable.shape_border_rigth_even));
        }

        mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickItemView.onItemClick(eventModel);
            }
        });

    }
 
    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(EventModel item);
    }
}