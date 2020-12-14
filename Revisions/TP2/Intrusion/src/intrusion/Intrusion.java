/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intrusion;
import org.eclipse.paho.client.mqttv3.*;
/**
 *
 * @author 33612
 */
public class Intrusion {

    
    private static String presence="";
    private static String porte="";
    private static boolean pubIntrusion=false;
    
    
    private static MqttClient clientSub;
    private static MqttClient clientPub;
    
    public static void main(String[] args) throws MqttException, InterruptedException {
        clientPub = new MqttClient("tcp://localhost:1883", "intrusionPub");
        clientPub.connect();

        clientSub = new MqttClient("tcp://localhost:1883", "intrusionSub");
        clientSub.setCallback(new MqttCallback(){
            @Override
            public void connectionLost(Throwable thrwbl) {
            }

            @Override
            public void messageArrived(String string, MqttMessage mm) throws Exception 
            {
                switch (string){
                    case "presence":
                        presence = mm.toString();
                        break;
                    case "porte":
                        porte = mm.toString();
                        break;
                }
                
                if(presence.equals("oui") || porte.equals("ouverte"))
                {
                    System.out.println("Intrusion detectee");
                    pubIntrusion=true;
                }
                else
                {
                    System.out.println("Pas d'intrusion");
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken imdt) {
            }
            
        });
        clientSub.connect();
        clientSub.subscribe("#");
        
        int i=0;
        while(i<5000)
        {
            Thread.sleep(1000);
            if (pubIntrusion)
            {
                MqttMessage message = new MqttMessage();
                String str="oui";
                message.setPayload(str.getBytes());
                clientPub.publish("intrusion", message);
                pubIntrusion=false;
            }
        }
        clientSub.disconnect();
        clientPub.disconnect();
    }
    
}