package com.paho.mqtt.client;

import com.paho.mqtt.client.config.CustomMqttClient;
import com.paho.mqtt.client.config.MqttCallbackImpl;
import com.paho.mqtt.client.config.MqttParam;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;


/**
 * Mqtt client
 *
 * @author wjl
 * @date 2020-07-20
 */
public class MqttClientApplication {

    public static void main(String[] args) {
        MqttParam mqttParam = new MqttParam.Builder()
                .url("tcp://127.0.0.1:1883")
                .clientId("client")
                .userName("username")
                .password("password".toCharArray())
                .mqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1)
                .cleanSession(true)
                .connectionTimeout(20)
                .keepAliveInterval(30)
                //.socketFactory("src/main/resources/certificate-name.pem")
                .build();
        CustomMqttClient mqttClient = new CustomMqttClient(mqttParam);
        mqttClient.connect(new MqttCallbackImpl(mqttClient), "$msg/topic");
    }
}
