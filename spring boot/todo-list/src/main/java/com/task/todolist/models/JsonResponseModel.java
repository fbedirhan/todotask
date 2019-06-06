package com.task.todolist.models;

import java.util.ArrayList;
import java.util.List;

public class JsonResponseModel {

    private boolean success;
    private String message;
    private List<Object> data = new ArrayList<>();


    public JsonResponseModel() {
    }

    public JsonResponseModel(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public JsonResponseModel(boolean success, List<Object> data) {
        this.success = success;
        this.data = data;
    }

    public JsonResponseModel(boolean success, String message, List<Object> data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<Object> getData() {
        return data;
    }
}
