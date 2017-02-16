package com.anax.realmrhythm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.anax.realmrhythmlibrary.RealmListSpecification;
import com.anax.realmrhythmlibrary.RealmSingleSpecification;

import java.util.List;
import java.util.logging.Logger;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

import static com.anax.realmrhythm.WeatherRealm.FieldName;
import static com.anax.realmrhythm.WeatherRealm.FieldTemp;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);
        final WeatherDBRepository weatherDBRepository = new WeatherDBRepository(config);

        weatherDBRepository.add(new WeatherRealm("Egypt", 1));
        weatherDBRepository.add(new WeatherRealm("Oman", 2));
        weatherDBRepository.add(new WeatherRealm("Qatar", 3));
        weatherDBRepository.add(new WeatherRealm("Tones", 4));
        weatherDBRepository.add(new WeatherRealm("USA", 5));
        weatherDBRepository.add(new WeatherRealm("UEA", 5));
        weatherDBRepository.add(new WeatherRealm("Palastine", 7));
        weatherDBRepository.add(new WeatherRealm("Kwite", 8));


        weatherDBRepository.update(new WeatherRealm("Egypt", 99), new RealmListSpecification<WeatherRealm>() {
            @Override
            public RealmResults<WeatherRealm> toRealmResults(Realm realm) {
                return realm.where(WeatherRealm.class).equalTo(FieldName, "Egypt").findAll();
            }
        });

        weatherDBRepository.update(new WeatherRealm("Egypt", 1111), new RealmSingleSpecification<WeatherRealm>() {
            @Override
            public WeatherRealm toRealmResults(Realm realm) {
                return realm.where(WeatherRealm.class).equalTo(FieldName, "Egypt").findFirst();
            }
        });

        WeatherRealm weatherRealm = weatherDBRepository.queryItem(new RealmSingleSpecification<WeatherRealm>() {
            @Override
            public WeatherRealm toRealmResults(Realm realm) {
                return realm.where(WeatherRealm.class).equalTo(FieldName, "Egypt").findFirst();
            }
        });

        weatherRealm.getName();
        weatherRealm.getTemp();

        WeatherRealm weatherRealm1 = weatherDBRepository.queryItem(new RealmSingleSpecification<WeatherRealm>() {
            @Override
            public WeatherRealm toRealmResults(Realm realm) {
                return realm.where(WeatherRealm.class).equalTo(FieldTemp, 20).findFirst();
            }
        });
        if (weatherRealm1 != null) {
            weatherRealm1.getName();
            weatherRealm1.getTemp();
        }

        List<WeatherRealm> weatherRealms = weatherDBRepository.queryList(new RealmListSpecification<WeatherRealm>() {
            @Override
            public RealmResults<WeatherRealm> toRealmResults(Realm realm) {
                return realm.where(WeatherRealm.class).between(FieldTemp, 1, 100).findAll();
            }
        }, 0, 100);

        weatherRealms.size();


        weatherDBRepository.remove(new RealmListSpecification<WeatherRealm>() {
            @Override
            public RealmResults<WeatherRealm> toRealmResults(Realm realm) {
                return realm.where(WeatherRealm.class).between(FieldTemp, 0, 10000000).findAll();
            }
        });

        List<WeatherRealm> weatherRealms2 = weatherDBRepository.queryList(new RealmListSpecification<WeatherRealm>() {
            @Override
            public RealmResults<WeatherRealm> toRealmResults(Realm realm) {
                return realm.where(WeatherRealm.class).between(FieldTemp, 1, 100).findAll();
            }
        }, 0, 100);


        Observable.just(new WeatherRealm("123", 1233))
                .map(new Function<WeatherRealm, WeatherRealm>() {
                    @Override
                    public WeatherRealm apply(WeatherRealm weatherRealm) throws Exception {
                        weatherDBRepository.add(weatherRealm);
                        return weatherRealm;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<WeatherRealm>() {
                    @Override
                    public void onNext(WeatherRealm weatherRealm) {
                        Log.d("observeOn", weatherRealm.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("observeOn", e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d("observeOn", "onComplete");
                    }
                });

        weatherDBRepository.queryItemObservable(new RealmSingleSpecification<WeatherRealm>() {
            @Override
            public WeatherRealm toRealmResults(Realm realm) {
                return realm.where(WeatherRealm.class).equalTo(FieldTemp, 3).findFirst();
            }
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<WeatherRealm>() {
                    @Override
                    public void onNext(WeatherRealm weatherRealm) {
                        Log.d("observeOn", weatherRealm.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("observeOn", e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d("observeOn", "onComplete");
                    }
                });
        ;
    }
}
