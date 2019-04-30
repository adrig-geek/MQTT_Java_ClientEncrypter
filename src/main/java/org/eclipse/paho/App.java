package org.eclipse.paho;

import MQTT.MQTTClient;

public class App {


    public static void main( String[] args ){
        MQTTClient mqtt = new MQTTClient("tcp://172.16.99.131:1883");
        mqtt.publish("Fitotreballa","test");
        mqtt.disconnect();
    }
}
