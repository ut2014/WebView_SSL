package com.it5.webview_ssl;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by IT5 on 2016/9/7.
 */
public interface ResultResponse {
    void onResult(Response resp) throws IOException;
}
