/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartphone;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import org.json.*;
/**
 *
 * @author 33612
 */
public class Smartphone {

     // Methode pour afficher l'etat de la lampe
    private static void afficherEtatLampe() throws IOException 
    {
        String charset = "UTF-8";  // Or in Java 7 and later, use the constant: java.nio.charset.StandardCharsets.UTF_8.name()
        String url = "http://localhost:4567/lampe";
        URLConnection connection = new URL(url).openConnection();
        connection.setDoOutput(false);  // Triggers GET.
        connection.setRequestProperty("Accept-Charset", charset);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);

        InputStream response = connection.getInputStream();
        // Obtenir le resultat de la requete
        try (Scanner scanner = new Scanner(response)) {
            String responseBody = scanner.useDelimiter("\\A").next();
            JSONObject obj = new JSONObject(responseBody);
            String etatLampe = obj.getString("lampeAllumee");
            System.out.println("Lampe allumee: " + etatLampe);
        }
    }


    // Methode pour forcer l'allumage de la lampe
    private static void forceLampeAllumee(boolean force) throws IOException 
    {        
        // Definir l'adresse URL de la requete
        String charset = "UTF-8";  // Or in Java 7 and later, use the constant: java.nio.charset.StandardCharsets.UTF_8.name()
        String url = "http://localhost:4567/forcelumiere";
        String query;
        if (force)
            query = String.format("valeur=oui");
        else
            query = String.format("valeur=non");            
        URLConnection connection = new URL(url).openConnection();
        connection.setDoOutput(true); // Triggers POST.
        connection.setRequestProperty("Accept-Charset", charset);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);

        try (OutputStream output = connection.getOutputStream()) {
            output.write(query.getBytes(charset));
        }

        InputStream response = connection.getInputStream();
        // Obtenir le resultat de la requete
        try (Scanner scanner = new Scanner(response)) {
            String responseBody = scanner.useDelimiter("\\A").next();
            //System.out.println("Reponse de la requete HTTP POST:" + responseBody);
        }
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // Affiche un menu
        Scanner in = new Scanner(System.in);
        boolean doExit=false;
        do
        {
            System.out.println("Choisir une option:");
            System.out.println(" 1: Forcer l'allumage de la lampe");
            System.out.println(" 2: Afficher l'etat de la lampe");
            System.out.println(" 3: Arreter le programme");
            int choix=in.nextInt();
            switch(choix)
            {
                case 1:
                    System.out.println("Forcer l'allumage de la lampe: 0 pour non, 1 pour oui");
                    int tmp=in.nextInt();
                    forceLampeAllumee(tmp==1);
                    break;
                case 2:
                    afficherEtatLampe();
                    break;
                case 3:
                    doExit=true;
                    break;
            }
        }
        while(doExit==false);
    }
    
}
