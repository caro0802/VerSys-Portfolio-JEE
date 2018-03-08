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
            <a href="<c:url value="/app/offer/"/>">Übersicht</a>
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
                            <option value="${category.id}" ${offer_form.values["offer_category"][0] == category.id ? 'selected' : ''}>
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
                        <option value="${offer_form.values["offer_art"][0]}"> Bieten </option>
                        <option value="${offer_form.values["offer_art"][0]}"> Suchen </option>
                    </select>
                </div>

                <label for="offer_title">
                    Bezeichnung:
                    <span class="required">*</span>
                </label>
                <div class="side-by-side">
                    <input type="text" name="offer_title" value="${offer_form.values["offer_title"][0]}">
                </div>
                
                 <label for="offer_description"> Beschreibung: </label>
                <div class="side-by-side">
                    <input type="text" name="offer_description" value="${offer_form.values["offer_description"][0]}">
                </div>
                
                 <label for="offer_price"> Preis:
                    </label>
                    <table>
                        <tr>
                            <td>
                             <div class="side-by-side">
                             <select name="offer_art">
                             <option value=""> Verhandlungsbasis </option>
                             <option value=""> Festpreis </option>
                             </select>    
                            </td>
                            <td>
                               <input type="text" name="offer_price" value="${signup_form.values["offer_price"][0]}" style="width: 100%"> 
                            </td>
                        </tr>
                    </table>

                <%-- Button zum Abschicken --%>
                <div class="side-by-side">
                    <button class="icon-pencil" type="submit" name="action" value="save">
                        Sichern
                    </button>
            </div>

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