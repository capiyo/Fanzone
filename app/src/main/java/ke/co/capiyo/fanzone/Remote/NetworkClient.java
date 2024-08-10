package ke.co.capiyo.fanzone.Remote;


import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import ke.co.capiyo.fanzone.interceptor.AccessTokenInterceptor;
import ke.co.capiyo.fanzone.interceptor.AuthInterceptor;
import ke.co.capiyo.fanzone.util.CommonUtils;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient {
    private static Retrofit retrofit = null;
    private static OkHttpClient okHttpClient = null;
    private static final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();

    public static Retrofit getClient(String baseUrl, OkHttpClient client) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }

    public static OkHttpClient getPaymentOkhttpClient(String authToken) {
        try {
            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(CommonUtils.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(CommonUtils.INPUT_TIMEOUT, TimeUnit.SECONDS)
                    .sslSocketFactory(new TLSSocketFactory())
                    .readTimeout(CommonUtils.FETCH_TIMEOUT, TimeUnit.SECONDS)
                    .addInterceptor(httpLoggingInterceptor)
                    .addInterceptor(new AuthInterceptor(authToken))
                    .build();
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return okHttpClient;
    }

    public static OkHttpClient getAuthenticationOkhttpClient(String CONSUMER_KEY, String CONSUMER_SECRET) {
        try {
            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(CommonUtils.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(CommonUtils.INPUT_TIMEOUT, TimeUnit.SECONDS)
                    .sslSocketFactory(new TLSSocketFactory())
                    .readTimeout(CommonUtils.FETCH_TIMEOUT, TimeUnit.SECONDS)
                    .addInterceptor(httpLoggingInterceptor)
                    .addInterceptor(new AccessTokenInterceptor())
                    .build();
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return okHttpClient;
    }
}