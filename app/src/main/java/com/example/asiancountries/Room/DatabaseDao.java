package com.example.asiancountries.Room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.asiancountries.Model.Country;

import java.util.List;

@Dao
public interface DatabaseDao {

    @Query("Select distinct *from Countries")
    List<Country> getCountries();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertCountry(Country country);

    @Query("Select distinct *from Countries where alpha3Code=:code")
    Country getCountry(String code);

    @Query("Delete from Countries")
    void deleteData();

}
