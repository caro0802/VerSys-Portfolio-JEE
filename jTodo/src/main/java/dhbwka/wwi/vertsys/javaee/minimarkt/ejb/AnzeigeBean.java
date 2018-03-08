/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.minimarkt.ejb;

import dhbwka.wwi.vertsys.javaee.minimarkt.jpa.Anzeige;
import dhbwka.wwi.vertsys.javaee.minimarkt.jpa.Art;
import dhbwka.wwi.vertsys.javaee.minimarkt.jpa.Category;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Stateless
@RolesAllowed("minimarkt-app-user")
public class AnzeigeBean extends EntityBean<Anzeige, Long>{
    public AnzeigeBean(){
        super(Anzeige.class);
    }
    
        /**
     * Suche nach Aufgaben anhand ihrer Bezeichnung, Kategorie und Status.
     * 
     * Anders als in der Vorlesung behandelt, wird die SELECT-Anfrage hier
     * mit der CriteriaBuilder-API vollkommen dynamisch erzeugt.
     * 
     * @param search In der Kurzbeschreibung enthaltener Text (optional)
     * @param category Kategorie (optional)
     * @param art Status (optional)
     * @return Liste mit den gefundenen Aufgaben
     */
    public List<Anzeige> search(String search, Category category, Art art) {
        // Hilfsobjekt zum Bauen des Query
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        
        // SELECT t FROM Task t
        CriteriaQuery<Anzeige> query = cb.createQuery(Anzeige.class);
        Root<Anzeige> from = query.from(Anzeige.class);
        query.select(from);

        // ORDER BY dueDate, dueTime
        query.orderBy(cb.asc(from.get("erstelldatum")));
        
        // WHERE t.shortText LIKE :search
        if (search != null && !search.trim().isEmpty()) {
            query.where(cb.like(from.get("titel"), "%" + search + "%"));
        }
        
        // WHERE t.category = :category
        if (category != null) {
            query.where(cb.equal(from.get("category"), category));
        }
        
        // WHERE t.status = :status
        if (art != null) {
            query.where(cb.equal(from.get("art"), art));
        }
        
        return em.createQuery(query).getResultList();
    }
    
}
