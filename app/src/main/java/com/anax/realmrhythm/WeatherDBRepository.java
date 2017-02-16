package com.anax.realmrhythm;


import com.anax.realmrhythmlibrary.RepositoryImpl;

import io.realm.RealmConfiguration;

/**
 * Created by anas alaa on 14/01/2017.
 */

public class WeatherDBRepository extends RepositoryImpl<WeatherRealm> {


    WeatherDBRepository(RealmConfiguration realmConfiguration) {
        super(realmConfiguration);
    }

    @Override
    public WeatherRealm createInstance(WeatherRealm weatherRealm) {
        return new WeatherRealm(weatherRealm.getName(), weatherRealm.getTemp());
    }

    @Override
    public WeatherRealm updateInstance(WeatherRealm instance, WeatherRealm item) {
        //do NOT update primary key.
//        instance.setName(item.getName());
        instance.setTemp(item.getTemp());
        return instance;
    }
}
