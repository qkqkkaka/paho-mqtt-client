package com.paho.mqtt.client.config;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

/**
 * mqtt param
 *
 * @author wjl
 * @date 2020-07-20
 */
public class MqttParam {

    private String url;

    private String clientId;

    private String userName;

    private char[] password;

    private String sslFilePath;

    private int connectionTimeout;

    private int keepAliveInterval;

    private int mqttVersion;

    private boolean cleanSession;

    private MqttParam () {}

    private MqttParam(Builder builder) {
        this.url = builder.url;
        this.clientId = builder.clientId;
        this.userName = builder.userName;
        this.password = builder.password;
        this.sslFilePath = builder.sslFilePath;
        this.connectionTimeout = builder.connectionTimeout;
        this.keepAliveInterval = builder.keepAliveInterval;
        this.mqttVersion = builder.mqttVersion;
        this.cleanSession = builder.cleanSession;
    }

    public String getUrl() {
        return url;
    }

    public String getClientId() {
        return clientId;
    }

    public String getUserName() {
        return userName;
    }

    public char[] getPassword() {
        return password;
    }

    public String getSslFilePath() {
        return sslFilePath;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public int getKeepAliveInterval() {
        return keepAliveInterval;
    }

    public int getMqttVersion() {
        return mqttVersion;
    }

    public boolean isCleanSession() {
        return cleanSession;
    }

    public static class Builder {

        private String url = "";

        private String clientId = "client";

        private String userName = "userName";

        private char[] password = "password".toCharArray();

        private String sslFilePath = null;

        private int connectionTimeout = 20;

        private int keepAliveInterval = 30;

        private int mqttVersion = MqttConnectOptions.MQTT_VERSION_3_1_1;

        private boolean cleanSession = true;

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder clientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder password(char[] password) {
            this.password = password;
            return this;
        }

        public Builder socketFactory(String sslFilePath) {
            this.sslFilePath = sslFilePath;
            return this;
        }

        public Builder connectionTimeout(int connectionTimeout) {
            this.connectionTimeout = connectionTimeout;
            return this;
        }

        public Builder keepAliveInterval(int keepAliveInterval) {
            this.keepAliveInterval = keepAliveInterval;
            return this;
        }

        public Builder mqttVersion(int mqttVersion) {
            this.mqttVersion = mqttVersion;
            return this;
        }

        public Builder cleanSession(boolean cleanSession) {
            this.cleanSession = cleanSession;
            return this;
        }

        public MqttParam build() {
            return new MqttParam(this);
        }
    }
}
