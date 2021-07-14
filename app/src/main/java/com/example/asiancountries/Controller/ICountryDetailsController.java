package com.example.asiancountries.Controller;

import android.content.Context;

public interface ICountryDetailsController {
    void getCountryWithCode(String code);
    void getCountryWithCodeFromRoom(String code, Context context);
}
