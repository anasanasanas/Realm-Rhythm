package com.anax.realmrhythmlibrary;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmModel;
import io.realm.RealmResults;

/**
 * Created by Anas Alaa on 14/01/2017.
 */

public abstract class RepositoryImpl<R extends RealmModel> implements Repository<R> {


    private final RealmConfiguration realmConfiguration;

    public RepositoryImpl(final RealmConfiguration realmConfiguration) {
        this.realmConfiguration = realmConfiguration;
    }

    @Override
    public void add(final R item) {
        final Realm realm = Realm.getInstance(realmConfiguration);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(item);
            }
        });
        realm.close();
    }

    @Override
    public void add(final List<R> items) {
        final Realm realm = Realm.getInstance(realmConfiguration);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (R r : items) {
                    realm.copyToRealmOrUpdate(r);
                }
            }
        });
        realm.close();
    }

    @Override
    public void update(final R item, RealmListSpecification<R> specification) {
        final Realm realm = Realm.getInstance(realmConfiguration);
        final RealmResults<R> realmResults = specification.toRealmResults(realm);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (R r : realmResults) {
                    updateInstance(r, item);
                }
            }
        });
        realm.close();
    }

    @Override
    public void update(final R item, RealmSingleSpecification<R> specification) {
        final Realm realm = Realm.getInstance(realmConfiguration);
        final R result = specification.toRealmResults(realm);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                updateInstance(result, item);
            }
        });
        realm.close();
    }

    @Override
    public void remove(RealmListSpecification<R> specification) {
        final Realm realm = Realm.getInstance(realmConfiguration);
        final RealmResults<R> realmResults = specification.toRealmResults(realm);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realmResults.deleteAllFromRealm();
            }
        });
        realm.close();
    }

    @Override
    public List<R> queryList(RealmListSpecification<R> specification, int from, int size) {
        final Realm realm = Realm.getInstance(realmConfiguration);
        final RealmResults<R> realmResults = specification.toRealmResults(realm);

        final List<R> dataModels = new ArrayList<>();

        for (int i = from; i < realmResults.size() && i < from + size; i++) {
            dataModels.add(createInstance(realmResults.get(i)));
        }
        realm.close();
        return dataModels;
    }

    @Override
    public Observable<List<R>> queryListObservable(RealmListSpecification<R> specification, int from, int size) {
        List<R> rList = queryList(specification, from, size);
        if (rList.size() != 0)
            return Observable.just(rList);
        else
            return Observable.empty();
    }


    @Override
    public R queryItem(RealmSingleSpecification<R> specification) {
        final Realm realm = Realm.getInstance(realmConfiguration);
        final R r = specification.toRealmResults(realm);
        R object = null;
        if (r != null)
            object = createInstance(r);
        realm.close();
        return object;
    }

    @Override
    public Observable<R> queryItemObservable(RealmSingleSpecification<R> specification) {
        R r = queryItem(specification);
        if (r != null)
            return Observable.just(r);
        else
            return Observable.empty();
    }
}
