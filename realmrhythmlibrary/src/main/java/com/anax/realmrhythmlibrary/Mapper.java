package com.anax.realmrhythmlibrary;

/**
 * Created by anas on 14/01/2017.
 */

public interface Mapper<T> {
    T createInstance(T t);

    T updateInstance(T instance, T item);
}
