package com.example.hong3.myapplication;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
/**
 * Created by jeongyeon on 2016-11-10.
 */

public class MyClient {
    private static final String BASE_URL = "http://127.0.0.1:3000";
   private static AsyncHttpClient client=new AsyncHttpClient();
    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }
    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

}
