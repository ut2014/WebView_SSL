package com.it5.webview_ssl.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.ClientCertRequest;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.Call;

public class MainActivity extends AppCompatActivity {

    private String URLS = "https://kyfw.12306.cn/otn/";
    private String URL = "http://soso.com";

    private String MURLS = "https://192.168.3.128:8443/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView wb = new WebView(this);
        setContentView(wb);
        initAppConfig();
        wb.setWebViewClient(myClient);
        wb.loadUrl(MURLS);
//        key(this);
//        getHtml(URLS);

        
    }

    private void prikey() {
        InputStream bksFile = null;
        String password = null;
        try {
            KeyStore e = KeyStore.getInstance("BKS");

            e.load(bksFile, password.toCharArray());
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(e, password.toCharArray());
//        return keyManagerFactory.getKeyManagers();
            keyManagerFactory.getKeyManagers();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    private void getHtml(String url) {
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int i) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(String s, int i) {
                        System.out.println(s);
                    }
                });
    }

    private void initAppConfig() {
        try {
            AppConfig.initPrivateKeyAndX509Certificate(this);
            System.out.println("key" + AppConfig.mPrivateKey+"");
            System.out.println("Certificates" + AppConfig.mX509Certificates+ "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void key(Context c) {
        char[] password = "pw123456".toCharArray();
        try {
//            FileInputStream fis = new FileInputStream("cert.keystore");
//            InputStream fis=c.getResources().openRawResource(R.raw.cert);
            InputStream fis = c.getAssets().open("srca.bks");
            KeyStore ks = KeyStore.getInstance("BKS");

            ks.load(fis, password);
/*            KeyStore.PrivateKeyEntry pkEntry =(KeyStore.PrivateKeyEntry)ks.getEntry(
                    "mykey",
                    new KeyStore.PasswordProtection(password));*/
//            PrivateKey key = (PrivateKey) ks.getKey("mykey", password);
//            System.out.println(pkEntry.toString());
//            System.out.println(key.toString());

            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(ks, password);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.
                    getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(ks);
            sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private WebViewClient myClient = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//            super.onReceivedSslError(view, handler, error);
            Log.e("SslError", error.toString());
            handler.proceed();

        }

        @TargetApi(21)
        @Override
        public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
            Log.e("CertRequest", request.toString());
            if ((null != AppConfig.mPrivateKey)
                    && ((null != AppConfig.mX509Certificates) && (AppConfig.mX509Certificates.length != 0))) {
                request.proceed(AppConfig.mPrivateKey, AppConfig.mX509Certificates);
            } else {
                request.cancel();
            }
            super.onReceivedClientCertRequest(view, request);
        }


        private void getkey() {
            try {
                FileInputStream fis = new FileInputStream("cert12306.crt");
                CertificateFactory cf = CertificateFactory.getInstance("X509");
                X509Certificate c = (X509Certificate) cf.generateCertificate(fis);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (CertificateException ex) {
                /** @todo Handle this exception */
                ex.printStackTrace();
            }
        }
    };


}
