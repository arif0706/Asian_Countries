package com.example.asiancountries.Room;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class ArrayTypeConvertor {
    static Gson gson=new Gson();

    @TypeConverter
    public static String ArrayToList(String[] data){
        return gson.toJson(data);
    }

    @TypeConverter
    public String[] StringToArray(String data){
        Type type=new TypeToken<String[]>(){}.getType();
        return gson.fromJson(data,type);
    }

}
