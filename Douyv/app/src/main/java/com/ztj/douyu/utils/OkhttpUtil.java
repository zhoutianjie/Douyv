package com.ztj.douyu.utils;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * okhttp工具类
 * Created by zhoutianjie on 2018/6/28.
 */

public class OkhttpUtil {

    private OkHttpClient client;

    public OkhttpUtil() {
        client = new OkHttpClient();
    }

    private static class InnerSingleTon{
        private static final OkhttpUtil Instance = new OkhttpUtil();
    }

    public static OkhttpUtil getInstance(){
        return InnerSingleTon.Instance;
    }

    /**
     * 同步请求
     * @param url
     * @return
     */
    public Response getSyncResponse(String url){
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 异步请求
     */
    public void getAsyncResponse(String url, Callback callback){
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(callback);
    }

}
