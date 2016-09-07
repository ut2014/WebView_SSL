/**
 * 
 */
package com.it5.webview_ssl.acitivity_1;

import android.content.Context;
import android.util.Log;

import com.it5.webview_ssl.R;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

/**
 * @author Administrator
 *
 */
public class AppConfig_1 {
    private final static String TAG = "AppConfig";

    public static final String SERVER_URL = "https://10.7.35.209:8443/sagframe/mobile/report_mobile!result.action?reportId=MOB_0001";

    public static X509Certificate[] mX509Certificates;

    public static PrivateKey mPrivateKey;

    public static String CERTFILE_PASSWORD = "123456";

    public static void initPrivateKeyAndX509Certificate(Context c) throws Exception {
        if (mPrivateKey != null && mX509Certificates != null) {
            return;
        }
//        KeyStore keyStore = KeyStore.getInstance("PKCS12", "BC");
        KeyStore keyStore = KeyStore.getInstance("BKS");
        Log.e(TAG, "initPrivateKeyAndX509Certificate : " + "keyStore initialized.");
        keyStore.load(c.getResources().openRawResource(R.raw.srcakey), CERTFILE_PASSWORD.toCharArray());
        Log.e(TAG, "initPrivateKeyAndX509Certificate : " + "keyStore loaded.");
        Enumeration<?> localEnumeration;
        localEnumeration = keyStore.aliases();
        while (localEnumeration.hasMoreElements()) {
            String str3 = (String) localEnumeration.nextElement();
            mPrivateKey = (PrivateKey) keyStore.getKey(str3, CERTFILE_PASSWORD.toCharArray());
            if (mPrivateKey == null) {
                continue;
            } else {
                Certificate[] arrayOfCertificate = keyStore.getCertificateChain(str3);
                mX509Certificates = new X509Certificate[arrayOfCertificate.length];
                for (int j = 0; j < mX509Certificates.length; j++) {
                    mX509Certificates[j] = ((X509Certificate) arrayOfCertificate[j]);
                }
            }
        }
        Log.e(TAG, "initPrivateKeyAndX509Certificate : " + "initialize complited.");
    }

    public static void initPrivateKeyAndX509Certificate(Context c, InputStream in) throws Exception {
        if (mPrivateKey != null && mX509Certificates != null) {
            return;
        }
        KeyStore keyStore = KeyStore.getInstance("PKCS12", "BC");
        keyStore.load(in, CERTFILE_PASSWORD.toCharArray());
        Enumeration<?> localEnumeration;
        localEnumeration = keyStore.aliases();
        while (localEnumeration.hasMoreElements()) {
            String str3 = (String) localEnumeration.nextElement();
            mPrivateKey = (PrivateKey) keyStore.getKey(str3, CERTFILE_PASSWORD.toCharArray());
            if (mPrivateKey == null) {
                continue;
            } else {
                Certificate[] arrayOfCertificate = keyStore.getCertificateChain(str3);
                mX509Certificates = new X509Certificate[arrayOfCertificate.length];
                for (int j = 0; j < mX509Certificates.length; j++) {
                    mX509Certificates[j] = ((X509Certificate) arrayOfCertificate[j]);
                }
            }
        }
    }
}
