/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.minimarkt.web;

import dhbwka.wwi.vertsys.javaee.minimarkt.ejb.CategoryBean;
import dhbwka.wwi.vertsys.javaee.minimarkt.ejb.OfferBean;
import dhbwka.wwi.vertsys.javaee.minimarkt.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.minimarkt.ejb.ValidationBean;
import dhbwka.wwi.vertsys.javaee.minimarkt.jpa.Offer;
import dhbwka.wwi.vertsys.javaee.minimarkt.jpa.Art;
import dhbwka.wwi.vertsys.javaee.minimarkt.jpa.Preisart;
import dhbwka.wwi.vertsys.javaee.minimarkt.jpa.User;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Seite zum Anlegen oder Bearbeiten einer Aufgabe.
 */
@WebServlet(urlPatterns = "/app/offer/*")
public class OfferEditServlet extends HttpServlet {

    @EJB
    OfferBean offerBean;

    @EJB
    CategoryBean categoryBean;

    @EJB
    UserBean userBean;

    @EJB
    ValidationBean validationBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verfügbare Kategorien und Angebotsarten für die Suchfelder ermitteln
        request.setAttribute("categories", this.categoryBean.findAllSorted());
        request.setAttribute("arten", Art.values());
        request.setAttribute("preise", Preisart.values());

//        User user = this.userBean.getCurrentUser();
//        String ersteller = (user.getName() + "\n" + user.getAnschrift() + "\n " + user.getPLZ() + " " + user.getOrt());
//        request.setAttribute("ersteller", ersteller);

        // Zu bearbeitende Anzeige einlesen
        HttpSession session = request.getSession();

        Offer offer = this.getRequestedOffer(request);

        request.setAttribute("edit", offer.getId() != 0);

        if (session.getAttribute("offer_form") == null) {
            // Keine Formulardaten mit fehlerhaften Daten in der Session,
            // daher Formulardaten aus dem Datenbankobjekt übernehmen
            request.setAttribute("offer_form", this.createOfferForm(offer));
        }
       
      if (!this.userBean.getCurrentUser().getUsername().equals(offer.getErsteller().getUsername())) {
            request.setAttribute("readonly", true);
        } 
 

        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/app/offer_edit.jsp").forward(request, response);

        session.removeAttribute("offer_form");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Angeforderte Aktion ausführen
        request.setCharacterEncoding("utf-8");

        Offer offer = this.getRequestedOffer(request);
        
//        if (offer.getErsteller().getUsername() != userBean.getCurrentUser().getUsername()) {
//            response.sendRedirect(request.getRequestURI());
//            return;
//        }

        String action = request.getParameter("action"); 
        
        if (action == null) {
            action = "";
        }

