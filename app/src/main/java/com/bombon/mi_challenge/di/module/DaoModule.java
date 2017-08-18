package com.bombon.mi_challenge.di.module;

import com.bombon.mi_challenge.dao.DeliveryDao;
import com.bombon.mi_challenge.di.scope.AppScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Vaughn on 8/18/17.
 */

@Module
public class DaoModule {
    @Provides
    @AppScope
    DeliveryDao provideDeliveryDao(){
        return new DeliveryDao();
    }
}
