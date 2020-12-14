/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartphone;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import org.json.JSONObject;

/**
 *
 * @author 33612
 */
public class Smartphone {
    public static final Integer NB_SALLE = 10;

    public static void main(String... args) throws MalformedURLException, IOException {
        boolean running = true;
        
        String charset = "UTF-8";
        
        Scanner sc = new Scanner(System.in);
        
        while(running) {
         
            System.out.println("1 etat nettoyage");
            System.out.println("2 arret nettoyage");
            System.out.println("3 nombre de salle nettoyées");

          
            int option = sc.nextInt();
            String url;
            switch(option) {
                
                case 1:
                  
                    url = "http://localhost:4567/salles-nettoyees";
                    URLConnection connection = new URL(url).openConnection();
                    InputStream res = connection.getInputStream();
                    try(Scanner scH = new Scanner(res)) {
                        Integer salles = scH.useDelimiter("\\A").nextInt();
                    
                        if(salles >= NB_SALLE) {
                            System.out.println("nettoyage fini");
                        } else {
                            System.out.println("nettoyage en cours");
                        }
                    }
                    break;
              
                case 2:
                    
                    url = "http://localhost:4567/stop-nettoyage";
                    charset = "UTF-8";
                    connection = new URL(url).openConnection();
                    connection.setDoOutput(true);
                    connection.setRequestProperty("Accept-Charset", charset);
                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
                    OutputStream output = connection.getOutputStream();
                    
                    InputStream response;
                    response = connection.getInputStream();
                    try(Scanner scanner = new Scanner(response))
                    {
                        System.out.println("Modification prise en compte.");
                    }
                    break;
             
                case 3:
                   
                    url = "http://localhost:4567/salles-nettoyees";
                    URLConnection connection3 = new URL(url).openConnection();
                    InputStream res3 = connection3.getInputStream();
                    try(Scanner scH = new Scanner(res3)) {
                     
                        Integer salles = scH.useDelimiter("\\A").nextInt();
                        System.out.println("Nombre de salles nettoyées : " + salles + " / " + NB_SALLE);
                    }
                    break;
            }
        }
    }
}
