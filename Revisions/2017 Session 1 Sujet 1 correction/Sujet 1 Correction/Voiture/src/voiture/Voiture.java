/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package voiture;

import java.util.Random;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * 
 * QUESTION 1 : liste des topics
 * voiture
 * densite
 * feu
 * duree/vert
 * duree/rouge
 *
 * 
 */
public class Voiture {

    public static void main(String[] args) throws MqttException, InterruptedException {
        MqttClient voiture = new MqttClient("tcp://localhost:1883", "voiture");
        Random random = new Random();
        voiture.connect();

        while (true) {
            voiture.publish("voiture", new MqttMessage(String.valueOf(random.nextInt(6)).getBytes()));
            Thread.sleep(1000);
        }
    }
}
   