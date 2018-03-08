/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbwka.wwi.vertsys.javaee.minimarkt.jpa;

public enum Art {
    UNBEKANNT, BID, SEARCH;
    
        public String getLabel() {
        switch (this) {
            case UNBEKANNT:
                return "Offen";
            case BID:
                return "Bieten";
            case SEARCH:
               return "Suchen";
            default:
                return this.toString();
        }
    }
}
