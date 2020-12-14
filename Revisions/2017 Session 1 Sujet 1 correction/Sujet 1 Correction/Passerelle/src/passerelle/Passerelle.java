/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package passerelle;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static spark.Spark.*;

/**
 *
 * @author fst37.28
 */
public class Passerelle {
    
     private static final Logger log = LoggerFactory.getLogger(Passerelle.class);
     
    private static int DUREE_FEU_VERT;
    private static int DUREE_FEU_ROUGE;
    private static int densite;
    
    public static void main(String[] args) throws MqttException, InterruptedException {
        MqttClient passerelle = new MqttClient("tcp://localhost:1883", "passerelle");
        passerelle.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable thrwbl) {
            }
            @Override
            public void messageArrived(String string, MqttMessage mm) throws Exception {
                densite = Integer.valueOf(String.valueOf(mm));
                log.info("new densite : {}",densite);
            }
            @Override
            public void deliveryComplete(IMqttDeliveryToken imdt) {
            }
        });
        
        passerelle.connect();
        passerelle.subscribe("densite");
        
        get("/", (request,response) -> {
            JSONObject obj = new JSONObject();
            obj.put("densite",densite);
            return obj.toString();
        });
        
        // POST /feuvert?param=value
        post("/feuvert",(request,response) -> {
            DUREE_FEU_VERT = Integer.valueOf(request.queryParams("duree"));
            passerelle.publish("duree/vert", new MqttMessage(String.valueOf(DUREE_FEU_VERT).getBytes()));
            return "";
        });
        
        // POST /feurouge?param=value
        post("/feurouge",(request,response) -> {
            DUREE_FEU_ROUGE = Integer.valueOf(request.queryParams("duree"));
            passerelle.publish("duree/rouge", new MqttMessage(String.valueOf(DUREE_FEU_VERT).getBytes()));
            return "";
        });
        
        while (true);
    }
    
}
