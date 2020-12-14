/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feu;

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
public class Feu {
    
    private static final Logger log = LoggerFactory.getLogger(Feu.class);
    
    private static int DUREE_FEU_VERT = 5000;
    private static int DUREE_FEU_ROUGE = 5000;
    private static final String VERT = "vert";
    private static final String ROUGE = "rouge";
    

    public static void main(String[] args) throws MqttException, InterruptedException {
        
        
         MqttClient feu = new MqttClient("tcp://localhost:1883", "feu");
         feu.setCallback(new MqttCallback() {
             @Override
             public void connectionLost(Throwable thrwbl) {
             }

             @Override
             public void messageArrived(String string, MqttMessage mm) throws Exception {
                 int value = Integer.valueOf(String.valueOf(mm));
                 if(string.contains("duree/vert")){
                     DUREE_FEU_VERT = value;
                     log.info("Nouvelle duree feu vert : {}",DUREE_FEU_VERT);
                 }else if(string.contains("duree/rouge")){
                     DUREE_FEU_ROUGE = value;
                     log.info("Nouvelle duree feu rouge : {}",DUREE_FEU_ROUGE);
                 }
             }

             @Override
             public void deliveryComplete(IMqttDeliveryToken imdt) {
             }
         });
         feu.connect();
         feu.subscribe("duree/vert");
         feu.subscribe("duree/rouge");
         
         while(true){
             // 1 publish feu au rouge
             feu.publish("feu", new MqttMessage(String.valueOf(ROUGE).getBytes()));
             // 2 pause corrsp feu rouge
             Thread.sleep(DUREE_FEU_ROUGE);
             // 3. push feu au vert
             feu.publish("feu", new MqttMessage(String.valueOf(VERT).getBytes()));
             // 4. pause corresp feu vert
             Thread.sleep(DUREE_FEU_VERT);
         }
    }
}
