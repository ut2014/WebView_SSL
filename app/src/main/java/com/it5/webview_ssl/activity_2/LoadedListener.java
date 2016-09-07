package com.it5.webview_ssl.activity_2;

/**
 * Created by IT5 on 2016/9/7.
 */
public interface LoadedListener {
    void Loaded(String url);
    void PinningPreventedLoading(String host);
}
