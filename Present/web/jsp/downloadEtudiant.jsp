<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
        <head>
                <title>DownloadFichierIndividuel</title>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
                <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
                <link rel="stylesheet" type="text/css" href="css/main.css">
                <link rel="stylesheet" type="text/css" href="css/layoutGlobal.css">
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
                                                <%                                                int idetu = (int) request.getSession().getAttribute("idetu");
                                                    String photo = (String) request.getSession().getAttribute("photo");
                                                    out.println("<img src='resource/images/portaits/etu/" + photo + "' width='30' height='30' class='d-inline-block align-top' >");

                                                    String nomUser = (String) request.getSession().getAttribute("nomuser");
                                                    out.println(nomUser);
                                                %>
                                        </a>
                                        <!--menu-->
                                        <ul class="navbar-nav mr-auto">
                                                <li class="nav-item">
                                                        <a  style="color:#d9d9d9;" class="nav-link" href="vueetu">Déclarer présence</a>
                                                </li>
                                                <li class="nav-item dropdown active">
                                                        <a  style="color:white;" class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                                Fiche d'émargement
                                                        </a>
                                                        <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                                                                <a class="dropdown-item" href="downloadEtudiant"> Télécharger</a>
                                                                <a class="dropdown-item" href="vueupetu">Déposer</a>
                                                                <a class="dropdown-item" href="consuetu">Consulter</a>
                                                        </div>
                                                </li>
                                                <li class="nav-item">
                                                        <a  style="color:#d9d9d9;" class="nav-link" href="connexion.jsp">Se d&eacute;connecter</a>
                                                </li>
                                        </ul>
                                </div>
                        </nav>
                </div>
                <br>
                <div class="container ">
                        <!--choisir date-->    
                        <div class="row justify-content-center">
                                <h3 class="text-center">T&eacute;l&eacute;chargez votre fichier individuel</h3>
                        </div >
                        <br/>
                        <br/>
                        <div class="row justify-content-center">
                                <div class="col-12 col-sm-3 col-sm-3 col-md-3 col-lg-3 text-right">
                                        Choisissez le mois : 
                                </div >
                                <div class="col-6 col-sm-6 col-sm-6 col-md-6 col-lg-6">
                                        <input type="month" id="moischoisi" name="moischoisi" class="form-control "/>
                                </div >
                                <div class="col-6 col-sm-3 col-sm-3 col-md-3 col-lg-3">
                                        <button   id="btnSubmit" class="btn btn-primary" onclick="getFiche()"> G&eacute;n&eacute;rer  </button>
                                </div >
                        </div>
                        <br>
                        <div class="row justify-content-center">
                                <div   id="zoneurl" class="col-6 col-sm-6 col-sm-6 col-md-6 col-lg-6">

                                </div >
                        </div>
                </div>

                <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
                <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
                <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
                <script src="js/jsDownloadEtu.js"></script>
                        <span id="etuid" style="display : none"><%
                    out.println(idetu);%></span> 
        </body>
</html>