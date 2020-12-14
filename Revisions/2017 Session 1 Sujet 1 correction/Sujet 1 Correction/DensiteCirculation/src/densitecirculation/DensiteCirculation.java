/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package densitecirculation;

import java.util.Random;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author fst37.28
 */
public class DensiteCirculation {
    
    private static final Logger log = LoggerFactory.getLogger(DensiteCirculation.class);
    private static int sommeVoiture = 0;

    public static void main(String[] args) throws MqttException, InterruptedException {
        MqttClient densite = new MqttClient("tcp://localhost:1883", "densite");
        
        densite.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable thrwbl) {
            }

            @Override
            public void messageArrived(String string, MqttMessage mm) throws Exception {
                
                if(string.equals("voiture")){
                    int val = Integer.valueOf(String.valueOf(mm));
                    sommeVoiture += val;
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken imdt) {
            }
        });
        
        densite.connect();
        densite.subscribe("voiture");
        while (true) {
            log.info("Nombre voitures : {} ",sommeVoiture);
            densite.publish("densite",new MqttMessage(String.valueOf(sommeVoiture).getBytes()));
            sommeVoiture = 0;
            Thread.sleep(5000);
        }
    }
    
}
