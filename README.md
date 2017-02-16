# Realm Rhythm [![](https://jitpack.io/v/anasanasanas/Realm-Rhythm.svg)](https://jitpack.io/#anasanasanas/Realm-Rhythm)


Android library makes using Realm database easier.

## Features
* Support RxJava 2
* Build on top of repository design pattern

## Installation

Add this to your module `build.gradle` file:

```
dependencies {
    ...
     compile 'com.github.anasanasanas:Realm-Rhythm:0.0.1'
}
```

Add this to your **root** `build.gradle` file:
```
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

##How to use Realm Rhythm ?
```
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
        instance.setTemp(item.getTemp());
        return instance;
    }
}
```

```
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
```

