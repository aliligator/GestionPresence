<%-- 
    Document   : vueResponsable
    Created on : 21 juillet 2020, 15:41:07
    Author     : 
--%>

<%@page import="org.hibernate.Transaction"%>
<%@page import="bd.*"%>
<%@page import="java.util.Set"%>
<%@page import="org.hibernate.Session"%>
<%@page import="services.ServiceRespSaisir"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

        <head>
                <title>Création d'activité</title>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <link rel="icon" type="image/png" href="ressource/images/favicon.ico"/>
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
                <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
                <link rel="stylesheet" type="text/css" href="css/main.css">
                <link rel="stylesheet" href="css/layoutGlobal.css" type="text/css">
        </head>
        <body>
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
                                                                <a class="dropdown-item" href="#"> Ajouter activité</a>
                                                                <a class="dropdown-item" href="importdonnees" >Importation</a>
                                                                <a class="dropdown-item" href="affectergroupe" >Affecter groupe</a>
                                                        </div>
                                                </li>
                                                <li class="nav-item ">
                                                        <a  style="color:#d9d9d9;" class="nav-link" href="resappel">Faire appel</a>
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
                <h3 class="text-center">Création de créneau</h3>
                <br/>
                <br/>

                <!--affichier les créneaus existes pour consulter/modifier/supprimer-->
                <div class="container">
                        <div class="row justify-content-center">
                                <div class="col-6 col-sm-6 col-sm-6 col-md-6 col-lg-6">
                                        Choisissez la date : <input type="date" id="datechoisi" name="date" class="form-control " />
                                </div>
                        </div>
                        <br>
                        <!--table des creneau-->
                        <div class="row justify-content-center">
                                <div class="col-10 col-sm-10 col-sm-10 col-md-10 col-lg-10 table-responsive">
                                        <table id="listCreneau" class=" table  table-condensed">
                                        </table>
                                </div>
                        </div>
                        <div class="row justify-content-center">
                                <div class="col-6 col-sm-6 col-sm-6 col-md-6 col-lg-6">
                                        <input class="btn btn-primary" id="ajout" type="button" value ="Ajouter un créneau" required class="form-control "/>
                                </div>
                        </div>
                </div>
                <br/>
                <br/>
                <!--form pour ajouter un créneau-->
                <form id="ajoutform" class="displayfor" method="post" action="servletAjCreneau">
                        <div class="container">
                                <input  type="hidden" id ="idaction" name ="idaction" value =""/>
                                <div class="row justify-content-center">
                                        <div id="divnom" class="col-6 col-sm-6 col-sm-6 col-md-6 col-lg-6">
                                                Nom : <input type="text" id="nommod" name="nomajout" class="form-control " value ="" required/>
                                                </br>
                                        </div>
                                </div>
                                <div class="row justify-content-center">
                                        <div id="divty" class="col-6 col-sm-6 col-sm-6 col-md-6 col-lg-6">
                                                Type d'activité : <select id="typeAct" name="typeAct" class="form-control " required >
                                                        <option value ="projet">projet</option>
                                                        <option value ="conference">conférence</option>
                                                </select>
                                                </br>
                                        </div>
                                </div>
                                <div class="row justify-content-center">
                                        <div id="divformation" class="col-6 col-sm-6 col-sm-6 col-md-6 col-lg-6">
                                        </div>
                                </div>
                                <br>
                                <div class="row justify-content-center">
                                        <div id="divgroup" class="col-6 col-sm-6 col-sm-6 col-md-6 col-lg-6">
                                                Groupe : <select id='groupchoi' name='groupchoi' class='form-control' required>

                                                </select>
                                        </div>
                                </div>  

                                <div class="row justify-content-center">
                                        <div id="divdate" class="col-6 col-sm-6 col-sm-6 col-md-6 col-lg-6">
                                                </br>
                                                Date : <input type="date" id = "iddate" name="dateajout" class="form-control " required/>
                                                </br>
                                        </div>
                                </div>  
                                <div class="row justify-content-center">
                                        <div id="divheure" class="col-6 col-sm-6 col-sm-6 col-md-6 col-lg-6">
                                                Heure de début : <input type='time' id="idtime" name='hajout' class="form-control " required/>
                                                </br>
                                        </div>
                                </div>  
                                <div class="row justify-content-center">
                                        <div id="divfin" class="col-6 col-sm-6 col-sm-6 col-md-6 col-lg-6">
                                                Heure de fin : <input type ='time' id="idduree" name='durajout' class="form-control " required/>
                                                </br>
                                        </div>
                                </div>  
                                <!--partis de la confirmation-->
                                <div class="row justify-content-center">
                                        <div class="form-check">
                                                <input required="required" type="checkbox" class="form-check-input" id="exampleCheck1">
                                                <label class="form-check-label" for="exampleCheck1">Je confirme la v&eacute;racit&eacute; de mes choix</label>
                                        </div>
                                </div>
                                <br/>
                                <div class="row justify-content-center">
                                        <input class="btn btn-primary" id ="btn_vAjout" type="submit" value="valider" >
                                </div>
                        </div>
                </form>       
                <div class="container">
                        <div>${msg_info}</div>
                </div>
                <%
                    out.println("<input type='hidden' id='idRes' value = '" + idResponsable + "'/>");
                %>
                <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
                <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
                <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
                <script src="js/responsable.js"></script>
        </body>

</html>
