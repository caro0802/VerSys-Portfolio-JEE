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

import dhbwka.wwi.vertsys.javaee.minimarkt.ejb.AnzeigeBean;
import dhbwka.wwi.vertsys.javaee.minimarkt.ejb.CategoryBean;
import dhbwka.wwi.vertsys.javaee.minimarkt.jpa.Anzeige;
import dhbwka.wwi.vertsys.javaee.minimarkt.jpa.Art;
import dhbwka.wwi.vertsys.javaee.minimarkt.jpa.Category;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet für die Startseite bzw. jede Seite, die eine Liste der Aufgaben
 * zeigt.
 */
@WebServlet(urlPatterns = {"/app/tasks/"})
public class AnzeigeListServlet extends HttpServlet {

    @EJB
    private CategoryBean categoryBean;
    
    @EJB
    private AnzeigeBean anzeigeBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verfügbare Kategorien und Stati für die Suchfelder ermitteln
        request.setAttribute("categories", this.categoryBean.findAllSorted());
        request.setAttribute("arten", Art.values());

        // Suchparameter aus der URL auslesen
        String searchText = request.getParameter("search_text");
        String searchCategory = request.getParameter("search_category");
        String searchArt = request.getParameter("search_art");

        // Anzuzeigende Anzeigen suchen
        Category category = null;
        Art art = null;

        if (searchCategory != null) {
            try {
                category = this.categoryBean.findById(Long.parseLong(searchCategory));
            } catch (NumberFormatException ex) {
                category = null;
            }
        }

        if (searchArt != null) {
            try {
                art = Art.valueOf(searchArt);
            } catch (IllegalArgumentException ex) {
                art = null;
            }

        }

        List<Anzeige> anzeigen = this.anzeigeBean.search(searchText, category, art);
        request.setAttribute("art", art);

        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/app/anzeige_list.jsp").forward(request, response);
    }
}
