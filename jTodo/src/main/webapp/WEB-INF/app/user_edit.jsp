<%-- 
    Document   : user_edit
    Created on : Mar 8, 2018, 1:33:52 PM
    Author     : D067642
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
    <template:base>
    <jsp:attribute name="title">
       Benutzer bearbeiten
    </jsp:attribute> 

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/user_edit.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/offers"/>">Übersicht</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <form method="post" class="stacked">
            <div class="column">
                <%-- CSRF-Token --%>
                <input type="hidden" name="csrf_token" value="${csrf_token}">
                
                <h2> Passwort ändern </h2>
                
                <%-- Eingabefelder --%>
                <label for="user_username"> Benutzername:</label>
                <div class="side-by-side">
                    <input type="text" name="user_username" value="${user_form.values["user_username"][0]}" readonly="readonly">
                </div>
                
                   <label for="user_password"> 
                    Altes Passwort:
                   <span class="required">*</span>
                   </label>
                <div class="side-by-side">
                    <input type="text" name="user_password" value="******" readonly="readonly">
                </div>
                
                <label for="user_password1"> 
                    Neues Passwort:
                   <span class="required">*</span>
                   </label>
                <div class="side-by-side">
                    <input type="text" name="user_password1"  readonly="readonly">
                </div>
                
                <label for="user_password2"> 
                    Neues Passwort (wdh.):
                   <span class="required">*</span>
                   </label>
                <div class="side-by-side">
                    <input type="text" name="user_password2" readonly="readonly">
                </div>
                
                <h2> Anschrift </h2>
                
                <label for="user_name">
                    Vor- und Nachname: 
                    <span class="required">*</span>
                </label>
                <div class="side-by-side">
                    <input type="text" name="user_name" value="${user_form.values["user_name"][0]}">
                </div>
                
                <label for="user_anschrift">
                    Straße und Hausnummer: 
                    <span class="required">*</span>
                </label>
                <div class="side-by-side">
                    <input type="text" name="user_anschrift" value="${user_form.values["user_anschrift"][0]}">
                </div>
                
                <label for="user_ort">
                    Postleitzahl und Ort: 
                    <span class="required">*</span>
                </label>
                <div class="side-by-side">
                    <input type="text" name="user_plz" value="${user_form.values["user_plz"][0]}">
                    <input type="text" name="user_ort" value="${user_form.values["user_ort"][0]}">
                </div>

                <h2> Kontaktdaten </h2>
                
                <label for="user_telefon">
                    Telefon: 
                    <span class="required">*</span>
                </label>
                <div class="side-by-side">
                    <input type="text" name="user_telefon" value="${user_form.values["user_telefon"][0]}">
                </div>
                
                
                <label for="user_email">
                    Email: 
                    <span class="required">*</span>
                </label>
                <div class="side-by-side">
                    <input type="text" name="user_email" value="${user_form.values["user_email"][0]}">
                </div>
                
                <%-- Button zum Abschicken --%>
                <div class="side-by-side">
                    <button class="icon-pencil" type="submit" name="action" value="save">
                        Sichern
                    </button>

                </div>
            </div>

            <%-- Fehlermeldungen --%>
            <c:if test="${!empty user_form.errors}">
                <ul class="errors">
                    <c:forEach items="${user_form.errors}" var="error">
                        <li>${error}</li>
                    </c:forEach>
                </ul>
            </c:if>
        </form>
    </jsp:attribute>
</template:base>
</html>



