package com.example.asiancountries.Api;

import com.example.asiancountries.Model.Country;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Api {

    String BASE_URL="https://restcountries.eu/";

    @GET("/rest/v2/region/Asia")
    Call<List<Country>> getCountries();

    @GET("/rest/v2/alpha/{code}")
    Call<Country> getCountry(@Path("code") String code);
}
