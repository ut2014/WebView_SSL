package com.it5.webview_ssl.acitivity_1;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.it5.webview_ssl.R;

/**
 * Created by IT5 on 2016/9/2.
 */
public class MainActivity_1 extends Activity implements View.OnClickListener {
    private final String TAG = "MainActivity";
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_1);
        initView();

        ExportPrivateKey export = new ExportPrivateKey();
//        URI file= URI.create(getResources().getResourceName(R.raw.truststore));
        Uri uri = Uri.parse("android.resource://" + getPackageName() +":raw/"+R.raw.android);

//        System.out.print(uri);

        /*export.keystoreInput = getResources().openRawResource(R.raw.key);
        export.keyStoreType = "BKS";
        export.password = "123456".toCharArray();
        export.alias = "alias_name";
        export.exportedFile = new File("truststore");*/
        try {
            export.export();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        Button button = (Button) findViewById(R.id.main_load_btn);
        button.setOnClickListener(this);
        Button button1 = (Button) findViewById(R.id.main_cfg_btn);
        button1.setOnClickListener(this);
        mWebView = (WebView) this.findViewById(R.id.main_webview_view);
    }

    private void configWebView() {
        Log.e(TAG, "configWebView : " + "start config web view.");
        try {
            AppConfig_1.initPrivateKeyAndX509Certificate(this);
            Log.e(TAG, "private key : " + AppConfig_1.mPrivateKey);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "configWebView : " + e.getMessage());
        }
        WebViewClientSSL client = new WebViewClientSSL(this);
        mWebView.setWebViewClient(client);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_load_btn:
                mWebView.loadUrl(AppConfig_1.SERVER_URL);
                break;
            case R.id.main_cfg_btn:
                configWebView();
//                transform(null);
                break;
        }
    }

    private void transform(String str) {
        byte[] param = addZeroRightToMod8(str.getBytes());
        Log.e(TAG, "param length = " + param);
    }

    /**
     * 功能描述：在一个byte[]数组的末尾补零，直至该byte[]数组的长度是8的整数倍
     *
     * @param data
     * @return
     */
    public static byte[] addZeroRightToMod8(byte[] data) {
        byte[] addObject = new byte[]{0};
        if (data.length % 8 != 0) {
            while (data.length % 8 != 0) {
                data = copyarray(data, addObject);
            }
            return data;
        } else {
            return data;
        }
    }

    /**
     * 合并byte数组
     *
     * @param data1
     * @param data2
     * @return
     */
    public static byte[] copyarray(byte[] data1, byte[] data2) {
        byte[] data3 = new byte[data1.length + data2.length];
        System.arraycopy(data1, 0, data3, 0, data1.length);
        System.arraycopy(data2, 0, data3, data1.length, data2.length);

        return data3;
    }
}
