/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbwka.wwi.vertsys.javaee.jtodo.jpa;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Ännikän
 */
@Entity
public class Anzeige implements Serializable{
    
    @Id
    @GeneratedValue
    private long id = 0;
    
    @ManyToOne
    @NotNull(message = "Die Anzeige muss einem Benutzer geordnet werden.")
    private User ersteller;
    
    private String titel = "";
    private Date erstelldatum = new Date();
    private Date onlinebis = new Date();
    private Art art = Art.UNBEKANNT;
    
    @Column(precision=7, scale=2)
    private long preis = 0;
    
    private String postleitzahl = "";
    private String ort = "";
    
    @OneToMany(mappedBy="fotos")
    private List<Foto> fotos = new ArrayList<>();
    
    //<editor-fold defaultstate="collapsed" desc="Setter und Getter">
    public void setId(long id) {
        this.id = id;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public void setErstelldatum(Date erstelldatum) {
        this.erstelldatum = erstelldatum;
    }

    public void setOnlinebis(Date onlinebis) {
        this.onlinebis = onlinebis;
    }

    public void setArt(Art art) {
        this.art = art;
    }

    public void setPreis(long preis) {
        this.preis = preis;
    }

    public void setPostleitzahl(String postleitzahl) {
        this.postleitzahl = postleitzahl;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public void setFotos(List<Foto> fotos) {
        this.fotos = fotos;
    }

    public long getId() {
        return id;
    }

    public String getTitel() {
        return titel;
    }

    public Date getErstelldatum() {
        return erstelldatum;
    }

    public Date getOnlinebis() {
        return onlinebis;
    }

    public Art getArt() {
        return art;
    }

    public long getPreis() {
        return preis;
    }

    public String getPostleitzahl() {
        return postleitzahl;
    }

    public String getOrt() {
        return ort;
    }

    public List<Foto> getFotos() {
        return fotos;
    }
    //</editor-fold>
}
