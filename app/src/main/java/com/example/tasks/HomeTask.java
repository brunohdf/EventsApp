package com.example.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.repository.ResultSet;
import com.example.repository.local.EventsRepository;

public class HomeTask extends AsyncTask<Void, Void, ResultSet> {

    private Context mContext;

    public HomeTask(Context context) {
        mContext = context;
    }

    private HomeTaskListener listener;

    public void setListener(HomeTaskListener listener) {
        this.listener = listener;
    }

    @Override
    protected ResultSet doInBackground(Void... voids) {
        ResultSet rs = new ResultSet();
        try {
            Thread.sleep(1500);
            EventsRepository repository = new EventsRepository(mContext);
            rs.setData(repository.getEvents());
            rs.setStatus(ResultSet.STATUS.SUCCESS);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return rs;
    }

    @Override
    protected void onPostExecute(ResultSet resultSet) {
        int i = 0;
        if (listener != null) {
            listener.onSuccess(resultSet);
        }

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    public interface HomeTaskListener {
        void onSuccess(ResultSet rs);
    }
}