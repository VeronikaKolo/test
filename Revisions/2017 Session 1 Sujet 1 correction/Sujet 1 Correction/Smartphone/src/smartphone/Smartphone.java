/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartphone;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Scanner;

/**
 *
 * @author fst37.28
 */
public class Smartphone {
    
    private static String buildResponse(InputStream input) {
        try {
            Scanner sc = new Scanner(input);
            return sc.useDelimiter("\\A").next();
        } catch (Exception ex) {
            return "empty response";
        }
    }

    private static String getQueryFeuVert(int value) throws UnsupportedEncodingException {
        return String.format("duree=%s", URLEncoder.encode(String.valueOf(value), "UTF-8"));
    }

    private static String getQueryFeuRouge(int value) throws UnsupportedEncodingException {
        return String.format("duree=%s", URLEncoder.encode(String.valueOf(value), "UTF-8"));
    }

    private static InputStream performGET() throws UnsupportedEncodingException, MalformedURLException, IOException {
        URLConnection co = new URL("http://localhost:4567/").openConnection();
        co.setRequestProperty("Accept-Charset", "UTF-8");
        return co.getInputStream();
    }

    private static InputStream performPOSTvert(int value) throws MalformedURLException, IOException {
        URLConnection co = new URL("http://localhost:4567/feuvert").openConnection();
        co.setDoOutput(true);
        co.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

        OutputStream out = co.getOutputStream();
        out.write(getQueryFeuVert(value).getBytes());
        return co.getInputStream();
    }
    
     private static InputStream performPOSTrouge(int value) throws MalformedURLException, IOException {
        URLConnection co = new URL("http://localhost:4567/feurouge").openConnection();
        co.setDoOutput(true);
        co.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

        OutputStream out = co.getOutputStream();
        out.write(getQueryFeuRouge(value).getBytes());
        return co.getInputStream();
    }
    
    private static void menu(){
        System.out.println("Menu:");
        System.out.println("1 - Afficher densite :");
        System.out.println("2 - Changer la duree feu rouge (en ms) ");
        System.out.println("3 - Changer la duree feu vert (en ms)");
        System.out.println("Choix ?");
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        int choix;
        int value;
        Scanner sc = new Scanner(System.in);
        
        while(true){
            menu();
            choix = sc.nextInt();
            switch(choix){
                case 1:
                    String json_response = buildResponse(performGET());
                    System.out.println(json_response);
                    break;
                case 2:
                    System.out.println("Nouvelle duree feu rouge? (ms)");
                    value = sc.nextInt();
                    buildResponse(performPOSTrouge(value));
                    break;
                case 3:
                    System.out.println("Nouvelle duree feu vert? (ms)");
                    value = sc.nextInt();
                    buildResponse(performPOSTvert(value));
                    break;
            }
            Thread.sleep(10);
        }
    }

}
