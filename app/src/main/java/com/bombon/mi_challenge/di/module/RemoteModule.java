package com.bombon.mi_challenge.di.module;

import com.bombon.mi_challenge.di.scope.AppScope;
import com.bombon.mi_challenge.remote.DeliveryRemote;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Vaughn on 8/18/17.
 */

@Module(includes = NetworkModule.class)
public class RemoteModule {
    @Provides
    @AppScope
    DeliveryRemote provideDeliveryRemote(Retrofit retrofit){
        return retrofit.create(DeliveryRemote.class);
    }
}
