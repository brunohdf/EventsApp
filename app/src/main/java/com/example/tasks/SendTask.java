package com.example.tasks;

import android.os.AsyncTask;

public class SendTask extends AsyncTask<Void, Void, Void> {

    private SendTaskListener listener;

    public SendTaskListener getListener() {
        return listener;
    }

    public void setListener(SendTaskListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (listener != null) {
            listener.onSuccess();
        }
    }

    public interface SendTaskListener {
        void onSuccess();
    }
}
