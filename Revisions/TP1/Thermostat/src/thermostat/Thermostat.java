/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thermostat;

import org.eclipse.paho.client.mqttv3.*;

/**
 *
 * @author 33612
 */
public class Thermostat {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws MqttException, InterruptedException {
        // TODO code application logic here
      // Client qui publie
         MqttClient clientPub = new MqttClient("tcp://localhost:1883", "thermostatPub");
        
        // CLient qui s'abonne
        MqttClient clientSub = new MqttClient("tcp://localhost:1883", "thermostatSub");
        clientSub.setCallback(new MqttCallback(){
            @Override
            public void connectionLost(Throwable thrwbl) {
            }

            @Override
            public void messageArrived(String string, MqttMessage mm) throws Exception 
            {
                String temperature = mm.toString();
                int val=Integer.parseInt(temperature);
                MqttMessage message = new MqttMessage();
                String str;
                if (val<15)
                    str="ON";
                else
                    str="OFF";
                message.setPayload(str.getBytes());
                clientPub.publish("radiateur", message);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken imdt) {
            }
            
        });
         clientSub.connect();
        clientSub.subscribe("temperature");
        
        int i=0;
        while(i<5000)
        {
            Thread.sleep(1000);
        }
        clientPub.disconnect();
        clientSub.disconnect();
    }
    }
    

