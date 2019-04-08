package com.example.dewa732corps.code03.Controller;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiClient {
    @POST("mobileauthenticate")
    @FormUrlEncoded
    Call<ResponseBody> GetLogin(
            @Field("username")String username,
            @Field("password")String password);

    @GET("sparepart")
    Call<SparepartList> getSparepart();

    @Multipart
    @POST("sparepart/updateimage")
    Call<ResponseBody> updateImageSparepart(
            @Part MultipartBody.Part image_sparepart,
            @Part("id_sparepart") RequestBody id_sparepart
    );

    @Multipart
    @POST("sparepart")
    Call<ResponseBody> addSparepart(
            @Part MultipartBody.Part image_sparepart,
            @Part("id_sparepart") RequestBody id_sparepart,
            @Part("name_sparepart") RequestBody name_sparepart,
            @Part("brand_sparepart") RequestBody brand_sparepart,
            @Part("stock_sparepart") RequestBody stock_sparepart,
            @Part("minimal_stock_sparepart") RequestBody minimal_stock_sparepart,
            @Part("buy_price") RequestBody buy_price,
            @Part("sell_price") RequestBody sell_price,
            @Part("placement") RequestBody placement,
            @Part("id_sparepart_type") RequestBody id_sparepart_type

    );


    @DELETE("sparepart/{id}")
    Call<ResponseBody> deleteSparepart(@Path("id") String id);

    @GET("spareparttype")
    Call<SparepartTypeList> getSparepartType();


}
