package com.example.asiancountries.Model;

public class Language {
    public String name;
    public String nativeName;

    public Language() {
    }

    public Language(String name, String nativeName) {
        this.name = name;
        this.nativeName = nativeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNativeName() {
        return nativeName;
    }

    public void setNativeName(String nativeName) {
        this.nativeName = nativeName;
    }
}
