package com.example.repository.local;

import android.content.Context;

import com.example.eventsapp.R;
import com.example.model.EventModel;
import com.example.util.FileUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class EventsRepository {

    private Context mContext;
    private List<EventModel> postsList;

    public EventsRepository(Context context) {
        mContext = context;
        postsList = new ArrayList();
        String json = FileUtil.loadJsonRawResource(mContext, R.raw.events);
        EventModel[] posts = new Gson().fromJson(json, EventModel[].class);
        if (posts.length > 0) {
            postsList = Arrays.asList(posts);
        }
    }

    public List<EventModel> getEvents() {
        return postsList;
    }


    public EventModel getEventById(int id) {
        for (EventModel eventModel : postsList) {
            if (eventModel.getId() == id) {
                return eventModel;
            }
        }
        return null;
    }

}
