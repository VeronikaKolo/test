/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thermometre;

import java.util.Random;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Scanner;

public class Thermometre {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException, IOException {
        // TODO code application logic here
        Random rand = new Random();
        int c=0;
        while (c<5000)
        {
            Thread.sleep(1000);
            System.out.println("Running...");
            c++;

            int  temperature = rand.nextInt(30);        

            String charset = "UTF-8";  
            String url = "http://localhost:4567/thermometre";
            String query = String.format("temperature=%s", temperature);
            URLConnection connection = new URL(url).openConnection();
            connection.setDoOutput(true); 
            connection.setRequestProperty("Accept-Charset", charset);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
            
            try (OutputStream output = connection.getOutputStream()) {
                output.write(query.getBytes(charset));
            }

            InputStream response = connection.getInputStream();
            try (Scanner scanner = new Scanner(response)) {
                String responseBody = scanner.useDelimiter("\\A").next();
                System.out.println("Reponse de la requete HTTP POST:"+responseBody);
            }

        }
    }
    
}