package com.navas.mvpexample.ui.mvp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by facundo.scoccia on 10/7/2016.
 */

public class RepositoryModel {
    @SerializedName("name")
    public String mName;

    @SerializedName("private")
    public Boolean mIsPrivate;

    @SerializedName("description")
    public String mDescription;

    public String toString() {
        return String.format("name: %s / private: %s / desc: %s", mName, mIsPrivate, mDescription);
    }
}
