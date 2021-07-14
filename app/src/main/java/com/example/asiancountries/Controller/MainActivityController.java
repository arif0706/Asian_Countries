package com.example.asiancountries.Controller;

import android.content.Context;

import com.example.asiancountries.Api.RetrofitClient;
import com.example.asiancountries.Model.Country;
import com.example.asiancountries.Room.AppDatabase;
import com.example.asiancountries.View.IMainActivityView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityController implements IMainActivityController{

    IMainActivityView mainActivityView;

    public MainActivityController(IMainActivityView mainActivityView) {
        this.mainActivityView = mainActivityView;
    }

    @Override
    public void getCountries() {
        Call<List<Country>> call= RetrofitClient.getInstance().getMyApi().getCountries();
        call.enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                mainActivityView.onGettingList(response.body());

            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {

            }
        });
    }

    @Override
    public void getCountriesFromRoom(Context context) {
        AppDatabase appDatabase=AppDatabase.getInstance(context);
        List<Country> countries=appDatabase.databaseDao().getCountries();
        mainActivityView.onGettingListFromRoom(countries);
    }
}
