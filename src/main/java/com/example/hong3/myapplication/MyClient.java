package com.example.hong3.myapplication;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
/**
 * Created by jeongyeon on 2016-11-10.
 */


public class MyClient {
    private static final String BASE_URL = "http://192.168.1.101:1039";//"http://202.30.23.51:1039";
   private static AsyncHttpClient client=new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }
    public static void post(String url,RequestParams params,AsyncHttpResponseHandler responseHandler){

        client.post(getAbsoluteUrl(url),params,responseHandler);

    }
    public static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }


}
