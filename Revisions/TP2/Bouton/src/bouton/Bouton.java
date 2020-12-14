/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bouton;

import java.util.Random;
import java.util.Scanner;
import org.eclipse.paho.client.mqttv3.*;
/**
 *
 * @author 33612
 */
public class Bouton {

   /**
     * @param args the command line arguments
     */
 
        private static MqttClient client;
    private static MqttMessage message;
    private static int choix;
    
    public static void main(String[] args) throws MqttException {
        client = new MqttClient("tcp://localhost:1883", "bouton");
        client.connect();
        message = new MqttMessage();
        
        Scanner in = new Scanner(System.in);
        
        while(true){
            System.out.println("Le menu :\n\t1 - Activer la sirene\n\t2 - Desctiver la sirene\n");
            choix = in.nextInt();
            message.setPayload(String.valueOf(choix).getBytes());
            client.publish("bouton", message);
        }
    }
    


    }
    

