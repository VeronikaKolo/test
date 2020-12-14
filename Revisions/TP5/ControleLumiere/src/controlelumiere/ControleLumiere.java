/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlelumiere;

/**
 *
 * @author 33612
 */
import org.eclipse.paho.client.mqttv3.*;
import spark.Request;
import spark.Response;
import static spark.Spark.get;
import static spark.Spark.post;

public class ControleLumiere 
{

    static class MQTTControleLumiere 
    {
        MqttClient client;

        MQTTControleLumiere() throws MqttException {
            // Creation un client
            client = new MqttClient("tcp://localhost:1883","controlelumiere");  
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                }
                
                // Callback pour la reception des messages
                @Override
                public void messageArrived(String topic,
                        MqttMessage msg) throws Exception {
                   
                    String message = new String(msg.getPayload());
                    if (topic.contains("intensite_lumiere"))
                        intensiteLumiere = Integer.parseInt(message);
                    else if (topic.contains("presence"))
                        presence = message.contains("oui");
                 
                    calculEtatLumiere();
                    System.out.println(topic + " : " + message);
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                }
            });
            // Connexion du client
            client.connect();
            // Definition des topics pour la reception des messages, un thread est cree
            client.subscribe("intensite_lumiere");
            client.subscribe("presence");
        }


        public void publish(boolean lumiereAllumee) throws MqttException 
        {
            String content;
            if (lumiereAllumee) {
                content = "allumee";
            } else {
                content = "eteinte";
            }
            String topic = "lumiere";
            MqttMessage message = new MqttMessage(content.getBytes());
            System.out.println("Publish message: " + message);
            client.publish(topic, message);
        }

        public void disconnect() throws MqttException {
            client.disconnect();
        }
    }
    
    static boolean forceLampeAllumee = false;
    static boolean etatLampeAllumee = false;
    static int intensiteLumiere = 0;
    static boolean presence = false;
    static MQTTControleLumiere s;

 
    private static void calculEtatLumiere() throws MqttException {
        boolean nouvelEtat;
        if (intensiteLumiere > 50) {
            nouvelEtat = false;
        } else if (presence) {
            nouvelEtat = true;
        } else if (forceLampeAllumee) {
            nouvelEtat = true;
        } else {
            nouvelEtat = false;
        }
        if (nouvelEtat != etatLampeAllumee) {
            etatLampeAllumee = nouvelEtat;
            s.publish(etatLampeAllumee);
        }
    }

    public static void main(String[] args) throws MqttException 
    {
        s = new MQTTControleLumiere();

        post("/forcelumiere", (Request request, Response response) -> {
          
            String val = request.queryParams("valeur");
            forceLampeAllumee = val.contains("oui");
            calculEtatLumiere();
            return "OK";
        });
   
        get("/lampe", (Request request, Response response) -> {
           
            if (etatLampeAllumee) {
                return "{\"lampeAllumee\": \"oui\"}";
            } else {
                return "{\"lampeAllumee\": \"non\"}";
            }
        });
    }
}

