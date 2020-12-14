/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
Les topics sont le robot1
                robot2,
                robot3,
                le smartphone
 */

package robot;

/**
 *
 * @author 33612
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.eclipse.paho.client.mqttv3.*;

public class Robot {
    public static final int NB_SALLE = 10;
    private static List<Integer> salleNettoyees;
    private static Boolean running = true;
    public static void main(String[] args) throws MqttException, InterruptedException {
      Random rand = new Random();
        salleNettoyees = new ArrayList<>();
        
        // client
        MqttClient client = new MqttClient("tcp://localhost:1883", "robot_"+ rand.nextInt(10000));
        
        // receive
        client.setCallback(new MqttCallback() {
            @Override
            public void messageArrived(String topic, MqttMessage mm) throws Exception {
                switch(topic) {
                    case "/nettoie/start":
                        // enregistrement de la salle nettoyée
                        String temp = mm.toString();
                        Integer salle = Integer.parseInt(temp);
                        salleNettoyees.add(salle);
                        break;
                    case "/nettoie/stop":
                        // arret du robot
                        running = false;
                        System.out.println("stop");
                        break;
                    case "/nettoie/restart":
                        // recommencer le nettoyage
                        salleNettoyees.clear();
                        System.out.println("restart");
                        break;
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken imdt) {}
            
            @Override
            public void connectionLost(Throwable thrwbl) {}
        });
        client.connect();
        // Subscribe
        client.subscribe("/nettoie/#");
                
        Integer salle = null;
        while(running) {
            // choix salle : vérification que la salle n'a pas été déjà nettoyée
            do {
                int sallsTmp = rand.nextInt(NB_SALLE)+1;
                if(!salleNettoyees.contains(sallsTmp))
                    salle = sallsTmp;
            } while(salleNettoyees.size() != NB_SALLE && salle == null);
            
            // Il y a une salle à nettoyer
            if(salle != null) {
                // pub
                MqttMessage mes = new MqttMessage();
                mes.setPayload(salle.toString().getBytes());
                client.publish("/nettoie/start", mes);

                Thread.sleep(2000);
                client.publish("/nettoie/end", mes);
                salle = null;
            // Il n'y a  pas de salle à nettoyer
            } else {
                System.out.println("aucune salle à nettoyer");
                Thread.sleep(2000);
            }
        }
        client.disconnect();

    }
}
