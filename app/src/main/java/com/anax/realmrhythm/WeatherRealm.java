package com.anax.realmrhythm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by anas alaa on 14/01/2017.
 */

public class WeatherRealm extends RealmObject {

    static final String FieldName = "name";
    static final String FieldTemp = "temp";

    @PrimaryKey
    private String name;
    private Integer temp;

    public WeatherRealm(String name, Integer temp) {
        this.name = name;
        this.temp = temp;
    }

    public WeatherRealm() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTemp() {
        return temp;
    }

    public void setTemp(Integer temp) {
        this.temp = temp;
    }
}