package com.bombon.mi_challenge.di.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Vaughn on 8/18/17.
 */

@Module
public class ContextModule {

    private final Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    @Provides
    Context provideContext() {
        return context;
    }
}
