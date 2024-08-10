package ke.co.capiyo.fanzone.Services;

import ke.co.capiyo.fanzone.Model.AccessToken;
import ke.co.capiyo.fanzone.Model.STKPush;
import ke.co.capiyo.fanzone.Model.STKPushResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface STKPushService {
    @Headers("Content-Type: application/json")
    @POST("mpesa/stkpush/v1/processrequest")
    Call<STKPushResponse> sendPush(@Body STKPush stkPush);

    @Headers("Content-Type: application/json")
    @GET("oauth/v1/generate?grant_type=client_credentials")
    Call<AccessToken> getAccessToken();
}