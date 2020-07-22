<%-- 
    Document   : AffecterEtudiant
    Created on : 2020-7-24, 13:49:27
    Author     : 
--%>

<%@page import="services.ServiceRespSaisir"%>
<%@page import="services.Bdforcours"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Groupe d'etudiants</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="icon" type="image/png" href="ressource/images/favicon.ico"/>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <link rel="stylesheet" href="css/jquery.datetimepicker.min.css">
        <!--<link rel="stylesheet" href="css/choisirgroupe.css">-->
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
                                <a class="dropdown-item" href="vuerespon"> Ajouter activité</a>
                                <a class="dropdown-item" href="importdonnees" >importation</a>
                                <a class="dropdown-item" href="#" >Affecter groupe</a>
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
                            <a  style="color:#d9d9d9;" class="nav-link" href="index.php">Se d&eacute;connecter</a>
                        </li>

                    </ul>

                    <!--                                <div class="nav_logo">
                                                            <img id=logo_ut1c  hspace="5" alt="ut1c" src="resource/images/layout/logo_grid.gif">
                                                    </div>-->
            </nav>
        </div>
        <br><br>
        <h3 class="text-center">Affilier des étudiants à des groupes</h3> <br><br>




        <div class="formetudiant">
            <form action="affecter" method="get">
                <div class="container  ">
                    <div class="row justify-content-start">
                        <div>
                            Nom du Groupe : &nbsp;<select id="saisirgroupe" name="saisirgroupe"><option>--</option></select> 
                            &nbsp; <input  class="btn btn-primary"  type="button" id="afficherajouter" name="afficherajouter" value="Nouveau groupe">
                            <br><br>
                            Liste d'étudiants : <br><br>
                            <div class="tableau">
                                <input type="checkbox" onclick="toggle(this)" /> Tout sélectionner &nbsp;&nbsp;&nbsp;
                                <input    class="btn btn-primary" style="display:inline-table" id="btn_ajouter" type="submit" value="Affilier">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="container  ">
                    <div class="row justify-content-start">
                        <div  class="col-6 col-sm-6 col-md-6 col-lg-6">
                            <table   class="table table-responsive">
                                <thead>
                                    <tr>
                                        <th>Nom</th>
                                        <th>Pr&eacute;nom</th>
                                        <th>Type de formation</th>
                                        <th>Choisir</th>   
                                    </tr>
                                </thead>
                                <tbody id="listEtudiant">
                                    <!-----------------js ajouter list etudiant--------------------->
                                </tbody>
                            </table>
                        </div>
                        </form>
                        <div  class="col-6 col-sm-6 col-md-6 col-lg-6">
                            <div    id="ajouter" style="display:none" class="formajouter">
                                Choisir formation :  &nbsp;<select id="saisirformation" name="saisirformation"><option>--</option></select> <br><br>
                                <form action="ajoutergroupe" method="get">
                                    <select id="saisircours" name="saisircours" style="display : none;"><option>--</option></select>  <br><br>
                                    Nom du Groupe :  &nbsp; <input type="text"  class="form-control" id="ajoutergroupe" name="ajoutergroupe" size="10"> &nbsp; 
                                    <input id="btn_ag"  class="btn btn-primary" type="submit" value="Creer le groupe">
                                </form>
                            </div>
                        </div>
                    </div>
                </div>

                <!--      <div>
                                <br><br>
                          Etudiant choisi : <br><br>
                              <table class="table table-responsive">
                                  <thead>
                                      <tr>
                                          <th>Nom</th>
                                          <th>Pr&eacute;nom</th>                                          
                                      </tr>
                                  </thead>
                                  <tbody id="listEtudiantChoisi">
                                          
                                  </tbody>
                              </table>
                                  
                      </div> -->




                <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
                <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
                <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
                <script src="js/jquery.datetimepicker.full.min.js"></script>
                <script src="js/choisirgroupe.js"></script>
                </body>
                </html>

