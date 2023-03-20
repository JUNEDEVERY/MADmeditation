package com.example.designmobileproject;

import com.example.designmobileproject.Masks.MaskSend;
import com.example.designmobileproject.Masks.MaskUser;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitApi {

    @POST("user/login")
    Call<MaskUser> User(@Body MaskSend mSend);
}
