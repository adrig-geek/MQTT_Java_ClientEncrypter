package MQTT;

import AES.AesCipher;

public class MQTTEncrypter {

    private static final String key = "0123456789012345";
    private static final String iv = "0000000000000000";
    private AesCipher aesCipher;

    public MQTTEncrypter(){
        configureAES();
    }

    private void configureAES(){
        // create crypter
        aesCipher = new AesCipher();

        // set keys
        aesCipher.setCryptKey(key, iv);
    }

    public String encryptMQTTMessage(String message){
        String result = aesCipher.encrypt(message);
        return result;
    }


}
