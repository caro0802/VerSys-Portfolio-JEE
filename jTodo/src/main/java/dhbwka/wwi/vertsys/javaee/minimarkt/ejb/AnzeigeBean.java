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

import dhbwka.wwi.vertsys.javaee.jtodo.minimarkt.Anzeige;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

@Stateless
@RolesAllowed("minimarkt-app-user")
public class AnzeigeBean extends EntityBean<Anzeige, Long>{
    public AnzeigeBean(){
        super(Anzeige.class);
    }
    
}
