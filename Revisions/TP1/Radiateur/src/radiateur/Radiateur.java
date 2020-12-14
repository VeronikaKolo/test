/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package radiateur;
import org.eclipse.paho.client.mqttv3.*;
/**
 *
 * @author 33612
 */
public class Radiateur {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws MqttException, InterruptedException {
        // TODO code application logic here

         MqttClient clientSub = new MqttClient("tcp://localhost:1883", "radiateur");
        clientSub.setCallback(new MqttCallback(){
            @Override
            public void connectionLost(Throwable thrwbl) {
            }

            @Override
            public void messageArrived(String string, MqttMessage mm) throws Exception 
            {
                String str = mm.toString();
                if (str.equals("ON"))
                    System.out.println("Radiateur en marche");
                else
                    System.out.println("Radiateur a l'arret");
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken imdt) {
            }
            
        });
        clientSub.connect();
        clientSub.subscribe("radiateur");
        
        int i=0;
        while(i<5000)
        {
            Thread.sleep(1000);
            //System.out.println("Main loop");
        }
        clientSub.disconnect();

    }
    
}
