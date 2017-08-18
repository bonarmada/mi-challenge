package com.bombon.mi_challenge.remote;

import com.bombon.mi_challenge.model.Delivery;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;

/**
 * Created by Vaughn on 8/18/17.
 */

public interface DeliveryRemote {
    @GET("/deliveries")
    Observable<Response<List<Delivery>>> getDeliveries();
}
