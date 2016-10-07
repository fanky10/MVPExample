package com.navas.mvpexample.ui.mvp.model;


import android.content.Context;
import android.util.Log;

import com.navas.mvpexample.R;
import com.navas.mvpexample.domain.BroadcastExecutor;
import com.navas.mvpexample.services.FetchRepositoriesService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

import static android.R.attr.name;

public class MainModel {
    private static final String TAG = MainModel.class.getName();
    static final String NAME_DEFAULT = "fanky10";

    private BroadcastExecutor broadcastExecutor;

    public MainModel(Context context) {
        this.broadcastExecutor = new BroadcastExecutor(context);
    }

    public String getName() {
        return NAME_DEFAULT;
    }

    public void sendBroadcast() {
        broadcastExecutor.sendBroadcast();
    }

    public void fetchData(Context context, String name) {
        FetchRepositoriesService.startActionFetchRepos(context, name);
    }
}
