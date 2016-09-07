package com.it5.webview_ssl;

import android.annotation.TargetApi;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.it5.webview_ssl.activity_2.Okhttps;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.TlsVersion;

/**
 * Created by IT5 on 2016/9/7.
 */
public class MainActivity_3 extends AppCompatActivity {
    OkHttpClient client = null;
    Okhttps okhttps;
    private String URLS = "https://kyfw.12306.cn/otn/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        okhttps = new Okhttps();
        okhttps.setCertificates(getResources().openRawResource(R.raw.cert12306));
   /*     try {
            okhttps.AsyRun(URLS);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        WebView webView = new WebView(this);
        webView.setWebViewClient(myWebViewClient_1);
        setContentView(webView);
        webView.loadUrl(URLS);
    }

    private void setHttps() {
        ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_2)
                .cipherSuites(
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256)
                .build();

        client = new OkHttpClient.Builder()
                .connectionSpecs(Collections.singletonList(spec))
                .build();
    }

    private WebViewClient myWebViewClient_1 = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);

        }

        @Override
        public void onReceivedSslError(final WebView view, SslErrorHandler handler, final SslError error) {
//            super.onReceivedSslError(view, handler, error);
            try {
                okhttps.run(error.getUrl(), new ResultResponse() {
                    @Override
                    public void onResult(final Response resp) throws IOException {
                        final String html=resp.body().string();
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                view.loadData(html, "text/html; charset=UTF-8", null);
                            }
                        });
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };

    private WebViewClient myWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(final WebView view, String url) {
            return processRequest(Uri.parse(url));
        }

        @Override
        @TargetApi(21)
        public WebResourceResponse shouldInterceptRequest(final WebView view, WebResourceRequest interceptedRequest) {

            return processRequest(interceptedRequest.getUrl());
        }

        private WebResourceResponse processRequest(Uri uri) {
            Log.d("SSL_PINNING_WEBVIEWS", "GET: " + uri.toString());
            try {
                Response response = okhttps.run(uri.toString());
                // Get content, contentType and encoding
                InputStream is = response.body().byteStream();
                String contentType = response.body().contentType().toString();
                String encoding = response.body().contentType().charset().name();
                // If got a contentType header
                if (contentType != null) {
                    String mimeType = contentType;
                    // Parse mime type from contenttype string
                    if (contentType.contains(";")) {
                        mimeType = contentType.split(";")[0].trim();
                    }

                    Log.d("SSL_PINNING_WEBVIEWS", "Mime: " + mimeType);

                    // Return the response
                    return new WebResourceResponse(mimeType, encoding, is);
                }

            } catch (Exception e) {
                Log.d("SSL_PINNING_WEBVIEWS", e.getLocalizedMessage());
            }

            // Return empty response for this request
            return new WebResourceResponse(null, null, null);
        }

    };


}
