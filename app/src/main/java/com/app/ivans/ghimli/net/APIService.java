package com.app.ivans.ghimli.net;

import com.app.ivans.ghimli.model.APIResponse;
import com.app.ivans.ghimli.model.CuttingOrderRecordDetail;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {
    @GET("users")
    Call<APIResponse> getUsers(@Header("Authorization") String authorization);

    @FormUrlEncoded
    @POST("login")
    Call<APIResponse> userSignIn(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("register")
    Call<APIResponse> userSignUp(@Field("name") String name, @Field("email") String email, @Field("password") String password, @Field("confirm_password") String confirmPassword);

    @GET("colors")
    Call<APIResponse> getColor(@Header("Authorization") String authorization);

    @GET("cutting-orders")
    Call<APIResponse> getCuttingOrder(@Header("Authorization") String authorization);

    @POST("cutting-orders") Call<APIResponse> createOptCuttingOrderObj(@Header("Authorization") String authorization, @Body CuttingOrderRecordDetail cuttingOrderRecordDetail);

    @FormUrlEncoded
    @POST("cutting-orders")
    Call<APIResponse> createOptCuttingOrder(
            @Header("Authorization") String authorization,
            @Field("serial_number") String serialNumber,
            @Field("fabric_roll") String fabricRoll,
            @Field("fabric_batch") String fabricBatch,
            @Field("color") String color,
            @Field("yardage") double yardage,
            @Field("weight") double weight,
            @Field("layer") int layer,
            @Field("joint") String joint,
            @Field("balance_end") String balanceEnd,
            @Field("remarks") String remarks,
            @Field("operator") String operator);

    @GET("cutting-orders/{serial_number}")
    Call<APIResponse> getCuttingOrderBySerialNumber(@Header("Authorization") String authorization, @Path("serial_number") String serialNumber);

    @GET("cutting-record-remark")
    Call<APIResponse> getRemarks(@Header("Authorization") String auth);

    @FormUrlEncoded
    @POST("cutting-orders/status-cut")
    Call<APIResponse> postStatusCut(@Header("Authorization") String auth, @Field("serial_number") String serialNumber, @Field("name") String status);

    @FormUrlEncoded
    @POST("laying-planning-show")
    Call<APIResponse> getLayingPlanningBySerialNumber(@Header("Authorization") String authorization, @Field("serial_number") String serialNumber);

    @GET("cutting-tickets/{id}")
    Call<APIResponse> getCuttingTicketDetail(@Header("Authorization") String authorization, @Path("id") int id);

}
