package com.bombon.mi_challenge.service;

import android.util.Log;

import com.bombon.mi_challenge.dao.DeliveryDao;
import com.bombon.mi_challenge.model.Delivery;
import com.bombon.mi_challenge.remote.DeliveryRemote;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * Created by Vaughn on 8/18/17.
 */

public class DeliveryService {
    private static DeliveryRemote remote;
    private static DeliveryDao dao;

    @Inject
    DeliveryService(DeliveryRemote remote, DeliveryDao dao) {
        this.remote = remote;
        this.dao = dao;
    }

    public static void getDeliveries(final ServiceCallback callback){
        remote.getDeliveries().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<List<Delivery>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Response<List<Delivery>> response) {
                        Log.i("Code:", ""+response.code());
                        if (response.code() == 200){
                            dao.clear();
                            for (Delivery delivery: response.body()){
                                delivery.setId(dao.getId());
                                dao.save(delivery);
                            }
                        }
                        else{
                            onError(new HttpException(response));
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        callback.getResult(((HttpException) e).code(), dao.get());
                    }

                    @Override
                    public void onComplete() {
                        callback.getResult(200, dao.get());
                    }
                });
    }

    public static Delivery getDelivery(int id){
        return dao.get(id);
    }
}
