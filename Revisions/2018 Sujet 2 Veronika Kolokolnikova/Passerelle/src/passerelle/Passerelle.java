/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package passerelle;
import org.eclipse.paho.client.mqttv3.*;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static spark.Spark.*;

public class Passerelle {
    public static final Integer NB_SALLE = 10;
    private static Integer nbSalles = 0;
    public static void main(String... args) throws MqttException, InterruptedException {
     
        MqttClient client = new MqttClient("tcp://localhost:1883", "passerelle");
        
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable thrwbl) {}

            @Override
            public void messageArrived(String string, MqttMessage mm) throws Exception {
                nbSalles++;
                System.out.println("salles nettoyees: " + nbSalles);
                // Une fois toutes les salles nettoyées on recommence
                if(nbSalles >= NB_SALLE) {
                    System.out.println("recommencer nettoyage");
                    MqttMessage mes = new MqttMessage();
                    Boolean b = true;
                    mes.setPayload(b.toString().getBytes());
                    client.publish("/nettoie/restart", mes);
                    nbSalles = 0;
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken imdt) {}
        });
        client.connect();
        client.subscribe("/nettoie/end");
        
        get("/salles-nettoyees", (req, res) -> {
            return nbSalles;
        });
        
        
        post("/stop-nettoyage", (req, res) -> {
            MqttMessage mes = new MqttMessage();
            Boolean b = true;
            mes.setPayload(b.toString().getBytes());
            client.publish("/nettoie/stop", mes);
            return "ok";
        });
        
        boolean running = true;
        while(running) {
            Thread.sleep(2000);
        }
    
    }
}
