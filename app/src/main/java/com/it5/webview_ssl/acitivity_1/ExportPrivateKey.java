package com.it5.webview_ssl.acitivity_1;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;

/**
 * Created by IT5 on 2016/9/5.
 */
public class ExportPrivateKey {
    public InputStream keystoreInput;
    public String keyStoreType;
    public char[] password;
    public String alias;
    public File exportedFile;

    public static KeyPair getPrivateKey(KeyStore keystore, String alias, char[] password) {
        try {
            Key key=keystore.getKey(alias,password);
            if(key instanceof PrivateKey) {
                Certificate cert=keystore.getCertificate(alias);
                PublicKey publicKey=cert.getPublicKey();
                return new KeyPair(publicKey,(PrivateKey)key);
            }
        } catch (UnrecoverableKeyException e) {
        } catch (NoSuchAlgorithmException e) {
        } catch (KeyStoreException e) {
        }
        return null;
    }
    public void export() throws Exception{
        KeyStore keystore=KeyStore.getInstance(keyStoreType);
//        Base64 encoder=new Base64();
//        keystore.load(new FileInputStream(keystoreFile),password);
        keystore.load(keystoreInput,password);
        KeyPair keyPair=getPrivateKey(keystore,alias,password);
        PrivateKey privateKey=keyPair.getPrivate();
        String encoded=Base64.encode(privateKey.getEncoded());
        System.out.println(encoded);
        FileWriter fw=new FileWriter(exportedFile);
        fw.write("—–BEGIN PRIVATE KEY—–\n");
        fw.write(encoded);
        fw.write("\n");
        fw.write("—–END PRIVATE KEY—–");
        fw.close();
    }
    public static void main(String args[]) throws Exception{
        ExportPrivateKey export=new ExportPrivateKey();
//        export.keystoreFile=new File("/Users/Luke/Workspace/StringTest/src/com/lukejin/stringtest/keystore.jks");
        export.keyStoreType="JKS";
        export.password="changeit".toCharArray();
        export.alias="tom_server";
        export.exportedFile=new File("luke");
        export.export();
    }
}
