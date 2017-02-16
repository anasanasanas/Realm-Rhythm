package com.anax.realmrhythmlibrary;

import io.realm.Realm;
import io.realm.RealmModel;

/**
 * Created by anas alaa on 14/01/2017.
 */

public interface RealmSingleSpecification<T extends RealmModel> extends Specification<T> {
    T toRealmResults(Realm realm);
}
