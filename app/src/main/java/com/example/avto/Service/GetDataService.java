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

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
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

    @POST("/subscription/create")
    Call<SubscriptionResponse> createSubscription(@Body Subscription body);

    @GET("/subscription/show-all")
    Call<List<SubscriptionResponse>> getSubscriptions();

    @DELETE("/subscription/remove/{subscriptionId}")
    Call<ResultResponse> removeSubscription(@Path("subscriptionId") Integer subscriptionId);
}
