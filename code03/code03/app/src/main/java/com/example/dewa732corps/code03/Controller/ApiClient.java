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


    // ==============================================================  USERNAME DAN PASSWORD
        @POST("mobileauthenticate")
    @FormUrlEncoded
    Call<ResponseBody> GetLogin(
            @Field("username")String username,
            @Field("password")String password);

    // ==============================================================  CHANGE PASSWORD
    @PATCH ("user/changepassword/{id}")
    @FormUrlEncoded
    Call<ResponseBody> editPassword(
           @Path("id") String id,
           @Field("newPassword")String newPassword

    );

    // ==============================================================  SPAREPART
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

    // ============================================================== SUPPLIER
    @GET("supplier")
    Call<SupplierList> getSupplier();

    @Multipart
    @POST("supplier")
    Call<ResponseBody> addSupplier(

            @Part("name_supplier") RequestBody name_supplier,
            @Part("address_supplier") RequestBody address_supplier,
            @Part("phone_number_supplier") RequestBody phone_number_supplier
    );

    @PATCH("supplier/{id_supplier}")
    @FormUrlEncoded
    Call<ResponseBody> editSupplier(
            @Path ("id_supplier") Integer id_supplier,
            @Field("name_supplier") String name_supplier,
            @Field("address_supplier") String address_supplier,
            @Field("phone_number_supplier") String phone_number_supplier
    );

    @DELETE("supplier/{id}")
    Call<ResponseBody> deleteSupplier(@Path("id") Integer id);

    // ==============================================================  SALES
    @GET("sales")
    Call<SalesList> getSales();

    @Multipart
    @POST("sales")
    Call<ResponseBody> addSales(
            @Part("id_supplier") RequestBody id_supplier,
            @Part("name_sales") RequestBody name_sales,
            @Part("phone_number_sales") RequestBody phone_number_sales
    );

    @PATCH("sales/{id_sales}")
    @FormUrlEncoded
    Call<ResponseBody> editSales(
            @Path("id_sales") Integer id_sales,
            @Field("id_supplier") String id_supplier,
            @Field("name_sales") String name_sales,
            @Field("phone_number_sales") String phone_number_sales
    );

    @DELETE("sales/{id}")
    Call<ResponseBody> deleteSales(@Path("id") Integer id);

    // ==============================================================  CUSTOMER
    @GET("customer")
    Call<CustomerList> getCustomer();

    @Multipart
    @POST("customer")
    Call<ResponseBody> addCustomer(

            @Part("name_customer") RequestBody name_customer,
            @Part("address_customer") RequestBody address_customer,
            @Part("phone_number_customer") RequestBody phone_number_customer
    );

    @PATCH("customer/{id_customer}")
    @FormUrlEncoded
    Call<ResponseBody> editCustomer(
            @Path ("id_customer") Integer id_customer,
            @Field("name_customer") String name_customer,
            @Field("address_customer") String address_customer,
            @Field("phone_number_customer") String phone_number_customer
    );

    @DELETE("customer/{id}")
    Call<ResponseBody> deleteCustomer(@Path("id") Integer id);

    // ==============================================================  MOTOR CUSTOMER
    @GET("motorcustomer")
    Call<MotorCustomerList> getMotorCustomer();

    @Multipart
    @POST("motorcustomer")
    Call<ResponseBody> addMotorCustomer(
            @Part("plate_number") RequestBody plate_number,
            @Part("id_motorcycle_type") RequestBody id_motorcycle_type,
            @Part("id_customer") RequestBody id_customer
    );

    @PATCH("motorcustomer/{id_motorcustomer}")
    @FormUrlEncoded
    Call<ResponseBody> editMotorCustomer(
            @Path ("id_motorcustomer") Integer id_motorcustomer,
            @Field("plate_number") String plate_number,
            @Field("id_motorcycle_type") String id_motorcycle_type,
            @Field("id_customer") String id_customer
    );

    @DELETE("motorcustomer/{id}")
    Call<ResponseBody> deleteMotorCustomer(@Path("id") Integer id);

    // ==============================================================  BRAND MOTOR
    @GET("motorbrand")
    Call<BrandMotorList> getBrandMotor();

    // ==============================================================  TYPE MOTOR
    @GET("motortype")
    Call<TypeMotorList> getTypeMotor();

    // ============================================================== PENGADAAN SPAREPART
    @GET("procurement")
    Call<SparepartProcurementList> getSparepartProcurement();

    @Multipart
    @POST("procurement")
    Call<ResponseBody> addSparepartProcurement(
            @Part("id_procurement") RequestBody id_procurement,
            @Part("date_procurement") RequestBody date_procurement,
            @Part("name_sales") RequestBody name_sales,
            @Part("name_supplier") RequestBody name_supplier,
            @Part("status_procurement") RequestBody status_procurement
    );

    @PATCH("procurement/{id_procurement}")
    @FormUrlEncoded
    Call<ResponseBody> editSparepartProcurement(
            @Part("id_procurement") RequestBody id_procurement,
            @Part("date_procurement") RequestBody date_procurement,
            @Part("name_sales") RequestBody name_sales,
            @Part("name_supplier") RequestBody name_supplier,
            @Part("status_procurement") RequestBody status_procurement
    );

    @DELETE("procurement/{id}")
    Call<ResponseBody> deleteSparepartProcurement(@Path("id") String id);
}