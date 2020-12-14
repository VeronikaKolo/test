/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur;

import spark.Request;
import spark.Response;
import static spark.Spark.*;

public class Serveur {

    static int temperatureDefaut=20;        
    static int temperatureCourante=20;
    static boolean radiateurMarche=false;
    
    public static void main(String[] args) 
    {    

        post("/thermometre", (Request request, Response response) -> {
            String val=request.queryParams("temperature");
            temperatureCourante=Integer.parseInt(val);
            radiateurMarche= temperatureCourante<temperatureDefaut;
            System.out.println("Temperature recue: "+val);
            return "OK";
            });
        
        
        get("/radiateur", (request, response) -> {
            if (radiateurMarche)
                return "{\"radiateurMarche\": \"true\"}";
            else
                return "{\"radiateurMarche\": \"false\"}";
            });
        
        
        get("/thermometre", (request, response) -> {
            return "{\"temperatureCourante\":"+temperatureCourante+"}";
            });
        
        
        post("/chauffage", (Request request, Response response) -> {
            String val=request.queryParams("temperature");
            temperatureDefaut=Integer.parseInt(val);
            radiateurMarche= temperatureCourante<temperatureDefaut;
            System.out.println("Nouvelle temperature de chauffage: "+temperatureDefaut);
            return "OK";
            });
    }
    
}