package com.it5.webview_ssl.activity_2;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.it5.webview_ssl.R;

import java.io.IOException;

/**
 * Created by IT5 on 2016/9/7.
 */
public class MainActivity_2 extends AppCompatActivity {

    private WebView webView;
    public static Switch pinningSwitch;
    private Button btnA;
    private Button btnB;
    public static TextView textView;

    private String url1 = "your https url";
    private String url2 = "your https url";

    public MainActivity_2() {
    }
    public static Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);

        webView = (WebView)findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);

        pinningSwitch = (Switch)findViewById(R.id.pinningSwitch);
        btnA = (Button)findViewById(R.id.btn1);
        btnB = (Button)findViewById(R.id.btn2);
        textView = (TextView)findViewById(R.id.textView);

        mContext=this;
        SslPinningWebViewClient webViewClient = null;
        try {
            webViewClient = new SslPinningWebViewClient(new LoadedListener() {
                @Override
                public void Loaded(final String url) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText("Loaded " + url);
                        }
                    });
                }

                @Override
                public void PinningPreventedLoading(final String host) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText("SSL Pinning prevented loading from " + host);
                        }
                    });
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        webView.setWebViewClient(webViewClient);

        btnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.clearView();
                textView.setText("");
                webView.loadUrl(url1);
            }
        });

        btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.clearView();
                textView.setText("");
                webView.loadUrl(url2);
            }
        });
    }
}