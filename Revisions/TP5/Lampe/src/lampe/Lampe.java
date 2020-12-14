/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lampe;

import org.eclipse.paho.client.mqttv3.*;

/**
 *
 * @author 33612
 */
class MQTTLampe
{
    MqttClient client;
    MQTTLampe() throws MqttException {
        // Creation un client
        client = new MqttClient("tcp://localhost:1883","lampe");  	
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
            }
  
            @Override
            public void messageArrived(String topic,
                    MqttMessage message) throws Exception {
                System.out.println(new String(message.getPayload()));
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
            }
        });
    
        client.connect();
        client.subscribe("lumiere");
    }
    
    public void disconnect() throws MqttException
    {
        client.disconnect();
    }
}
public class Lampe {

     public static void main(String[] args) throws MqttException, InterruptedException 
    {
        int i=0;

        MQTTLampe s=new MQTTLampe();
        
        while(i<5000)
        {
            Thread.sleep(5000);
            i++;
        }
        s.disconnect();

    }
    
}
