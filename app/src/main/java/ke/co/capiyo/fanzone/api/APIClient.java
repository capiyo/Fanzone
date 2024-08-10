package ke.co.capiyo.fanzone.api;

import java.util.concurrent.TimeUnit;

import ke.co.capiyo.fanzone.Services.BulkPaymentService;
import ke.co.capiyo.fanzone.Services.STKPushService;
import ke.co.capiyo.fanzone.interceptor.AccessTokenInterceptor;
import ke.co.capiyo.fanzone.interceptor.AuthInterceptor;

import ke.co.capiyo.fanzone.util.Constants;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private Retrofit retrofit;
    private boolean isDebug;
    private boolean isGetAccessToken;
    private String mAuthToken;
    public final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();

    public APIClient setIsDebug(boolean isDebug) {
        this.isDebug = isDebug;
        return this;
    }

    public APIClient setAuthToken(String authToken) {
        mAuthToken = authToken;
        return this;
    }

    public APIClient setGetAccessToken(boolean getAccessToken) {
        isGetAccessToken = getAccessToken;
        return this;
    }

    private OkHttpClient.Builder okHttpClient() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient
                .connectTimeout(Constants.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Constants.WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Constants.READ_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor);

        return okHttpClient;
    }

    private Retrofit getRestAdapter() {

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(Constants.BASE_URL);
        builder.addConverterFactory(GsonConverterFactory.create());

        if (isDebug) {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }

        OkHttpClient.Builder okhttpBuilder = okHttpClient();

        if (isGetAccessToken) {
            okhttpBuilder.addInterceptor(new AccessTokenInterceptor());
        }

        if (mAuthToken != null && !mAuthToken.isEmpty()) {
            okhttpBuilder.addInterceptor(new AuthInterceptor(mAuthToken));
        }

        builder.client(okhttpBuilder.build());

        retrofit = builder.build();

        return retrofit;
    }
    public STKPushService mpesaSTKService() {
        return getRestAdapter().create(STKPushService.class);
    }

    public BulkPaymentService mpesaB2CService() {
        return getTestRestAdapter().create(BulkPaymentService.class);
    }

    private Retrofit getTestRestAdapter() {

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(Constants.BASE_URL);
        builder.addConverterFactory(GsonConverterFactory.create());

        if (isDebug) {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }

        OkHttpClient.Builder okhttpBuilder = okHttpClient();

        if (isGetAccessToken) {
            okhttpBuilder.addInterceptor(new AccessTokenInterceptor(Constants.SANDBOX_CONSUMER_KEY, Constants.SANDBOX_CONSUMER_SECRET));
        }

        if (mAuthToken != null && !mAuthToken.isEmpty()) {
            okhttpBuilder.addInterceptor(new AuthInterceptor(mAuthToken));
        }

        builder.client(okhttpBuilder.build());

        retrofit = builder.build();

        return retrofit;
    }

}
