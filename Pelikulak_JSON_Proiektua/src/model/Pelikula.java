/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author DM3-2-12
 */
public class Pelikula {
    
    private final SimpleStringProperty Izena;
    private final SimpleStringProperty Zuzendaria;
    private final SimpleStringProperty dembora;
    private final SimpleIntegerProperty adina;
    private final SimpleIntegerProperty urtea;
    
    public Pelikula(String PIzena, String Pzuzendari, String Ptime, int Padina, int Purtea) { //derrigortuta nago, ezta? public jartzera beste pakete batetik sortuko dudalako?
        this.Izena = new SimpleStringProperty(PIzena);
        this.Zuzendaria = new SimpleStringProperty(Pzuzendari);
        this.dembora = new SimpleStringProperty(Ptime);
        this.adina = new SimpleIntegerProperty(Padina);
        this.urtea = new SimpleIntegerProperty(Purtea);
    }

    public String getIzena() {
        return Izena.get();
    }
    public void setIzena(String fName) {
        Izena.set(fName);
    }
    public String getZuzendaria() {
        return Zuzendaria.get();
    }
    public void setZuzendaria(String fName) {
        Zuzendaria.set(fName);
    }
    public String getDurazioa() {
        return dembora.get();
    }
    public void setDurazioa(String fName) {
        dembora.set(fName);
    }
    
    public int getAdina() {
        return adina.get();
    }
    public void setAdina(int fName) {
        adina.set(fName);
    }
    
    public int getUrtea() {
        return urtea.get();
    }
    public void setUrtea(int fName) {
        urtea.set(fName);
    }
}