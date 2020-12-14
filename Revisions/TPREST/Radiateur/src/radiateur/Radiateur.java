/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package radiateur;
 
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import org.json.*;

public class Radiateur {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException, IOException {
        // TODO code application logic here
        
        int c=0;
        while (c<5000)
        {
            Thread.sleep(1000);
            System.out.println("Running...");
            c++;

            String charset = "UTF-8";  
            String url = "http://localhost:4567/radiateur";
            URLConnection connection = new URL(url).openConnection();
            connection.setDoOutput(false); 
            connection.setRequestProperty("Accept-Charset", charset);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
            
            InputStream response = connection.getInputStream();
            try (Scanner scanner = new Scanner(response)) 
            {
                String responseBody = scanner.useDelimiter("\\A").next();

                JSONObject obj = new JSONObject(responseBody);
                String radiateurMarche=obj.getString("radiateurMarche");
                if (radiateurMarche.contains("true"))
                     System.out.println("Radiateur en marche");
                else
                     System.out.println("Radiateur a l'arret");
            }

        }
    }
    
}
