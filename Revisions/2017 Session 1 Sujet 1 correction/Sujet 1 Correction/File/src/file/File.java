/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file;

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
public class File {

    private static final Logger log = LoggerFactory.getLogger(File.class);

    private static String COULEUR_FEU = "";
    private static final String VERT = "vert";
    private static final String ROUGE = "rouge";
    private static int NB_VOITURE_DANS_FILE_ATTENTE;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws MqttException, InterruptedException {
        NB_VOITURE_DANS_FILE_ATTENTE = 0;
        MqttClient file = new MqttClient("tcp://localhost:1883", "file");
        file.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable thrwbl) {
            }

            @Override
            public void messageArrived(String string, MqttMessage mm) throws Exception {
                if (string.contains("feu")) {
                    COULEUR_FEU = String.valueOf(mm);
                    if (COULEUR_FEU.contains("vert")) {
                        NB_VOITURE_DANS_FILE_ATTENTE = 0;
                    }
                } 
                
                else if (COULEUR_FEU.contains("rouge")) {
                    int val = Integer.valueOf(String.valueOf(mm));
                    NB_VOITURE_DANS_FILE_ATTENTE += val;
                }

                log.info("Nombre de voiture en file d'attente : {}", NB_VOITURE_DANS_FILE_ATTENTE);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken imdt) {
            }
        });

        file.connect();
        file.subscribe("feu");
        file.subscribe("voiture");

        while (true);
    }

}
