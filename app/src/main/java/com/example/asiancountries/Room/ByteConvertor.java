package com.example.asiancountries.Room;

import androidx.room.TypeConverter;

public class ByteConvertor {

    @TypeConverter
    public static String ByteToString(byte[] data){
        return new String(data);
    }

    @TypeConverter
    public byte[] StringToByte(String data){
        return data.getBytes();
    }
}
