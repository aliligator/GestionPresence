<!DOCTYPE html>
<html>

        <head>
                <title>Appel</title>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
                <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
                <link rel="stylesheet" href="css/faireAppel.css" type="text/css">
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
                                                        <a  style="color:white;" class="nav-link" href="#">Faire appel</a>
                                                </li>
                                                <li class="nav-item ">
                                                        <a  style="color:#d9d9d9;" class="nav-link" href="consult">Consulter les appels</a>
                                                </li>
                                                <li class="nav-item ">
                                                        <a  style="color:#d9d9d9;" class="nav-link" href="cx">Se d&eacute;connecter</a>
                                                </li>
                                        </ul>
                                </div>
                        </nav>
                </div>

                <br>


                <!--form-->
                <form id="formList"  onsubmit="return validerForm()" action="ServletComfirmAppel" method="get" accept-charset="UTF-8">
                        <div class="container">
                                <!--choisir formation ect-->
                                <div class="container" id="affecterCreneau">
                                        <div class="form-row">
                                                <div class=" col-xs-3 col-md-6 col-lg-6">
                                                        <select id="saisirFormation" name="saisirFormation" class="form-control">
                                                                <option value="none" selected>Formation...</option>
                                                        </select>
                                                </div>
                                                <div class="col-xs-3 col-md-6 col-lg-6">
                                                        <select id="saisirCours" name="saisirCours" class="form-control ">
                                                                <option value="none" selected>Cours...</option>
                                                        </select>
                                                </div>
                                        </div>
                                        <div class="form-row">
                                                <div class="col-12 col-sm-6 col-md-6 col-lg-6">
                                                        <select id="saisirAct" name="saisirAct" class="form-control ">
                                                                <option value="none" selected>Activit&eacute;...</option>
                                                                <option value="1">S&eacute;ance</option>
                                                                <option value="2">Examen</option>
                                                        </select>
                                                </div>

                                                <div class="col-12 col-sm-6 col-sm-6 col-md-6 col-lg-6">
                                                        <input type="date" id="date" name="date" class="form-control " />
                                                </div>
                                        </div>
                                        <div class="form-row">
                                                <div class="col-2 col-sm-1 col-sm-1 col-md-1 col-lg-1">
                                                        <label class="form-input-label" for="ti">De:</label>
                                                </div>
                                                <div class="col-4 col-sm-5 col-sm-5 col-md-5 col-lg-5">

                                                        <input type="time" id="time" name="time" class="form-control " />
                                                </div>
                                                <div class="col-2 col-sm-1 col-sm-1 col-md-1 col-lg-1">
                                                        <label class="form-input-label" for="ti">&agrave;:</label>
                                                </div>
                                                <div class="col-4 col-sm-5 col-sm-5 col-md-5 col-lg-5">

                                                        <input type="time" id="timefin" name="timefin" class="form-control " />
                                                </div>
                                        </div>
                                        <div class="form-row">
                                                <div class="col-12 col-sm-12 col-md-12 col-lg-12">
                                                        <select id="saisirGroupe" name="saisirGroupe" class="form-control ">
                                                                <option value="none" id="nongroupe" selected>Groupe...</option>
                                                        </select>
                                                </div>
                                        </div>
                                </div>
                                <br>
                                <!--panel-->
                                <ul class="nav nav-tabs mb-3" id="pills-tab" role="tablist">
                                        <li class="nav-item">
                                                <a class="nav-link active" id="pills-appel-tab" data-toggle="pill" href="#pills-appel" role="tab" aria-controls="pills-home" aria-selected="true">Liste d'appel</a>
                                        </li>
                                        <li class="nav-item">
                                                <a class="nav-link" id="pills-planning-tab" data-toggle="pill" href="#pills-planning" role="tab" aria-controls="pills-profile" aria-selected="false">Planning d'aujourd'hui</a>
                                        </li>
                                </ul>
                                <br>
                                <div class="  tab-content" id="pills-tabContent">
                                        <!--panel faire appel-->
                                        <div class="tab-pane fade show active" id="pills-appel" role="tabpanel" aria-labelledby="pills-home-tab">

                                                <!--table des etudiants-->
                                                <div class="container">
                                                        <div id="portait" class="portait" style="display:none">
                                                                <span class="close" onclick="document.getElementById('portait').style.display = 'none'">&times;</span>
                                                                <img class="portait-content" id="photoid"></div>
                                                        <div class="form-row">
                                                                <div class="table-responsive col-12 col-sm-12 col-md-12 col-lg-12">
                                                                        <table class="justify-content-center table  ">
                                                                                <thead>
                                                                                        <tr>
                                                                                                <th style="width:100px">Pr&eacute;s</th>
                                                                                                <th>Nom</th>
                                                                                                <th>Pr&eacute;nom</th>
                                                                                                <th>Form</th>
                                                                                                <th>Pr&eacute;c</th>
                                                                                                <th>Abs/Abj/R</th>
                                                                                        </tr>
                                                                                </thead>
                                                                                <tbody id="listEtudiant">
                                                                                        <!-----------------js ajouter list etudiant--------------------->
                                                                                </tbody>
                                                                        </table>
                                                                </div>
                                                        </div>
                                                </div>
                                                <br>
                                                <!--partis de la confirmation-->
                                                <div class="container">
                                                        <div class="row justify-content-center">
                                                                <div class="form-check">
                                                                        <input required="required" type="checkbox" class="form-check-input" id="checkSubmit">
                                                                        <label class="form-check-label" for="exampleCheck1">Je confirme la v&eacute;racit&eacute; de mes choix</label>
                                                                </div>
                                                        </div>
                                                        <br>
                                                        <div class="row justify-content-center">
                                                                <input id="ok" class="btn btn-primary" type="submit" value="valider" >
                                                        </div>
                                                </div>

                                        </div>
                                        <!--Planning d'aujourd'hui-->
                                        <div class="tab-pane fade" id="pills-planning" role="tabpanel" aria-labelledby="pills-profile-tab">
                                                <!--table des creneau-->
                                                <div class="container" id="creneauExiste">
                                                        <table id="listCreneau" class=" table table-responsive table-condensed"></table>
                                                </div>
                                        </div>
                                </div>
                        </div>
                </form>


                <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
                <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
                <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
                <script src="js/jsFaireAppel.js"></script>
        </body>

</html>
