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

    // {
    //     "status": 200,
    //     "message": "Cutting Order Record retrieved successfully.",
    //     "data": {
    //         "cutting_order_records": [
    //             {
    //                 "id": 5895,
    //                 "serial_number": "COR-63544-00-SCBWMSMH95BODY-S258-01-003",
    //                 "laying_planning_detail_id": 7644,
    //                 "id_status_layer": 1,
    //                 "id_status_cut": 1,
    //                 "created_by": 1,
    //                 "is_pilot_run": 0,
    //                 "created_at": "2023-09-12T10:01:01.000000Z",
    //                 "updated_at": "2023-09-12T10:01:01.000000Z",
    //                 "status_layer": {
    //                     "id": 1,
    //                     "name": "not completed",
    //                     "created_at": "2023-06-15T03:37:59.000000Z",
    //                     "updated_at": "2023-06-15T03:37:59.000000Z"
    //                 },
    //                 "cutting_order_record_detail": []
    //             },
    //             {
    //                 "id": 5894,
    //                 "serial_number": "COR-63544-00-SCBWMSMH95BODY-S258-01-002",
    //                 "laying_planning_detail_id": 7643,
    //                 "id_status_layer": 1,
    //                 "id_status_cut": 1,
    //                 "created_by": 1,
    //                 "is_pilot_run": 0,
    //                 "created_at": "2023-09-12T09:59:06.000000Z",
    //                 "updated_at": "2023-09-12T09:59:06.000000Z",
    //                 "status_layer": {
    //                     "id": 1,
    //                     "name": "not completed",
    //                     "created_at": "2023-06-15T03:37:59.000000Z",
    //                     "updated_at": "2023-06-15T03:37:59.000000Z"
    //                 },
    //                 "cutting_order_record_detail": []
    //             }
    //         ],
    //         "current_page": 2,
    //                 "last_page": 523,
    //                 "prev_page_url": "http://192.168.45.32/cutting-ticket-app/public/api/cutting-orders?page=1",
    //                 "next_page_url": "http://192.168.45.32/cutting-ticket-app/public/api/cutting-orders?page=3",
    //                 "total": 5230
    //     }
    // }
    @GET("cutting-orders")
    // getCuttingOrder paginate
    Call<APIResponse> getCuttingOrder(@Header("Authorization") String authorization, @Path("page") int page);

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
    @POST("cutting-orders/search")
    Call<APIResponse> searchCuttingOrder(@Header("Authorization") String auth, @Field("serial_number") String serialNumber);

    @FormUrlEncoded
    @POST("laying-planning-show")
    Call<APIResponse> getLayingPlanningBySerialNumber(@Header("Authorization") String authorization, @Field("serial_number") String serialNumber);

    @GET("cutting-tickets/{id}")
    Call<APIResponse> getCuttingTicketDetail(@Header("Authorization") String authorization, @Path("id") int id);

}
