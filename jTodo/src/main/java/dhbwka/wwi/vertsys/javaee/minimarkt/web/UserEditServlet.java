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


import dhbwka.wwi.vertsys.javaee.minimarkt.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.minimarkt.ejb.ValidationBean;
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
 * Seite zum Bearbeiten des Users.
 */
@WebServlet(urlPatterns = "/app/user_edit/")
public class UserEditServlet extends HttpServlet{
    
    @EJB
    UserBean userBean;
    

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Zu bearbeitenden User einlesen
        HttpSession session = request.getSession();

        User user = this.userBean.getCurrentUser();
        request.setAttribute("edit", user.getUsername() != null);
                                
        if (session.getAttribute("user_form") == null) {
            // Keine Formulardaten mit fehlerhaften Daten in der Session,
            // daher Formulardaten aus dem Datenbankobjekt übernehmen
            request.setAttribute("user_form", this.createUserForm(user));
        }

        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/app/user_edit.jsp").forward(request, response);

        session.removeAttribute("user_form");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Angeforderte Aktion ausführen
        request.setCharacterEncoding("utf-8");

        String action = request.getParameter("action");

        if (action == null) {
            action = "";
        }

        switch (action) {
            case "save":
                this.saveUser(request, response);
                break;
        }
    }

    /**
     * Aufgerufen in doPost(): Neue Userinformationen speichern
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void saveUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Formulareingaben prüfen
        String userName = request.getParameter("user_name");
        String userAnschrift = request.getParameter("user_anschrift");
        String userPLZ = request.getParameter("user_plz");
        String userOrt = request.getParameter("user_ort");
        String userTelefon = request.getParameter("user_telefon");
        String userEmail = request.getParameter("user_email");

        User user = this.userBean.getCurrentUser();

        user.setName(userName);
        user.setAnschrift(userAnschrift);
        user.setPLZ(userPLZ);
        user.setOrt(userOrt);
        user.setTelefon(userTelefon);
        user.setEmail(userEmail);        
    }



    /**
     * Neues FormValues-Objekt erzeugen und mit den Daten eines aus der
     * Datenbank eingelesenen Datensatzes füllen. Dadurch müssen in der JSP
     * keine hässlichen Fallunterscheidungen gemacht werden, ob die Werte im
     * Formular aus der Entity oder aus einer vorherigen Formulareingabe
     * stammen.
     *
     * @param user Die zu bearbeitende Aufgabe
     * @return Neues, gefülltes FormValues-Objekt
     */
    private FormValues createUserForm(User user) {
        Map<String, String[]> values = new HashMap<>();

        values.put("username", new String[]{
            user.getUsername()
        });
     
        values.put("user_name", new String[]{
            user.getName()
        });

        values.put("user_anschrift", new String[]{
            user.getAnschrift()
        });

        values.put("user_plz", new String[]{
            user.getPLZ()
        });

        values.put("user_ort", new String[]{
            user.getOrt()
        });
        
        values.put("user_telefon", new String[]{
            user.getTelefon()
        });

        values.put("user_email", new String[]{
            user.getEmail()
        });

        FormValues formValues = new FormValues();
        formValues.setValues(values);
        return formValues;
    }

    
}
