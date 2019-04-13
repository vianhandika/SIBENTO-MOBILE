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
import retrofit2.http.PATCH;
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

    @PATCH("sparepart/{id_sparepart}")
    @FormUrlEncoded
    Call<ResponseBody> editSparepart(
            @Path("id_sparepart") String id_sparepart,
            @Field("name_sparepart") String name_sparepart,
            @Field("brand_sparepart") String brand_sparepart,
            @Field("stock_sparepart") Integer stock_sparepart,
            @Field("minimal_stock_sparepart") Integer minimal_stock_sparepart,
            @Field("buy_price") Integer buy_price,
            @Field("sell_price") Integer sell_price,
            @Field("placement") String placement,
            @Field("id_sparepart_type") Integer id_sparepart_type

    );

    @Multipart
    @POST("sparepart/updateimage")
    Call<ResponseBody> editImageSparepart(
            @Part MultipartBody.Part image_sparepart,
            @Part("id_sparepart") RequestBody id_sparepart

    );


    @DELETE("sparepart/{id}")
    Call<ResponseBody> deleteSparepart(@Path("id") String id);

    @GET("spareparttype")
    Call<SparepartTypeList> getSparepartType();

    // SUPPLIER

    @GET("supplier")
    Call<SupplierList> getSupplier();

    @DELETE("supplier/{id}")
    Call<ResponseBody> deleteSupplier(@Path("id") Integer id);

    @Multipart
    @POST("supplier")
    Call<ResponseBody> addSupplier(
                    @Part("name_supplier") RequestBody name_supplier,
                    @Part("address_supplier") RequestBody address_supplier,
                    @Part("phone_number_supplier") RequestBody phone_number_supplier
            );
}
