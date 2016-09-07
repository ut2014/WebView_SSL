package com.it5.webview_ssl.activity;

import android.content.Context;
import android.view.ViewGroup;
import android.webkit.WebView;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

/**
 * Created by IT5 on 2016/8/31.
 */
public class AppConfig {
    private static WebView mWebView;

    public static X509Certificate[] mX509Certificates;

    public static PrivateKey mPrivateKey;

    public static String CERTFILE_PASSWORD="pw123456";

    private static void clearParent()
    {
        if (mWebView != null)
        {
            ViewGroup p = (ViewGroup) mWebView.getParent();
            if (p != null)
            {
                p.removeAllViewsInLayout();
            }
        }
    }

    public static void initPrivateKeyAndX509Certificate(Context c)
            throws Exception
    {
        if (mPrivateKey != null && mX509Certificates != null)
        {
            return;
        }
//        KeyStore keyStore = KeyStore.getInstance("PKCS12", "BC");
        KeyStore keyStore = KeyStore.getInstance("BKS");
//        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
//        InputStream is=c.getResources().openRawResource(R.raw.cert12306);
        InputStream is=c.getAssets().open("srca.bks");
        char[] chars=CERTFILE_PASSWORD.toCharArray();
        keyStore.load(is,chars);
        Enumeration<?> localEnumeration;
        localEnumeration = keyStore.aliases();
        while (localEnumeration.hasMoreElements())
        {
            String str3 = (String) localEnumeration.nextElement();
            mPrivateKey =
                    (PrivateKey) keyStore.getKey(str3,
                            CERTFILE_PASSWORD.toCharArray());
            if (mPrivateKey == null)
            {
                continue;
            }
            else
            {
                Certificate[] arrayOfCertificate =
                        keyStore.getCertificateChain(str3);
                mX509Certificates =
                        new X509Certificate[arrayOfCertificate.length];
                for (int j = 0; j < mX509Certificates.length; j++)
                {
                    mX509Certificates[j] =
                            ((X509Certificate) arrayOfCertificate[j]);
                }
            }
        }
    }

    public static void initPrivateKeyAndX509Certificate(Context c,
                                                        InputStream in) throws Exception
    {
        if (mPrivateKey != null && mX509Certificates != null)
        {
            return;
        }
        KeyStore keyStore = KeyStore.getInstance("PKCS12", "BC");
        keyStore.load(in, CERTFILE_PASSWORD.toCharArray());
        Enumeration<?> localEnumeration;
        localEnumeration = keyStore.aliases();
        while (localEnumeration.hasMoreElements())
        {
            String str3 = (String) localEnumeration.nextElement();
            mPrivateKey =
                    (PrivateKey) keyStore.getKey(str3,
                            CERTFILE_PASSWORD.toCharArray());
            if (mPrivateKey == null)
            {
                continue;
            }
            else
            {
                Certificate[] arrayOfCertificate =
                        keyStore.getCertificateChain(str3);
                mX509Certificates =
                        new X509Certificate[arrayOfCertificate.length];
                for (int j = 0; j < mX509Certificates.length; j++)
                {
                    mX509Certificates[j] =
                            ((X509Certificate) arrayOfCertificate[j]);
                }
            }
        }
    }}