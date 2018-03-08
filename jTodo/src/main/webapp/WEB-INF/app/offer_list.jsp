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
        Übersicht
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/offer_list.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/offer/new/"/>">Angebot anlegen</a>
        </div>

        <div class="menuitem">
            <a href="<c:url value="/app/categories/"/>">Kategorien bearbeiten</a>
        </div>
        
        <div class="menuitem">
            <a href="<c:url value="/app/user_edit/"/>">Benutzer bearbeiten</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <%-- Suchfilter --%>
        <form method="GET" class="horizontal" id="search">
            <input type="text" name="search_text" value="${param.search_text}" placeholder="Beschreibung"/>

            <select name="search_category">
                <option value="">Alle Kategorien</option>

                <c:forEach items="${categories}" var="category">
                    <option value="${category.id}" ${param.search_category == category.id ? 'selected' : ''}>
                        <c:out value="${category.name}" />
                    </option>
                </c:forEach>
            </select>

            <select name="search_art">
                <option value="">Alle Angebotsarten</option>

                <c:forEach items="${arten}" var="art">
                    <option value="${art}" ${param.search_art == art ? 'selected' : ''}>
                        <c:out value="${art.label}"/>
                    </option>
                </c:forEach>
            </select>

            <button class="icon-search" type="submit">
                Suchen
            </button>
        </form>

        <%-- Gefundene Angebote --%>
        <c:choose>
            <c:when test="${empty offers}">
                <p>
                    Es wurden keine Angebote gefunden. 🐈
                </p>
            </c:when>
            <c:otherwise>
                <jsp:useBean id="utils" class="dhbwka.wwi.vertsys.javaee.minimarkt.web.WebUtils"/>
                
                <table>
                    <thead>
                        <tr>
                            <th>Bezeichnung</th>
                            <th>Kategorie</th>
                            <th>Benutzer</th>
                            <th>Angebotstyp</th>
                            <th>Preis</th>
                            <th>Preistyp</th>
                            <th>Datum</th>
                        </tr>
                    </thead>
                    <c:forEach items="${offers}" var="offer">
                        <tr>
                            <td>
                                <a href="<c:url value="/app/offer/${offer.id}/"/>">
                                    <c:out value="${offer.titel}"/>
                                </a>
                            </td>
                            <td>
                                <c:out value="${offer.category.name}"/>
                            </td>
                            <td>
                                <c:out value="${offer.ersteller.username}"/>
                            </td>
                            <td>
                                <c:out value="${offer.art.label}"/>
                            </td>
                            <td>
                                <c:out value="${offer.preis}"/>
                            </td>
                            <td>
                                <c:out value="${offer.preisart}"/>
                            </td>
                            <td>
                                <c:out value="${utils.formatDate(offer.erstelldatum)}"/>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
    </jsp:attribute>
</template:base>
