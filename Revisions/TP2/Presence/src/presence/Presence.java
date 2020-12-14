/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presence;

import java.util.Random;
import org.eclipse.paho.client.mqttv3.*;

/**
 *
 * @author 33612
 */
public class Presence {

    /**
     * @param args the command line arguments
     */
     private static MqttClient clientPub;
    private static MqttMessage message;
    private static int pres;
    private static String stringPres;
    public static void main(String[] args) throws MqttException, InterruptedException {
        // TODO code application logic here
    // Creer un client
        // localhost:1883  Adr. et port du broker
        // client_nom_1 : Nom du client
        clientPub = new MqttClient("tcp://localhost:1883", "presence");
        clientPub.connect();
        
        Random rand = new Random();

        int i=0;
        while(i<5000)
        {
            pres=rand.nextInt(2);
            if(pres==1){
                stringPres = "oui";
            }else{
                stringPres = "non";
            }
            message.setPayload(stringPres.getBytes());
            clientPub.publish("presence", message);
            Thread.sleep(1000);
        }
        clientPub.disconnect();


    }
    
}
