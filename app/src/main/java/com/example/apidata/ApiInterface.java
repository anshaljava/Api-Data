package com.example.apidata;



import retrofit2.http.GET;
import retrofit2.Call;

public interface ApiInterface {
    @GET("users")
    Call<Pojo>getData();
}
