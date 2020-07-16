
function l_creneau()
{

        // Objet XMLHttpRequest.
        var xhr = new XMLHttpRequest();

        var date = document.getElementById("datechoisi").value;
        //alert(date);

        // Requête au serveur avec les paramètres éventuels.
        xhr.open("GET", "servletResProjet" + "?datechoisi=" + date, true);

        // On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
        xhr.onload = function ()
        {
                // Si la requête http s'est bien passée.
                if (xhr.status === 200)
                {
                        // Elément html que l'on va mettre à jour.
                        var TabCreneau = document.getElementById("listCreneau");
                        TabCreneau.innerHTML = null;
                        TabCreneau.insertAdjacentHTML("afterbegin", "<thead><tr><th>Créneau</th><th>Type d'activité</th><th>Groupe</th><th>Date</th><th>Heure début</th><th>Heure fin</th><th>Modifier</th><th>Supprimer</th></tr></thead>")
                        var tab = xhr.responseXML.getElementsByTagName("creneau");
                        for (i = 0; i < tab.length; i++)
                        {
                                //alert("okk");
                                var idcre = tab[i].getElementsByTagName("id")[0].firstChild.nodeValue;
                                var nomcre = tab[i].getElementsByTagName("nom")[0].firstChild.nodeValue;
                                var typecre = tab[i].getElementsByTagName("type")[0].firstChild.nodeValue;
                                var grpcre = tab[i].getElementsByTagName("grp")[0].firstChild.nodeValue;
                                var datecre = tab[i].getElementsByTagName("date")[0].firstChild.nodeValue;
                                var hdeb = tab[i].getElementsByTagName("h")[0].firstChild.nodeValue;
                                var duree = tab[i].getElementsByTagName("duree")[0].firstChild.nodeValue;

                                //alert(idcre);
                                TabCreneau.insertAdjacentHTML('beforeend', "<tr>" +
                                        "<td id='nom'>" + nomcre + "<input type='hidden' id ='idcre' value=" + idcre + "/></td>" +
                                        "<td>" + typecre + "</td>" +
                                        "<td>" + grpcre + "</td>" +
                                        "<td>" + datecre + "</td>" +
                                        "<td>" + hdeb + "</td>" +
                                        "<td>" + duree + "</td>" +
                                        "<td><input type='button' id ='btn_mod' value='modifier' onclick='mod_Creneau(" + idcre + ");l_formation();l_groupe()'/></td>" +
                                        "<td><input type='button' id='btn_sup' value='supprimer' onclick='sup_Creneau(" + idcre + ")'/></td>" +
                                        "</tr>");
                        }


                }
        };

        xhr.send();

}

function sup_Creneau(id)
{
        // Objet XMLHttpRequest.
        var xhr = new XMLHttpRequest();

        // Requête au serveur avec les paramètres éventuels.
        xhr.open("GET", "servletSupProjet" + "?idsup=" + id, true);

        // On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
        xhr.onload = function ()
        {
                // Si la requête http s'est bien passée.
                if (xhr.status === 200)
                {
                        // Elément html que l'on va mettre à jour.
                        window.location.reload();
                        var btnAjout = document.getElementById("ajout");
                        var res = xhr.responseXML.getElementsByTagName("ressup");
                        btnAjout.TabCreneau.insertAdjacentHTML('beforebegin', res);
                        l_creneau();


                }
        };

        xhr.send();

}

