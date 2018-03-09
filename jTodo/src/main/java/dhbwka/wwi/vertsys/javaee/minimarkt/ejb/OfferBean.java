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

import dhbwka.wwi.vertsys.javaee.minimarkt.jpa.Offer;
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
public class OfferBean extends EntityBean<Offer, Long>{
    public OfferBean(){
        super(Offer.class);
    }
    
       /**
     * Alle Aufgaben eines Benutzers, nach Fälligkeit sortiert zurückliefern.
     * @param username Benutzername
     * @return Alle Aufgaben des Benutzers
     */
        public List<Offer> findByUsername(String username) {
        return em.createQuery("SELECT t FROM Offer t WHERE t.ersteller.username = :username ORDER BY t.erstelldatum, t.erstellzeit")
                 .setParameter("username", username)
                 .getResultList();
    }
    
        /**
     * Suche nach Angeboten anhand ihrer Bezeichnung, Kategorie und Art.
     * 
     * @param search In der Kurzbeschreibung enthaltener Text (optional)
     * @param category Kategorie (optional)
     * @param art Art (optional)
     * @return Liste mit den gefundenen Angeboten
     */
    public List<Offer> search(String search, Category category, Art art) {
        // Hilfsobjekt zum Bauen des Query
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        
        // SELECT t FROM Offer t
        CriteriaQuery<Offer> query = cb.createQuery(Offer.class);
        Root<Offer> from = query.from(Offer.class);
        query.select(from);

        // ORDER BY erstelldatum
       query.orderBy(cb.asc(from.get("erstelldatum")), cb.desc(from.get("erstellzeit")));
        
        // WHERE t.shortText LIKE :search
        if (search != null && !search.trim().isEmpty()) {
            query.where(cb.like(from.get("titel"), "%" + search + "%"));
        }
        
        // WHERE t.category = :category
        if (category != null) {
            query.where(cb.equal(from.get("category"), category));
        }
        
        // WHERE t.art = :art
        if (art != null) {
            query.where(cb.equal(from.get("art"), art));
        }
        
        return em.createQuery(query).getResultList();
        
    }
    
    

}
