package com.example.avto.Service;

import android.content.Intent;

import com.example.avto.Model.CarModels.CarGeneration;
import com.example.avto.Model.CarModels.CarMark;
import com.example.avto.Model.CarModels.CarModel;
import com.example.avto.Model.CarPost;
import com.example.avto.Service.Model.FilterCountResultModel;
import com.example.avto.Service.Model.ResultResponse;
import com.example.avto.Service.Model.SimpleFilterModel;
import com.example.avto.Service.Model.Subscription;
import com.example.avto.Service.Model.SubscriptionResponse;
import com.google.android.gms.common.util.Strings;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GetDataService {

    @GET("/cars")
    Call<List<CarPost>> getAllCarPosts();

    @GET("/get-marks")
    Call<List<CarMark>> getAllCarMarks();

    @GET("/get-models/{markId}")
    Call<List<CarModel>> getAllCarModels(@Path("markId") Integer markId);

    @GET("/get-generations/{modelId}")
    Call<List<CarGeneration>> getAllCarGenerations(@Path("modelId") Integer modelId);

    @POST("/filter")
    Call<List<CarPost>> filter(@Body SimpleFilterModel body);

    @POST("/filter-count")
    Call<FilterCountResultModel> filterCount(@Body SimpleFilterModel body);

    @POST("/api/subscription/create")
    Call<SubscriptionResponse> createSubscription(@Header("Authorization") String token, @Body Subscription body);

    @GET("/api/subscription/show-all")
    Call<List<SubscriptionResponse>> getSubscriptions(@Header("Authorization") String token);

    @DELETE("/api/subscription/remove/{subscriptionId}")
    Call<ResultResponse> removeSubscription(@Header("Authorization") String token, @Path("subscriptionId") Integer subscriptionId);
}
