package ke.co.capiyo.fanzone.Services;

import ke.co.capiyo.fanzone.Model.AccessToken;
import ke.co.capiyo.fanzone.Model.BulkPaymentResponse;
import ke.co.capiyo.fanzone.Model.STKPushResponse;
import ke.co.capiyo.fanzone.Model.BulkPaymentRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface BulkPaymentService {

    @Headers("Content-Type: application/json")
    @POST("mpesa/b2c/v3/paymentrequest")
    Call<STKPushResponse> initiatePayment(@Body BulkPaymentRequest stkPush);

    @Headers("Content-Type: application/json")
    @POST("mpesa/b2c/v3/paymentrequest")
    Call<BulkPaymentResponse> makeB2CPayment(@Body BulkPaymentRequest b2CPaymentRequest);

    @Headers("Content-Type: application/json")
    @GET("oauth/v1/generate?grant_type=client_credentials")
    Call<AccessToken> getTestAccessToken();

}
