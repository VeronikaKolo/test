/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exercice2;

import org.eclipse.paho.client.mqttv3.*;

/**
 *
 * @author 33612
 */
public class Exercice2 {


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws MqttException {
        // TODO code application logic here
       MqttClient client = new 
    MqttClient("tcp://localhost:1883",  	// Adr. et port du broker
			"client_nom_2");  	// Nom du client (tous les clients doivent 
					// avoir des noms differents)
 client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {}
	       // Callback pour la reception des messages
                @Override
                public void messageArrived(String topic,
                        MqttMessage message)throws Exception {
                    System.out.println(new String(message.getPayload()));
                }
                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {}
            });
// Connexion du client
client.connect();
// Definition des topics pour la reception des messages, un thread est cree
client.subscribe("#");
// Arret de la connexion, le thread s’arrete
client.disconnect();

    }
    
}
