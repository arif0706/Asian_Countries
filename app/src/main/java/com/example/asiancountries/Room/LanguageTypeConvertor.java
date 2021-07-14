package com.example.asiancountries.Room;

import androidx.room.TypeConverter;

import com.example.asiancountries.Model.Language;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class LanguageTypeConvertor {
    static Gson gson=new Gson();

    @TypeConverter
    public static String ArrayToList(Language[] data){

        return gson.toJson(data);
    }

    @TypeConverter
    public Language[] LanguageToArray(String data){
        Type type=new TypeToken<Language[]>(){}.getType();
        return gson.fromJson(data,type);
    }
}
