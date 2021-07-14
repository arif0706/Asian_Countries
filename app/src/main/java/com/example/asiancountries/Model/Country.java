package com.example.asiancountries.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.asiancountries.Room.ArrayTypeConvertor;
import com.example.asiancountries.Room.ByteConvertor;
import com.example.asiancountries.Room.LanguageTypeConvertor;

import java.util.Comparator;


@Entity(tableName = "Countries",indices = @Index(unique = true,value = {"name"}))
public class Country {

    @PrimaryKey(autoGenerate = true)
    public int _id;
    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "capital")
    public String capital;

    @TypeConverters(ByteConvertor.class)
    @ColumnInfo(name = "flag",typeAffinity = ColumnInfo.BLOB)
    public String flag;

    @ColumnInfo(name = "region")
    public String region;

    @ColumnInfo(name = "subregion")
    public String subregion;

    @ColumnInfo(name = "population")
    public String population;

    @ColumnInfo(name = "alpha3Code")
    public String alpha3Code;


    @TypeConverters(ArrayTypeConvertor.class)
    @ColumnInfo(name = "borders")
    public String[] borders;

    @TypeConverters(LanguageTypeConvertor.class)
    @ColumnInfo(name = "languages")
    public Language[] languages;

    public Country() {
    }

    public Country(String name, String capital, String flag, String region, String subregion, String population, String[] borders, Language[] languages) {
        this.name = name;
        this.capital = capital;
        this.flag = flag;
        this.region = region;
        this.subregion = subregion;
        this.population = population;
        this.borders = borders;
        this.languages = languages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSubregion() {
        return subregion;
    }

    public void setSubregion(String subregion) {
        this.subregion = subregion;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String[] getBorders() {
        return borders;
    }

    public void setBorders(String[] borders) {
        this.borders = borders;
    }

    public Language[] getLanguages() {
        return languages;
    }

    public void setLanguages(Language[] languages) {
        this.languages = languages;
    }


    public static Comparator<Country> sortByNameAsc=new Comparator<Country>() {
        @Override
        public int compare(Country o1, Country o2) {
            return o1.getName().compareTo(o2.getName());
        }
    };
}