function mod_Creneau(id)
{
        //alert("function mod_Creneau");

        // Objet XMLHttpRequest.
        var xhr = new XMLHttpRequest();
        var elt = document.getElementById("ajoutform");
        elt.style.display = "block";
        document.getElementById("ajout").disabled = "false";
        document.getElementById("btn_mod").disabled = "true";

        document.getElementById("btn_vAjout").setAttribute("value", "modifier");
        document.getElementById("idaction").setAttribute("value", id);

        //Requête au serveur avec les paramètres éventuels.
        xhr.open("GET", "servletUpProjet" + "?idup=" + id, true);

        // On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
        xhr.onload = function ()
        {
                // Si la requête http s'est bien passée.
                if (xhr.status === 200)
                {
                        // Elément html que l'on va mettre à jour.
                        var nom = xhr.responseXML.getElementsByTagName("nom")[0].firstChild.nodeValue;
                        //var type = xhr.responseXML.getElementsByTagName("type")[0].firstChild.nodeValue;
                        //var groupe = xhr.responseXML.getElementsByTagName("groupe")[0].firstChild.nodeValue;
                        var date = xhr.responseXML.getElementsByTagName("date")[0].firstChild.nodeValue;
                        var heure = xhr.responseXML.getElementsByTagName("heure")[0].firstChild.nodeValue;
                        var duree = xhr.responseXML.getElementsByTagName("duree")[0].firstChild.nodeValue;
                        document.getElementById("nommod").setAttribute("value", nom);
                        //document.getElementById("typeAct").setAttribute("value",type);
                        //document.getElementById("groupchoi").setAttribute("value",groupe);
                        document.getElementById("iddate").setAttribute("value", date);
                        document.getElementById("idtime").setAttribute("value", heure);
                        document.getElementById("idduree").setAttribute("value", duree);
                }
        };

        xhr.send();

}

function l_groupe() {

//        alert("function l_groupe");
        // Objet XMLHttpRequest.
        var xhr = new XMLHttpRequest();

        var id = document.getElementById("idRes").value;

        // Requête au serveur avec les paramètres éventuels.
        xhr.open("GET", "serListeGrp" + "?idRes=" + id, true);

        // On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
        xhr.onload = function ()
        {
                // Si la requête http s'est bien passée.
                if (xhr.status === 200)
                {
                        // Elément html que l'on va mettre à jour.
                        var SelGroup = document.getElementById("groupchoi");
                        SelGroup.insertAdjacentHTML("beforeend", "");
                        var tab = xhr.responseXML.getElementsByTagName("groupe");
                        for (i = 0; i < tab.length; i++)
                        {
                                var idgrp = tab[i].getElementsByTagName("idgrp")[0].firstChild.nodeValue;
                                var nomgrp = tab[i].getElementsByTagName("nomgrp")[0].firstChild.nodeValue;

                                SelGroup.insertAdjacentHTML('beforeend', "<option value='" + idgrp + "'>&nbsp" + nomgrp + "</option>");
                        }
//                        SelGroup.insertAdjacentHTML("beforeend", "</select>");


                }
        };

        xhr.send();




}

function l_formation() {

//        alert("function l_formation");
        // Objet XMLHttpRequest.
        var xhr = new XMLHttpRequest();

        var id = document.getElementById("idRes").value;

        // Requête au serveur avec les paramètres éventuels.
        xhr.open("GET", "serListeForma" + "?idRes=" + id, true);

        // On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
        xhr.onload = function ()
        {
                // Si la requête http s'est bien passée.
                if (xhr.status === 200)
                {
                        // Elément html que l'on va mettre à jour.
                        var Divfor = document.getElementById("divformation");
                        Divfor.insertAdjacentHTML("beforeend", "Formation : ");
                        var tab = xhr.responseXML.getElementsByTagName("formation");
                        for (i = 0; i < tab.length; i++)
                        {
                                var idfor = tab[i].getElementsByTagName("idfor")[0].firstChild.nodeValue;
                                var nomfor = tab[i].getElementsByTagName("nomfor")[0].firstChild.nodeValue;

                                Divfor.insertAdjacentHTML('beforeend', "<input type='radio' id ='formajout' value='" + idfor + "' required>&nbsp" + nomfor + "</input>");
                        }


                }
        };

        xhr.send();




}

function ajout_Creneau()
{
//        alert("Ajout function");
        var btn = document.getElementById("ajout");
        document.getElementById("nommod").removeAttribute("value");
        document.getElementById("iddate").removeAttribute("value");
        document.getElementById("idtime").removeAttribute("value");
        document.getElementById("idduree").removeAttribute("value");
        var elt = document.getElementById("ajoutform");
        elt.style.display = "block";
        btn.disabled = "true";


}



/**
 * Lancement après le chargement du DOM.
 */
document.addEventListener("DOMContentLoaded", () => {

        document.getElementById("datechoisi").addEventListener("change", l_creneau);
        document.getElementById("ajout").addEventListener("click", l_formation);
        document.getElementById("ajout").addEventListener("click", l_groupe);
        document.getElementById("ajout").addEventListener("click", ajout_Creneau);
        //document.getElementById("btn_mod").addEventListener("click", mod_Creneau);
});
