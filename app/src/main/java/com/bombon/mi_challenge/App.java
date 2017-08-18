package com.bombon.mi_challenge;

import android.app.Application;

import com.bombon.mi_challenge.di.component.AppComponent;
import com.bombon.mi_challenge.di.component.DaggerAppComponent;
import com.bombon.mi_challenge.di.module.ContextModule;
import com.bombon.mi_challenge.di.module.NetworkModule;

import io.realm.Realm;

/**
 * Created by Vaughn on 8/18/17.
 */

public class App extends Application {

    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        component = DaggerAppComponent.builder()
                .contextModule(new ContextModule(this))
                .networkModule(new NetworkModule(Constants.BASE_URL))
                .build();
    }

    public AppComponent getComponent() {
        return component;
    }
}