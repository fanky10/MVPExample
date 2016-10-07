package com.navas.mvpexample.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import com.navas.mvpexample.R;
import com.navas.mvpexample.ui.mvp.model.MainModel;
import com.navas.mvpexample.ui.mvp.model.RepositoryModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

import static android.R.attr.name;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class FetchRepositoriesService extends IntentService {
    private static final String TAG = FetchRepositoriesService.class.getName();
    private static final String ACTION_FETCH_REPOS = "com.navas.mvpexample.services.action.FETCH_REPOS";

    private static final String EXTRA_PARAM_NAME = "com.navas.mvpexample.services.extra.PARAM_NAME";

    public FetchRepositoriesService() {
        super("FetchRepositoriesService");
    }

    private GitHubService gitHubService;

    @Override
    public void onCreate() {
        super.onCreate();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(getString(R.string.github_base_url))
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        gitHubService =  retrofit.create(GitHubService.class);
    }

    public static void startActionFetchRepos(Context context, String name) {
        Intent intent = new Intent(context, FetchRepositoriesService.class);
        intent.setAction(ACTION_FETCH_REPOS);
        intent.putExtra(EXTRA_PARAM_NAME, name);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FETCH_REPOS.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM_NAME);
                handleActionFetchRepos(param1);
            }
        }
    }

    private void handleActionFetchRepos(String name) {
        Call<List<RepositoryModel>> call = gitHubService.listRepos(name);
        call.enqueue(new Callback<List<RepositoryModel>>() {
            @Override
            public void onResponse(Call<List<RepositoryModel>> call, Response<List<RepositoryModel>> response) {
                Log.d(TAG, "repository models "+response.body());
            }

            @Override
            public void onFailure(Call<List<RepositoryModel>> call, Throwable t) {
                Log.e(TAG,"on failure retrofit "+t.getMessage());
            }
        });
    }


    private interface GitHubService {
        @GET("users/{user}/repos")
        Call<List<RepositoryModel>> listRepos(@Path("user") String user);
    }
}
