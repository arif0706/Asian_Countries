package com.example.asiancountries.Controller;

import android.content.Context;

import com.example.asiancountries.Api.RetrofitClient;
import com.example.asiancountries.Model.Country;
import com.example.asiancountries.Room.AppDatabase;
import com.example.asiancountries.View.ICountryDetailsView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountryDetailsController implements ICountryDetailsController{
    ICountryDetailsView countryDetailsView;

    public CountryDetailsController(ICountryDetailsView countryDetailsView) {
        this.countryDetailsView = countryDetailsView;
    }
    @Override
    public void getCountryWithCode(String code) {
        Call<Country> countryCall= RetrofitClient.getInstance().getMyApi().getCountry(code);

        countryCall.enqueue(new Callback<Country>() {
            @Override
            public void onResponse(Call<Country> call, Response<Country> response) {
                countryDetailsView.onGettingCountry(response.body());
            }

            @Override
            public void onFailure(Call<Country> call, Throwable t) {

            }
        });
    }

    @Override
    public void getCountryWithCodeFromRoom(String code, Context context) {
        AppDatabase appDatabase=AppDatabase.getInstance(context);
        Country country=appDatabase.databaseDao().getCountry(code);

        countryDetailsView.onGettingCountry(country);
    }
}
