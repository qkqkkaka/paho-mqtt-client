package com.paho.mqtt.client.utils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMReader;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * ssl util
 *
 * @author wjl
 * @date 2020-07-20
 */
public class SslUtil {

    public static SSLSocketFactory getSocketFactory(String filePath) throws IOException, NoSuchAlgorithmException
            , KeyStoreException, CertificateException, KeyManagementException {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            throw new SecurityException("file is not exist or readable");
        }

        byte[] caCrtFile = Files.readAllBytes(Paths.get(filePath));
        Security.addProvider(new BouncyCastleProvider());

        //===========加载 ca 证书==================================
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        // 加载本地指定的 ca 证书
        PEMReader reader = new PEMReader(new InputStreamReader(new ByteArrayInputStream(caCrtFile)));
        X509Certificate caCert = (X509Certificate) reader.readObject();
        reader.close();

        // CA certificate is used to authenticate server
        KeyStore caKs = KeyStore.getInstance(KeyStore.getDefaultType());
        caKs.load(null, null);
        caKs.setCertificateEntry("ca-certificate", caCert);
        // 把ca作为信任的 ca 列表,来验证服务器证书
        tmf.init(caKs);

        // ============finally, create SSL socket factory==============
        SSLContext context = SSLContext.getInstance("TLSv1.2");
        context.init(null, tmf.getTrustManagers(), null);

        return context.getSocketFactory();
    }
}
