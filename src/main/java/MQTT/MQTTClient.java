package MQTT;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MQTTClient implements MqttCallback {

    private static final String topic = "enc/";
    private static final int qos = 2;
    private String broker;
    private String clientId;
    private MemoryPersistence persistence = new MemoryPersistence();
    private int clientIdAcum;
    private MqttClient mqttClient;
    private MQTTEncrypter mqttEncrypter;

    public MQTTClient(String broker){
        this.broker = broker;
        clientId = Integer.toString(clientIdAcum++);
        connect();
        mqttEncrypter = new MQTTEncrypter();


    }

    private void connect(){
        try {
            this.mqttClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            System.out.println("Connecting to broker: "+broker);
            mqttClient.connect(connOpts);
            System.out.println("Connected");
            mqttClient.setCallback(this);
            mqttClient.subscribe("#");
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }


    public void publish(String content, String topicIn){
        try {
            content = mqttEncrypter.encryptMQTTMessage(content);
            System.out.println("Publishing message: "+content);
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            mqttClient.publish(MQTTClient.topic+topicIn, message);
            System.out.println("Message published");
        } catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }
    }

    public void disconnect(){
        try {
            mqttClient.disconnect();
            System.out.println("Disconnected");
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void messageArrived(String topic, MqttMessage message){
        System.out.println("MSG ARR. [ " + topic + " ]");
        System.out.println(new String(message.getPayload()));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // TODO Auto-generated method stub

    }

    @Override
    public void connectionLost(Throwable cause) {
        // TODO Auto-generated method stub

    }

}
