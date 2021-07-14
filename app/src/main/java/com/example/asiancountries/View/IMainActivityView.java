package com.example.asiancountries.View;

import com.example.asiancountries.Model.Country;

import java.util.List;

public interface IMainActivityView {
    void onGettingList(List<Country> countries);
    void onGettingListFromRoom(List<Country> countries);
}
