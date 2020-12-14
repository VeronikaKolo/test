/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ouverture;

import java.util.Random;
import org.eclipse.paho.client.mqttv3.*;
/**
 *
 * @author 33612
 */
public class Ouverture {

      private static MqttClient clientPub;
    private static MqttMessage message;
    private static int ouv;
    private static String stringOuv;
    public static void main(String[] args) throws MqttException, InterruptedException {
        // TODO code application logic here
    // Creer un client
        // localhost:1883  Adr. et port du broker
        // client_nom_1 : Nom du client
        clientPub = new MqttClient("tcp://localhost:1883", "ouverture");
        clientPub.connect();
        
        Random rand = new Random();

        int i=0;
        while(i<5000)
        {
             ouv=rand.nextInt(2);
            if(ouv==1){
                stringOuv = "ouverte";
            }else{
                stringOuv = "ferme";
            }
            message.setPayload(stringOuv.getBytes());
            clientPub.publish("ouverture", message);
            Thread.sleep(1000);
        }
        clientPub.disconnect();
    }
}
