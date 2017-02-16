package com.anax.realmrhythmlibrary;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmResults;

/**
 * Created by anas alaa on 14/01/2017.
 */

public interface RealmListSpecification<T extends RealmModel> extends Specification<T> {
    RealmResults<T> toRealmResults(Realm realm);
}
