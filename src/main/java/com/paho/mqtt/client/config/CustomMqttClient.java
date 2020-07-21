package com.paho.mqtt.client.config;

import com.paho.mqtt.client.utils.SslUtil;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

/**
 * custom mqtt client
 *
 * @author wjl
 * @date 2020-07-20
 */
public class CustomMqttClient {

    private MqttParam mqttParam;

    private MqttClient mqttClient;

    private MqttConnectOptions options;

    private String[] initTopics;

    public CustomMqttClient(MqttParam mqttParam) {
        this.mqttParam = mqttParam;
        initMqttConnectOptions();
        initClient();
    }

    private void initClient() {
        try {
            mqttClient = new MqttClient(mqttParam.getUrl(), mqttParam.getClientId(), new MemoryPersistence());
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void initMqttConnectOptions() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setMqttVersion(mqttParam.getMqttVersion());
        options.setCleanSession(mqttParam.isCleanSession());
        options.setUserName(mqttParam.getUserName());
        options.setPassword(mqttParam.getPassword());
        options.setConnectionTimeout(mqttParam.getConnectionTimeout());
        options.setKeepAliveInterval(mqttParam.getKeepAliveInterval());
        String sslFilePath = mqttParam.getSslFilePath();
        // the property is default true
        options.setHttpsHostnameVerificationEnabled(false);
        if (sslFilePath != null && !sslFilePath.isEmpty()) {
            try {
                options.setSocketFactory(SslUtil.getSocketFactory(sslFilePath));
            } catch (IOException
                    | KeyStoreException
                    | NoSuchAlgorithmException
                    | CertificateException
                    | KeyManagementException e) {
                e.printStackTrace();
            }
        }
        this.options = options;
    }

    public void connect(MqttCallback mqttCallback, String... initSubTopic) {
        if (null == mqttCallback) {
            System.out.println("mqtt has not set callback");
            return;
        }
        try {
            mqttClient.setCallback(mqttCallback);
            mqttClient.connect(options);
            if (null != initSubTopic && 0 < initSubTopic.length) {
                this.initTopics = initSubTopic;
                mqttClient.subscribe(initSubTopic);
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public boolean reConnect() {
        if (null == this.mqttClient) {
            System.out.println("mqtt client is null");
            return false;
        }
        if(!this.mqttClient.isConnected()) {
            try {
                this.mqttClient.connect(this.options);
                System.out.println("reconnect error");
            } catch (MqttException e) {
                e.printStackTrace();
                return false;
            }
        }
        try {
            this.mqttClient.subscribe(initTopics);
            return true;
        } catch (MqttException e) {
            e.printStackTrace();
            System.out.println("subscribe error");
            return false;
        }
    }
}
