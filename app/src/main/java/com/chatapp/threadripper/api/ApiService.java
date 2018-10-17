package com.chatapp.threadripper.api;

import android.appwidget.AppWidgetProviderInfo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService implements Callback<ApiResponseData> {

    CallbackApiListener listener;

    public static ApiService getInstance() {
        return new ApiService();
    }

    public interface CallbackApiListener {
        void onSuccess(ApiResponseData data);

        void onFailure(Throwable t);
    }

    public void addCallbackListener(CallbackApiListener listener) {
        this.listener = listener;
    }

    @Override
    public void onResponse(Call<ApiResponseData> call, Response<ApiResponseData> response) {
        if (response.isSuccessful()) {
            ApiResponseData data = response.body();
            listener.onSuccess(data);
            return;
        }

        ApiResponseData data = new ApiResponseData();
        try {
            data.setErrorMessage(response.errorBody().string());
        } catch (IOException e) {
            e.printStackTrace();
            data.setErrorMessage(e.getMessage());
        } finally {
            listener.onSuccess(data);
        }
    }

    @Override
    public void onFailure(Call<ApiResponseData> call, Throwable t) {
        listener.onFailure(t);
    }

    Retrofit getRetrofitInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.API_ROUTE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    public ApiService signUp(String email, String username, String password, String displayName) {
        ApiRoutes api = getRetrofitInstance().create(ApiRoutes.class);

        api.signUp(username, email, displayName, password).enqueue(this);
        return this;
    }

    public ApiService login(String username, String password) {
        ApiRoutes api = getRetrofitInstance().create(ApiRoutes.class);

        api.login(username, password).enqueue(this);
        return this;
    }
}