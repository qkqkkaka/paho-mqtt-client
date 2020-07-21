package com.paho.mqtt.client.config;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.concurrent.TimeUnit;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * mqtt callback class
 *
 * @author wjl
 * @date 2020-07-20
 */
public class MqttCallbackImpl implements MqttCallback {

    private static final int MAX_RECONNECT_TIMES = 20;

    private CustomMqttClient mqttClient;

    public MqttCallbackImpl(CustomMqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    @Override
    public void connectionLost(Throwable throwable) {
        System.out.println("connection lost and try to reconnect");
        int retryTimes = 0;
        while (true) {
            if (mqttClient.reConnect()) {
                System.out.println("reconnect success");
                break;
            }
            if (++retryTimes >= MAX_RECONNECT_TIMES) {
                System.out.println("the maximum number of reconnections has been reached");
                break;
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        // handle message
        //there will be some problems about connection if business throw a exception without try-catch block
        try {
            String payload = new String(mqttMessage.getPayload(), UTF_8);
            System.out.println("message payload:" + payload);
        } catch (Exception e) {
            System.out.println("business throw a exception");
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
