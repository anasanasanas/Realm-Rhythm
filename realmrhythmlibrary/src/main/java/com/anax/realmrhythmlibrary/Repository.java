package com.anax.realmrhythmlibrary;

import java.util.List;

import io.reactivex.Observable;
import io.realm.RealmModel;

/**
 * Created by anas alaa on 14/01/2017.
 */

interface Repository<T extends RealmModel> extends Mapper<T> {
    void add(T item);

    void add(List<T> items);

    void update(T item, RealmListSpecification<T> specification);

    void update(T item, RealmSingleSpecification<T> specification);

    void remove(RealmListSpecification<T> specification);

    List<T> queryList(RealmListSpecification<T> specification,int from, int size);

    Observable<List<T>> queryListObservable(RealmListSpecification<T> specification,int from, int size);

    T queryItem(RealmSingleSpecification<T> specification);

    Observable<T> queryItemObservable(RealmSingleSpecification<T> specification);
}
