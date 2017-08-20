package com.bombon.mi_challenge.di.component;

import com.bombon.mi_challenge.activity.MainActivity;
import com.bombon.mi_challenge.di.module.DaoModule;
import com.bombon.mi_challenge.di.module.RemoteModule;
import com.bombon.mi_challenge.di.scope.AppScope;
import com.bombon.mi_challenge.fragment.DeliveryDetailFragment;
import com.bombon.mi_challenge.fragment.DeliveryListFragment;

import dagger.Component;

/**
 * Created by Vaughn on 8/18/17.
 */

@AppScope
@Component(modules = {RemoteModule.class, DaoModule.class})

public interface AppComponent {
    void inject(MainActivity activity);
    void inject(DeliveryDetailFragment fragment);
    void inject(DeliveryListFragment fragment);
}
