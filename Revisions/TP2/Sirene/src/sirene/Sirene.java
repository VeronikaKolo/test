/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sirene;

import org.eclipse.paho.client.mqttv3.*;

/**
 *
 * @author 33612
 */
public class Sirene {

    
    
    private static String intrusion="";
    private static String bouton="";
  
    private static MqttClient clientSub;
  
    
    public static void main(String[] args) throws MqttException, InterruptedException {
     

        clientSub = new MqttClient("tcp://localhost:1883", "SireneSub");
        clientSub.setCallback(new MqttCallback(){
            @Override
            public void connectionLost(Throwable thrwbl) {
            }

            @Override
            public void messageArrived(String string, MqttMessage mm) throws Exception 
            {
                switch (string){
                    case "bouton":
                        bouton = mm.toString();
                        break;
                    case "porte":
                        intrusion = mm.toString();
                        break;
                }
                
                if(bouton.equals("1") || intrusion.equals("Intrusion detectee"))
                {
                    System.out.println("Sirene en état de marche");
                }
                else
                {
                    System.out.println("Sirene fonctionne pas");
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
        }
        clientSub.disconnect();
   
    }
}
