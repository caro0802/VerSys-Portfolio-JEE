<%-- 
    Copyright © 2018 Dennis Schulmeister-Zimolong

    E-Mail: dhbw@windows3.de
    Webseite: https://www.wpvs.de/

    Dieser Quellcode ist lizenziert unter einer
    Creative Commons Namensnennung 4.0 International Lizenz.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
            <c:choose>
            <c:when test="${edit}">
                Angebot bearbeiten
            </c:when>
            <c:otherwise>
                Angebot anlegen
            </c:otherwise>
        </c:choose>
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/offer_edit.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/offers/"/>">Übersicht</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <form method="post" class="stacked">
            <div class="column">
                <%-- CSRF-Token --%>
                <input type="hidden" name="csrf_token" value="${csrf_token}">

                <label for="offer_category">Kategorie:</label>
                <div class="side-by-side">
                    <select name="offer_category">
                        <option value="">Keine Kategorie</option>

                        <c:forEach items="${categories}" var="category">
                            <option value="${category.id}" ${offer_form.values["offer_category"][0] == category.id ? 'selected' : '' } ${readonly ? 'readonly="readonly"' : ''}>
                                <c:out value="${category.name}" />
                            </option>
                        </c:forEach>
                    </select>
                </div>
                
                <label for="offer_art"> 
                Art des Angebots:
                <span class="required">*</span>
                </label>
                <div class="side-by-side">
                    <select name="offer_art">
                        <c:forEach items="${arten}" var="art">
                               <option value="${art}" ${offer_form.values["offer_art"][0] == art ? 'selected' : ''} ${readonly ? 'readonly="readonly"' : ''}>
                               <c:out value="${art.label}"/>
                            </option>
                             </c:forEach>
                    </select>
                </div>

                <label for="offer_titel">
                    Bezeichnung:
                    <span class="required">*</span>
                </label>
                <div class="side-by-side">
                    <input type="text" name="offer_titel" value="${offer_form.values["offer_titel"][0]}" ${readonly ? 'readonly="readonly"' : ''}>
                </div>
                
                <label for="offer_beschreibung"> 
                     Beschreibung: 
                </label>
                <div class="side-by-side">
                    <textarea type="text" name="offer_beschreibung" ${readonly ? 'readonly="readonly"' : ''} ><c:out value="${offer_form.values['offer_beschreibung'][0]}"/></textarea>
                </div>
                
                 <label for="offer_preis"> 
                     Preis:               
                 </label>
                    <div class="side-by-side">
                            <select name="offer_preisart">
                                <c:forEach items="${preise}" var="preisart">
                                    <option value="${preisart}" ${offer_form.values["offer_preisart"][0] == preisart ? 'selected' : ''} ${readonly ? 'readonly="readonly"' : ''} >
                                        <c:out value="${preisart.label}"/>
                                    </option>
                                </c:forEach>
                            </select>                     
                               <input type="text" name="offer_preis" style="width: 100%" value="${offer_form.values["offer_preis"][0]}" ${readonly ? 'readonly="readonly"' : ''} > 
                    </div>

                <%-- Button zum Abschicken --%>
                <div class="side-by-side">
                    <button class="icon-pencil" type="submit" name="action" value="save">
                        Sichern
                    </button>
                    
                    <c:if test="${edit}">
                        <button class="icon-trash" type="submit" name="action" value="delete">
                            Löschen
                        </button>
                    </c:if>
                </div>
            </div>
                
            <h3> Angelegt am:</h3>
            <p> ${offer_form.values["offer_erstelldatum"][0]} ${offer_form.values["offer_erstellzeit"][0]}</p>
            
            <h3> Anbieter:</h3>
            <p> ${offer_form.values["offer_ersteller"][0]}  </p>
            <p> ${offer_form.values["offer_ersteller"][1]}  </p>
            <p> ${offer_form.values["offer_ersteller"][2]}  </p>
            <p> ${offer_form.values["offer_ersteller"][3]}  </p>
            

            <%-- Fehlermeldungen --%>
            <c:if test="${!empty offer_form.errors}">
                <ul class="errors">
                    <c:forEach items="${offer_form.errors}" var="error">
                        <li>${error}</li>
                    </c:forEach>
                </ul>
            </c:if>
        </form>
    </jsp:attribute>
</template:base>
