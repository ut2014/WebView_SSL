package com.it5.webview_ssl.activity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/**
 * Created by IT5 on 2016/9/1.
 */
public class TestCert {
    public static void main(String[] args) {
        key_1();
    }





    private static void key_1() {
        try {
            FileInputStream fis = new FileInputStream("srca.cer");
            CertificateFactory cf = CertificateFactory.getInstance("X509");
            X509Certificate c = (X509Certificate) cf.generateCertificate(fis);
            System.out.println("Certficate for" + c.getSubjectDN());
            System.out.println("Generated with " + c.getSigAlgName());
            System.out.println("Generated with " + c.getPublicKey());
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (CertificateException ex) {
            /** @todo Handle this exception */
            ex.printStackTrace();
        }
    }
}
