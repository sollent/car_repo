package com.example.avto.Service;

import com.example.avto.Service.Model.LoginUser;
import com.example.avto.Service.Model.TokenModel;
import com.example.avto.Service.Model.UserModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AuthService {

    @POST("/api/login")
    Call<TokenModel> login(@Body LoginUser loginUser);

    @GET("/api/get-user")
    Call<UserModel> getUser(@Header("Authorization") String token);
}