        switch (action) {
            case "save":
                this.saveOffer(request, response);
                break;
            case "delete":
                this.deleteOffer(request, response);
                break;
        }
    }

    /**
     * Aufgerufen in doPost(): Neue oder vorhandene Aufgabe speichern
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void saveOffer(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Formulareingaben prüfen
        List<String> errors = new ArrayList<>();

        String offerCategory = request.getParameter("offer_category");
        Date erstelldatum = new Date(System.currentTimeMillis());
        Time erstellzeit = new Time(System.currentTimeMillis());
        String offerArt = request.getParameter("offer_art");
        String offerTitel = request.getParameter("offer_titel");
        String offerBeschreibung = request.getParameter("offer_beschreibung");
        String offerPreisart = request.getParameter("offer_preisart");
        String offerPreis = request.getParameter("offer_preis");

        Offer offer = this.getRequestedOffer(request);

        if (offerCategory != null && !offerCategory.trim().isEmpty()) {
            try {
                offer.setCategory(this.categoryBean.findById(Long.parseLong(offerCategory)));
            } catch (NumberFormatException ex) {
                // Ungültige oder keine ID mitgegeben
            }
        }
        
        if(erstelldatum != null)
        { offer.setErstelldatum(erstelldatum);    
        }
        else
        {
            errors.add("Das Datum muss dem Format: dd.mm.yyyy entsprechen.");
        }
        
          if(erstellzeit != null)
        { offer.setErstellzeit(erstellzeit);    
        }
        else
        {
            errors.add("Das Format der Zeit ist nicht korrekt.");
        }

        try {
            offer.setArt(Art.valueOf(offerArt));
        } catch (IllegalArgumentException ex) {
            errors.add("Die ausgewählte Verkaufsart ist nicht vorhanden.");
        }

        try {
            offer.setPreisart(Preisart.valueOf(offerPreisart));
        } catch (IllegalArgumentException ex) {
            errors.add("Die ausgewählte Preisart ist nicht vorhanden.");
        }

        offer.setTitel(offerTitel);
        offer.setBeschreibung(offerBeschreibung);

        offerPreis = offerPreis.replace(",", ".");
        double preis = 0.0;
        try {
            preis = Double.parseDouble(offerPreis);
        } catch (Exception e) {
            errors.add("Fehler beim parsen.");
        }
        offer.setPreis(preis);

        this.validationBean.validate(offer, errors);

        // Datensatz speichern
        if (errors.isEmpty()) {
           
               this.offerBean.update(offer);
           
           
        }

        // Weiter zur nächsten Seite
        if (errors.isEmpty()) {
            // Keine Fehler: Startseite aufrufen
            response.sendRedirect(WebUtils.appUrl(request, "/app/offers/"));
        } else {
            // Fehler: Formuler erneut anzeigen
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("offer_form", formValues);

            response.sendRedirect(request.getRequestURI());
        }
    }

    /**
     * Aufgerufen in doPost: Vorhandene Aufgabe löschen
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    //Zur Erweiterung noch verhanden, wird hier nicht benötigt
    private void deleteOffer(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<String> errors = new ArrayList<>();
        // Datensatz löschen
        Offer offer = this.getRequestedOffer(request);
        this.offerBean.delete(offer);

        // Zurück zur Übersicht
        response.sendRedirect(WebUtils.appUrl(request, "/app/offers/"));

        if (!this.userBean.getCurrentUser().getUsername().equals(offer.getErsteller().getUsername())) {
            errors.add("Nur der Ersteller hat die Berechtigung!");
        }

        // Weiter zur nächsten Seite
        if (errors.isEmpty()) {
            // Keine Fehler: Startseite aufrufen
            response.sendRedirect(WebUtils.appUrl(request, "/app/offers/"));
        } else {
            // Fehler: Formuler erneut anzeigen
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("offer_form", formValues);

            response.sendRedirect(request.getRequestURI());
        }
    }

    /**
     * Zu bearbeitende Aufgabe aus der URL ermitteln und zurückgeben. Gibt
     * entweder einen vorhandenen Datensatz oder ein neues, leeres Objekt
     * zurück.
     *
     * @param request HTTP-Anfrage
     * @return Zu bearbeitende Aufgabe
     */
    private Offer getRequestedOffer(HttpServletRequest request) {
        // Zunächst davon ausgehen, dass ein neuer Satz angelegt werden soll
        Offer offer = new Offer();
        offer.setErsteller(this.userBean.getCurrentUser());

        // ID aus der URL herausschneiden
        String offerId = request.getPathInfo();

        if (offerId == null) {
            offerId = "";
        }

        offerId = offerId.substring(1);

        if (offerId.endsWith("/")) {
            offerId = offerId.substring(0, offerId.length() - 1);
        }

        // Versuchen, den Datensatz mit der übergebenen ID zu finden
        try {
            offer = this.offerBean.findById(Long.parseLong(offerId));
        } catch (NumberFormatException ex) {
            // Ungültige oder keine ID in der URL enthalten
        }

        return offer;
    }

    /**
     * Neues FormValues-Objekt erzeugen und mit den Daten eines aus der
     * Datenbank eingelesenen Datensatzes füllen. Dadurch müssen in der JSP
     * keine hässlichen Fallunterscheidungen gemacht werden, ob die Werte im
     * Formular aus der Entity oder aus einer vorherigen Formulareingabe
     * stammen.
     *
     * @param offer die zu bearbeitende Angebot
     * @return Neues, gefülltes FormValues-Objekt
     */
    private FormValues createOfferForm(Offer offer) {
        Map<String, String[]> values = new HashMap<>();

        values.put("offer_ersteller", new String[]{
            offer.getErsteller().getUsername(), offer.getErsteller().getAnschrift(), offer.getErsteller().getPLZ(), offer.getErsteller().getOrt()
        });
        
//       String readonly;
//       if(offer.getErsteller().getUsername() != userBean.getCurrentUser().getUsername())
//       {
//           readonly= "disabled=\"disabled\"";
//       }
//       else
//       {
//           readonly="";
//       }
//        values.put("offer_readonly", new String[]{
//                readonly
//            });

        if (offer.getCategory() != null) {
            values.put("offer_category", new String[]{
                offer.getCategory().toString()
            });
        }

        values.put("offer_erstelldatum", new String[]{
            WebUtils.formatDate(offer.getErstelldatum())
        });

        values.put("offer_erstellzeit", new String[]{
            WebUtils.formatTime(offer.getErstellzeit())
        });

        values.put("offer_art", new String[]{
            offer.getArt().toString()
        });

        values.put("offer_titel", new String[]{
            offer.getTitel()
        });

        values.put("offer_beschreibung", new String[]{
            offer.getBeschreibung()
        });

        values.put("offer_preis", new String[]{
            Double.toString(offer.getPreis())
        });

        values.put("offer_preisart", new String[]{
            offer.getPreisart().toString()
        });

        FormValues formValues = new FormValues();
        formValues.setValues(values);
        return formValues;
    }
}
