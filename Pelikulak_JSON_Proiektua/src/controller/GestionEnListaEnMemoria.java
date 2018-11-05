/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import model.Pelikula;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javafx.collections.FXCollections;
import javax.json.JsonReader;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;

/**
 *
 * @author DM3-2-12
 */
public class GestionEnListaEnMemoria{
    
    
     public static ObservableList<Pelikula> jsonPelikulaKargatu(File file) 
    {
        
        long fileSizeInBytes = file.length(); // fitxategiaren tamaina Byte-tan
            long fileSizeInKB = fileSizeInBytes/1024; // fitxategiaren tamaina KB-etan
            if (fileSizeInKB<2){
                return jsonFitxategianKargatu(file); // json OBJECT MODEL erabilita
                }
            else{
                return jsonStreamPeliKargatu(file); // json STREAM erabilita
            }
    }
     
     public static void jsonGorde(ObservableList<Pelikula> Pelikula, File file) {
        long fileSizeInBytes = file.length(); // fitxategiaren tamaina Byte-tan
                long fileSizeInKB = fileSizeInBytes/1024; // fitxategiaren tamaina KB-etan
                if (fileSizeInKB<2) 
                    jsonPelGorde(Pelikula, file);
                else
                    jsonStreamFitxategianGorde(Pelikula, file);  //Stream erabilita 

    }
     
     
     public static void jsonPelGorde(ObservableList<Pelikula> oList, File file) {
    JsonWriter jsonWriter = null;
        
        try { 
            JsonArrayBuilder jsonArray = Json.createArrayBuilder(); 
            JsonObjectBuilder jsonObject = Json.createObjectBuilder(); 
            
            for (Pelikula pelikula : oList) {
               
                jsonObject.add("Izena", pelikula.getIzena()); 
                jsonObject.add("Zuzendaria", pelikula.getZuzendaria());
                jsonObject.add("Durazioa", pelikula.getDurazioa());
                jsonObject.add("Adina", String.valueOf(pelikula.getAdina()));
                jsonObject.add("Urtea", String.valueOf(pelikula.getUrtea()));
                
                
                JsonObject jsonObjPeli = jsonObject.build(); 
                jsonArray.add(jsonObjPeli); 
            }
            JsonArray jsonArrayPeli = jsonArray.build();
            jsonWriter = Json.createWriter(new FileOutputStream(file, false));
            jsonWriter.write(jsonArrayPeli); 
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GestionEnListaEnMemoria.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        
}
     
     public static void jsonStreamFitxategianGorde(ObservableList<Pelikula> oList, File file) {
        try{
            JsonGenerator jsonGen = Json.createGenerator(new FileWriter(file));
            jsonGen.writeStartArray(); // Array-a idazten hasi
            
            for (Pelikula pel : oList) {
                /* Objektuak, elementu bakoitza gehitu */
                jsonGen.writeStartObject()
                        .write("Izena", pel.getIzena())  
                        .write("Zuzendaria", pel.getZuzendaria())
                        .write("Durazioa", pel.getDurazioa())
                        .write("Adina", String.valueOf(pel.getAdina()))
                        .write("Urtea", String.valueOf(pel.getUrtea()))
                    .writeEnd(); // objektua idazten bukatu
            } 
            
            jsonGen.writeEnd(); // Array-a idazten bukatu
            System.out.println("JSON fitxategian idatzi STREAM erabilita\n"); // Mezua, stream erabilita datuak kargatzen direla jakiteko
            jsonGen.close();
        } 
        catch (IOException ex) {
            System.err.println("Arazoak daude datuekin.");
        }
    } 
    
public static ObservableList<Pelikula> jsonFitxategianKargatu(File file) {
        ObservableList<Pelikula> obList = FXCollections.observableArrayList();
        try {
            JsonReader reader = Json.createReader(new FileReader(file));
            
            /* Fitxategiko array-a irakurri */
            JsonArray arrPel = reader.readArray();
            for (int i = 0; i<arrPel.size(); i++) {
               
                JsonObject pel = (JsonObject) arrPel.getJsonObject(i);

       
                Pelikula peliku = new Pelikula(
                        pel.getString("Izena"),
                        pel.getString("Zuzendaria"), 
                        pel.getString("Durazioa"),
                        Integer.parseInt(pel.getString("Adina")),
                        Integer.parseInt(pel.getString("Urtea")));
                
    
                obList.add(peliku);
            }
            return obList;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GestionEnListaEnMemoria.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return null;
    }
        
    public static ObservableList<Pelikula> jsonStreamPeliKargatu(File file) {
        ObservableList<Pelikula> peliObList = FXCollections.observableArrayList();
        JsonParser parser = null;
        try {
            parser = Json.createParser(new FileReader(file));
            
            String izena = null, zuzendari = null, durazioa = null;
            int  adina=0, urtea = 0;
            String key = "";
            String value = "";
            while (parser.hasNext()) {
               
                JsonParser.Event event = parser.next();
                switch (event) {
                    case START_ARRAY:
                    case END_ARRAY:
                    case START_OBJECT:
                        break;
                    case END_OBJECT:
                        /* Objektu berria sortu eta observableList-ean gehitu */
                        Pelikula peli = new Pelikula(izena, zuzendari, durazioa, adina,urtea);
                        peliObList.add(peli);
                        break;
                    case VALUE_FALSE:
                    case VALUE_NULL:
                    case VALUE_TRUE:
                        break;
                    case KEY_NAME:
                        /* Key-aren izena jaso */
                        key = parser.getString();
                        break;
                    case VALUE_STRING:
                    case VALUE_NUMBER:
                        /* Objektuaren balioa hartu, key-aren arabera */
                        value = parser.getString();
                        switch(key) {
                            case "Izena":
                                izena = value;
                                break;
                            case "Zuzendaria":
                                zuzendari = value;
                                break;
                            case "Durazioa":
                                durazioa = value;
                                break;
                            case "Adina":
                                adina = Integer.parseInt(value);
                                break;
                            case "Urtea":
                                urtea = Integer.parseInt(value);
                                break;
                            
                        }
                        break;
                }
            }
            System.out.println("*****************************JSON fitxategia kargatu STREAM erabilita\n*****************************"); // Mezua, stream erabilita datuak kargatzen direla jakiteko
            return peliObList;
        } catch (FileNotFoundException ex) {
            System.err.println("Ez da fitxategia aurkitu.");
        }
        return null;        
    }
    
         
}


