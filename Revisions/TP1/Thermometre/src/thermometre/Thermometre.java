/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thermometre;
import java.util.Random;
import org.eclipse.paho.client.mqttv3.*;

/**
 *
 * @author 33612
 */
public class Thermometre {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws MqttException, InterruptedException {
        // TODO code application logic here
    // Creer un client
        // localhost:1883  Adr. et port du broker
        // client_nom_1 : Nom du client
         MqttClient clientPub = new MqttClient("tcp://localhost:1883", "thermometre");
        clientPub.connect();
        
        Random rand = new Random();

        int i=0;
        while(i<5000)
        {
            Thread.sleep(1000);
            int temperature = rand.nextInt(30);        // Entre 0 et 30
            String content = String.valueOf(temperature);
            MqttMessage message = new MqttMessage();
            message.setPayload(content.getBytes());
            clientPub.publish("temperature", message);
        }
        clientPub.disconnect();


    }
    
    
}
