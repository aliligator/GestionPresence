<%-- 
    Document   : ConsulterAppel
    Created on : 2020-7-20, 12:27:44
    Author     : 
--%>

<%@page import="services.Bdforcours"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
        <head>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <title>Consulter des Appels</title>
                <meta charset="UTF-8">
                 <link rel="icon" type="image/png" href="ressource/images/favicon.ico"/>
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
                <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
                <link rel="stylesheet" href="css/jquery.datetimepicker.min.css">
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
                                                <li class="nav-item active">
                                                        <a  style="color:#d9d9d9;" class="nav-link" href="vueenseig">Faire appel</a>
                                                </li>
                                                <li class="nav-item ">
                                                        <a  style="color:white;" class="nav-link" href="#">Consulter les appels</a>
                                                </li>
                                                <li class="nav-item ">
                                                        <a  style="color:#d9d9d9;" class="nav-link" href="index.jsp">Se d&eacute;connecter</a>
                                                </li>
                                        </ul>
                                </div>
                        </nav>
                </div>
                <br>
                <br>
                <div class="container ">                       
                        <div class="row justify-content-center">
                                <h3 class="text-center">Liste d'appel</h3>
                        </div >
                        <br/>
                        <div class="row justify-content-center">
                                <div class="col-xs-3 col-md-8 col-lg-8">
                                        <select id="saisirCours" class="form-control ">
                                                <option>Cours...</option>
                                        </select>
                                </div>
                        </div>  
                        <div class="row justify-content-center">
                                <div class="col-xs-3 col-md-1 col-lg-1 ">
                                        <label for="start">Date début</label>
                                </div>    
                                <div class="col-xs-3 col-md-3 col-lg-3 ">
                                        <input type="date" id="start"  class="form-control ">
                                </div>    
                                <div class="col-xs-3 col-md-1 col-lg-1 ">
                                        <label for="end">Date fin</label>
                                </div>    
                                <div class="col-xs-3 col-md-3 col-lg-3 ">
                                        <input type="date" id="end" class="form-control ">
                                </div>
                        </div>  

                        <br>
                        <div class="row justify-content-center">
                                <input id="btn_ok" type="button" class="btn-primary btn" value="rechercher">
                        </div>
                        <br>

                        <div class="form-row">
                                <div class="table-responsive col-12 col-sm-12 col-md-12 col-lg-12">
                                        <table class="justify-content-center table  ">
                                                <thead>
                                                        <tr>

                                                                <th>Nom</th>
                                                                <th>Pr&eacute;nom</th>
                                                                <th>Form.</th>
                                                                <th>Pr&eacute;c.</th>
                                                                <th>Date</th>
                                                        </tr>
                                                </thead>
                                                <tbody id="listEtudiant">
                                                        <!-----------------js ajouter list etudiant--------------------->
                                                </tbody>
                                        </table>
                                </div>		
                        </div>
                </div>


                <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
                <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
                <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
                <script src="js/jquery.datetimepicker.full.min.js"></script>
                <script src="js/Afficheretudiant.js"></script>


        </body>
</html>

