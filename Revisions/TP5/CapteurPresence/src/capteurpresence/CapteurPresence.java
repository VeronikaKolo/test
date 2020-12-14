/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capteurpresence;
import java.util.Random;
import org.eclipse.paho.client.mqttv3.*;
/**
 *
 * @author 33612
 */
public class CapteurPresence {

       // TODO code application logic here
         private static MqttClient clientPub;
    
    public static void main(String[] args) throws MqttException, InterruptedException {
        clientPub = new MqttClient("tcp://localhost:1883", "occupee");
        clientPub.connect();
        
        Random rand = new Random();

            int presence = rand.nextInt(2);  
            String content;
            if (presence==0)
                content = String.valueOf("faux");
            else
                content = String.valueOf("vrai");
            
            MqttMessage message = new MqttMessage();
            message.setPayload(content.getBytes());
            clientPub.publish("occupee", message);
            clientPub.disconnect();
    }
    
}
