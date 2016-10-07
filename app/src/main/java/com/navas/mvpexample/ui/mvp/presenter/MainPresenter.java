package com.navas.mvpexample.ui.mvp.presenter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.navas.mvpexample.ui.mvp.model.MainModel;
import com.navas.mvpexample.ui.mvp.view.MainView;
import com.navas.mvpexample.ui.mvp.view.MainView.BroadcastButtonPrssedEvent;
import com.navas.mvpexample.ui.mvp.view.MainView.DisplayButtonPressedEvent;
import com.squareup.otto.Subscribe;

public class MainPresenter {

    private MainModel model;
    private MainView view;

    public MainPresenter(MainModel model, MainView view) {
        this.model = model;
        this.view = view;
    }

    @Subscribe
    public void onButtonPressed(DisplayButtonPressedEvent event) {
        view.displayName(event.getName());
    }

    @Subscribe
    public void onBroadcastButtonPressed(BroadcastButtonPrssedEvent event) {
        model.sendBroadcast();
    }

    @Subscribe
    public void onFetchDataButtonPressed(MainView.FetchDataPressedEvent event) {
        Activity activity = view.getActivity();
        if (activity == null) return;

        if (!TextUtils.isEmpty(event.getName())) {
            model.fetchData(activity.getApplicationContext(), event.getName());
        } else {
            view.showError("Empty name :(");
        }
    }
}
