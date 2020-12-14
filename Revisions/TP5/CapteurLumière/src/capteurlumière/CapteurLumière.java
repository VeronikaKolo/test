/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capteurlumière;
import java.util.Random;
import org.eclipse.paho.client.mqttv3.*;
/**
 *
 * @author 33612
 */
public class CapteurLumière {

      // TODO code application logic here
         private static MqttClient clientPub;
    
    public static void main(String[] args) throws MqttException, InterruptedException {
        clientPub = new MqttClient("tcp://localhost:1883", "capteur lumière");
        clientPub.connect();
        
        Random rand = new Random();

        int i=0;

            int luminosite = rand.nextInt(100);      
            String content = String.valueOf(luminosite);
            MqttMessage message = new MqttMessage();
            message.setPayload(content.getBytes());
            clientPub.publish("luminosite", message);
            clientPub.disconnect();
    }
    
}
