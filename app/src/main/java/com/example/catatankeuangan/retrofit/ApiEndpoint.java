package com.example.catatankeuangan.retrofit;

import com.example.catatankeuangan.model.CategoryResponse;
import com.example.catatankeuangan.model.LoginResponse;
import com.example.catatankeuangan.model.SubmitResponse;
import com.example.catatankeuangan.model.TransactionRequest;
import com.example.catatankeuangan.model.TransactionResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ApiEndpoint {

    //{{url}}/api/list-transaction.php

    @GET("api/list-transaction.php")
    Call<TransactionResponse> listTransaction(
            @Query("user_id") Integer user_id
    );


    @FormUrlEncoded
    @POST("api/login.php")
    Call<LoginResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("api/register.php")
    Call<SubmitResponse> register(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password
    );

    @GET("api/list-category.php")
    Call<CategoryResponse> listCategory();

    @FormUrlEncoded
    @POST("api/transaction.php")
    Call<SubmitResponse> transaction(
            @Field("category_id") Integer category_id,
            @Field("user_id") Integer user_id,
            @Field("description") String description,
            @Field("amount") Integer amount,
            @Field("type") String type


    );

    @GET("api/transaction-delete.php")
    Call<SubmitResponse> deleteTransaction(
            @Query("id") Integer id
    );

    @PUT("api/transaction-edit.php")
    Call<SubmitResponse> editTransaction(
            @Body TransactionRequest transactionRequest
            );
}
