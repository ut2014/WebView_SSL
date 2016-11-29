package com.it5.webview_ssl;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;

import static com.it5.webview_ssl.R.id.webView;

public class MainActivity_confim_alert extends AppCompatActivity {
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confim_alert);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mWebView = (android.webkit.WebView) findViewById(webView);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initwebview();
    }

    private void initwebview() {
        if (mWebView == null) {
            return;
        }
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.setWebViewClient(client);
        mWebView.setWebChromeClient(mChromeClient);
        mWebView.loadUrl("file:///android_asset/MyHtml.html");

    }

    private WebViewClient client = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return super.shouldOverrideUrlLoading(view, url);
        }
    };

    private WebChromeClient mChromeClient = new WebChromeClient() {
        @Override
        // 处理javascript中的alert
        public boolean onJsAlert(WebView view, String url, String message,
                                 final JsResult result) {
            // 构建一个Builder来显示网页中的对话框
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity_confim_alert.this);
            builder.setTitle("提示对话框");
            builder.setMessage(message);
            builder.setPositiveButton(android.R.string.ok,
                    new AlertDialog.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            // TODO Auto-generated method stub
                            // 点击确定按钮之后,继续执行网页中的操作
                            result.confirm();
                        }
                    });
            builder.setNegativeButton(android.R.string.cancel,
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            result.cancel();

                        }
                    });
            builder.setCancelable(false);
            builder.create();
            builder.show();

            return true;

        }

        @Override
        //处理javascript中的confirm
        public boolean onJsConfirm(WebView view, String url,
                                   String message, final JsResult result) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity_confim_alert.this);
            builder.setTitle("带选择的对话框");
            builder.setMessage(message);
            builder.setPositiveButton(android.R.string.ok,
                    new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            result.confirm();
                        }
                    });
            builder.setNegativeButton(android.R.string.cancel,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            result.cancel();
                        }
                    });
            builder.setCancelable(false);
            builder.create();
            builder.show();
            return true;
        }

        @Override
        // 处理javascript中的prompt
        // message为网页中对话框的提示内容
        // defaultValue在没有输入时，默认显示的内容
        public boolean onJsPrompt(WebView view, String url, String message,
                                  String defaultValue, final JsPromptResult result) {
            // 自定义一个带输入的对话框由TextView和EditText构成
            LayoutInflater layoutInflater = LayoutInflater
                    .from(MainActivity_confim_alert.this);
            final View dialogView = layoutInflater.inflate(
                    R.layout.content_activity_confim_alert, null);

            // 设置TextView对应网页中的提示信息
            ((TextView) dialogView.findViewById(R.id.TextView_PROM))
                    .setText(message);
            // 设置EditText对应网页中的输入框
            ((EditText) dialogView.findViewById(R.id.EditText_PROM))
                    .setText(defaultValue);
            //构建一个Builder来显示网页中的对话框
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity_confim_alert.this);
            //设置弹出框标题
            builder.setTitle("带输入的对话框");
            //设置弹出框的布局
            builder.setView(dialogView);
            //设置按键的监听
            builder.setPositiveButton(android.R.string.ok,
                    new AlertDialog.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog,
                                            int which) {

                            // 点击确定之后，取得输入的值，传给网页处理
                            String value = ((EditText) dialogView
                                    .findViewById(R.id.EditText_PROM))
                                    .getText().toString();
                            result.confirm(value);
                        }

                    });

            builder.setNegativeButton(android.R.string.cancel,
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            // TODO Auto-generated method stub
                            result.cancel();
                        }
                    });

            builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface dialog) {
                    result.cancel();
                }
            });
            builder.show();
            return true;
        }

        @Override
        //设置网页加载的进度条
        public void onProgressChanged(WebView view, int newProgress) {
            MainActivity_confim_alert.this.getWindow().setFeatureInt(Window.FEATURE_PROGRESS, newProgress * 100);
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            MainActivity_confim_alert.this.setTitle(title);
            super.onReceivedTitle(view, title);
        }

    };

}
