package dhbwka.wwi.vertsys.javaee.minimarkt.jpa;


import java.io.Serializable;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Offer implements Serializable{
    
    @Id
    @GeneratedValue
    private long id = 0;
    
    @ManyToOne
    @NotNull(message = "Das Angebot muss einem Benutzer geordnet werden.")
    private User ersteller;
    
    @Column(length = 50)
    @NotNull(message = "Das Angebot muss einen Titel haben.")
    @Size(min = 1, max = 50, message = "Die Bezeichnung muss zwischen ein und 50 Zeichen lang sein.")
    private String titel = "";
    
    @ManyToOne
    private Category category;
    
    @Enumerated(EnumType.STRING)
    @NotNull
    private Art art = Art.UNBEKANNT;

    private Date erstelldatum = new Date();
    
    @Column(precision=7, scale=2)
    private double preis = 0;
    
    @Enumerated(EnumType.STRING)
    private Preisart preisart = Preisart.UNBEKANNT;
    
    @Lob
    private String beschreibung = "";
    
    //<editor-fold defaultstate="collapsed" desc="Konstruktoren">
    
    public Offer(){   
    }
    
    public Offer(User ersteller, Category category, String titel, String beschreibung, Date erstelldatum, Art art, Preisart preisart, double preis) {
        this.ersteller = ersteller;
        this.category = category;
        this.titel = titel;
        this.beschreibung = beschreibung;
        this.erstelldatum = erstelldatum;
        this.art = art;
        this.preisart = preisart;
        this.preis = preis;
        
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Setter und Getter">
   public void setId(long id) {
        this.id = id;
    }

    public void setErsteller(User ersteller) {
        this.ersteller = ersteller;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setArt(Art art) {
        this.art = art;
    }

    public void setErstelldatum(Date erstelldatum) {
        this.erstelldatum = erstelldatum;
    }

    public void setPreis(double preis) {
        this.preis = preis;
    }

    public void setPreisart(Preisart preisart) {
        this.preisart = preisart;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }
    
    
    public long getId() {
        return id;
    }

    public User getErsteller() {
        return ersteller;
    }

    public String getTitel() {
        return titel;
    }

    public Category getCategory() {
        return category;
    }

    public Art getArt() {
        return art;
    }

    public Date getErstelldatum() {
        return erstelldatum;
    }

    public double getPreis() {
        return preis;
    }

    public Preisart getPreisart() {
        return preisart;
    }

    public String getBeschreibung() {
        return beschreibung;
    }
    
    //</editor-fold>

    public Object getCreator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}

