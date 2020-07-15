<%-- 
    Document   : FicheResponsable
    Created on : 28 mars 2020, 18:23:31
    Author     : yuxuan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>

        <head>
                <title>Création d'activité</title>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
                <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
                <link rel="stylesheet" type="text/css" href="css/main.css">
                <link rel="stylesheet" href="css/layoutGlobal.css" type="text/css">
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
                                                <li class="nav-item dropdown ">
                                                        <a  style="color:#d9d9d9;" class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                                Gestion des cours
                                                        </a>
                                                        <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                                                                <a class="dropdown-item" href="vuerespon"> Ajouter activité</a>
                                                                <a class="dropdown-item" href="importationDonnees" >importation</a>
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
                                                        <a  style="color:white;" class="nav-link" href="#">Valider fichier</a>
                                                </li>
                                                <li class="nav-item ">
                                                        <a  style="color:#d9d9d9;" class="nav-link" href="cx">Se d&eacute;connecter</a>
                                                </li>

                                        </ul>

                                        <!--                                <div class="nav_logo">
                                                                                <img id=logo_ut1c  hspace="5" alt="ut1c" src="resource/images/layout/logo_grid.gif">
                                                                        </div>-->
                        </nav>
                </div>
                <br>
                <br>
                <h3 class="text-center">Valider les fichiers individuels</h3>
                <br/>
                <br/>

                <!--affichier les créneaus existes pour consulter/modifier/supprimer-->
                <div class="container">
                        <div class="row justify-content-center">
                                <div class="col-6 col-sm-6 col-sm-6 col-md-6 col-lg-6">
                                        Choisissez mois : <input type="month" id="mois" name="mois" class="form-control "/>
                                </div>
                        </div>
                        <br>
                        <!--table des creneau-->
                        <form action ="servletUpLoadBdFi" enctype="multipart/form-data" method="post" >
                                <div class="row justify-content-center">
                                        <div class="col-10 col-sm-10 col-sm-10 col-md-10 col-lg-10 table-responsive">
                                                <table id="listfichier" class=" table  table-condensed">

                                                </table>
                                        </div>
                                </div>
                                <div class="row">
                                        <hr style="border-top:1px dashed #987cb9;" width="100%" color="#987cb9" size=1>
                                </div>
                                <div class="row justify-content-center">
                                        <div class="col-6 col-sm-6 col-sm-6 col-md-6 col-lg-6">
                                                <input class="form-control-file form-control" type="file" id="filedeposer" name="filedeposer" id="file" required/>
                                        </div>
                                        <input class= "btn btn-primary" type="submit" id="btn_vali" value="déposer fichier validé" onclick="deleteFi()" disabled/>
                                </div>
                        </form>
                </div>        

                <%
                    out.println("<input type='hidden' id='idRes' value = '" + idResponsable + "'/>");
                %>
                <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
                <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
                <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
                <script src="js/consulterRes.js"></script>
        </body>

</html>

