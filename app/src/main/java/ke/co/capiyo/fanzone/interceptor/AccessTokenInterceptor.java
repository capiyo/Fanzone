package ke.co.capiyo.fanzone.interceptor;

import android.util.Base64;

import androidx.annotation.NonNull;

import ke.co.capiyo.fanzone.util.Constants;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

;

public class AccessTokenInterceptor implements Interceptor {
    private final String CONSUMER_KEY;
    private final String CONSUMER_SECRET;

    public AccessTokenInterceptor() {
        this.CONSUMER_KEY = Constants.CONSUMER_KEY;
        this.CONSUMER_SECRET = Constants.CONSUMER_SECRET;

    }

    public AccessTokenInterceptor(String sandboxConsumerKey, String sandboxConsumerSecret) {
        this.CONSUMER_KEY = sandboxConsumerKey;
        this.CONSUMER_SECRET = sandboxConsumerSecret;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        String keys = String.format("%s:%s", this.CONSUMER_KEY, this.CONSUMER_SECRET);

        Request request = chain.request().newBuilder()
                .addHeader("Authorization", "Basic " + Base64.encodeToString(keys.getBytes(), Base64.NO_WRAP))
//                .addHeader("Authorization", "Basic " + Base64.encodeToString(keys.getBytes(), Base64.NO_WRAP))
                .build();
        return chain.proceed(request);
    }
}