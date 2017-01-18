package com.example.repository;

import java.util.List;

/**
 * Created by burno on 19/12/16.
 */

public class ResultSet {
    private List data;
    private STATUS status;
    private String message;

    public enum STATUS {
        SUCCESS,
        FAILURE
    }

    public ResultSet() {
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }

    public STATUS isStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess(){
        return status == STATUS.SUCCESS;
    }
}
