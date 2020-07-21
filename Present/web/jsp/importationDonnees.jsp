<%-- 
    Document   : importationDonnees
    Created on : 25 mars 2020, 14:35:09
    Author     : MIAGE UT1
--%>

<%@ page pageEncoding="UTF-8" %>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<!DOCTYPE html>
<html>
        <head>
                <meta charset="utf-8" />
        <head>
                <title>Importation des données</title>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
                <link rel="icon" type="image/png" href="ressource/images/favicon.ico"/>
                <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
                <link rel="stylesheet" type="text/css" href="css/main.css">
                <link rel="stylesheet" href="css/layoutGlobal.css" type="text/css">
        </head>
</head>
<body>
        <!--navigateur-->
        <!--navigateur-->
        <div class="nav_header">
                <nav class="navbar navbar-expand-lg navbar-light  navbar-fixed-top">
                        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo01" aria-controls="navbarTogglerDemo01" aria-expanded="false" aria-label="Toggle navigation">
                                <span class="navbar-toggler-icon"></span>
                        </button>
                        <div class="collapse navbar-collapse" id="navbarTogglerDemo01">
                                <a  style="color:#d9d9d9;" id="user" class="navbar-brand" href="#" >
                                        <%                                       int idResponsable = (int) request.getSession().getAttribute("idenseig");
                                            String photo = (String) request.getSession().getAttribute("photo");
                                            out.println("<img src='resource/images/portaits/ens/" + photo + "' width='30' height='30' class='d-inline-block align-top' >");
                                            String nomUser = (String) request.getSession().getAttribute("nomuser");
                                            out.println(nomUser);
                                        %>
                                </a>
                                <!--menu-->
                                <ul class="navbar-nav mr-auto ">
                                        <li class="nav-item dropdown active">
                                                <a  style="color:white;" class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                        Gestion des cours
                                                </a>
                                                <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                                                        <a class="dropdown-item" href="vuerespon"> Ajouter activité</a>
                                                        <a class="dropdown-item" href="#" >importation</a>
                                                        <a class="dropdown-item" href="AffecterEtudiant" >Affecter groupe</a>
                                                </div>
                                        </li>
                                        <li class="nav-item ">
                                                <a  style="color:#d9d9d9;" class="nav-link" href="resAppel">Faire appel</a>
                                        </li>
                                        <li class="nav-item ">
                                                <a  style="color:#d9d9d9;" class="nav-link" href="resconsult">Consulter les appels</a>
                                        </li>
                                        <li class="nav-item ">
                                                <a  style="color:#d9d9d9;" class="nav-link" href="vueficheres">Valider fichier</a>
                                        </li>
                                        <li class="nav-item ">
                                                <a  style="color:#d9d9d9;" class="nav-link" href="index.jsp">Se d&eacute;connecter</a>
                                        </li>

                                </ul>

                                <!--                                <div class="nav_logo">
                                                                        <img id=logo_ut1c  hspace="5" alt="ut1c" src="resource/images/layout/logo_grid.gif">
                                                                </div>-->
                </nav>
        </div>
        <br>
        <br>
        <h3 class="text-center">Importation des données</h3>
        <br/>
        <br/>

        <%--  <c:if test="${ !empty fichier }"><p><c:out value="Le fichier ${ fichier } (${ description }) a été uploadé !" /></p></c:if>
        --%>  
        <form method="post" action="bonjour" enctype="multipart/form-data">
                <div class="container" style="margin-left: 40%">
                        <div class="row  ">
                                <div class="form-check">
                                        <input class="form-check-input" type="radio" id="cours" name="import" value="cours" required="required">
                                        <label for="cours">
                                                Ajouter une liste de cours
                                        </label>
                                </div> 
                        </div> 
                        <div class="row  ">
                                <div class="form-check">
                                        <input class="form-check-input"type="radio" id="enseignant" name="import" value="enseignant">
                                        <label for="enseignant">Ajouter une liste d'enseignants      
                                        </label>
                                </div> 
                        </div> 
                        <div class="row  ">
                                <div class="form-check">
                                        <input class="form-check-input" type="radio" id="etudiant" name="import" value="etudiant">
                                        <label for="other">Ajouter une liste d'étudiants  
                                        </label>
                                </div> 
                        </div> 
                        <div class="row  ">
                                <div class="form-check">
                                        <input class="form-check-input" type="radio" id="periode" name="import" value="periode">
                                        <label for="other">Ajouter une liste de périodes  
                                        </label>
                                </div> 
                        </div>
                        <br>
                        <br>
                </div>
                <div class="container">
                        <div class="row  justify-content-center">
                                <div class="col-6 col-sm-6 col-sm-6 col-md-6 col-lg-6">
                                        <label for="fichier">Fichier à envoyer : </label>
                                        <input type="file" name="fichier" id="fichier" required="required"  class="form-control-file form-control"/>
                                </div>
                        </div>
                        <div class="row  justify-content-center">
                                <div class="form-check">
                                        <input class ="btn btn-primary form-check-input"  type="submit" />
                                </div>        
                        </div>
                </div>
        </form>
        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>

</body>
</html>