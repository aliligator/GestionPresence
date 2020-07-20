/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function l_creneauEtu()
{
        // Objet XMLHttpRequest.
        var xhr = new XMLHttpRequest();

        var date = document.getElementById("datechoisi").value;
        //alert(date);

        // Requête au serveur avec les paramètres éventuels.
        xhr.open("GET", "servletCrenPres" + "?datechoisi=" + date, true);

        // On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
        xhr.onload = function ()
        {
                // Si la requête http s'est bien passée.
                if (xhr.status === 200)
                {
                        // Elément html que l'on va mettre à jour.
                        var TabCreneau = document.getElementById("lCreneauEtu");
                        TabCreneau.innerHTML = null;
                        TabCreneau.insertAdjacentHTML("afterbegin", "<thead><tr><th>Créneau</th><th>Type d'activité</th><th>Groupe</th><th>Heure début</th><th>Heure fin</th><th>Pres</th></tr></thead>")
                        var tab = xhr.responseXML.getElementsByTagName("creneau");
                        for (i = 0; i < tab.length; i++)
                        {
                                //alert("okk");
                                var idcre = tab[i].getElementsByTagName("id")[0].firstChild.nodeValue;
                                var nomcre = tab[i].getElementsByTagName("nom")[0].firstChild.nodeValue;
                                var typecre = tab[i].getElementsByTagName("type")[0].firstChild.nodeValue;
                                var grpcre = tab[i].getElementsByTagName("grp")[0].firstChild.nodeValue;
                                var hdeb = tab[i].getElementsByTagName("h")[0].firstChild.nodeValue;
                                var duree = tab[i].getElementsByTagName("duree")[0].firstChild.nodeValue;

                                //alert(idcre);
                                TabCreneau.insertAdjacentHTML('beforeend', "<tr>" +
                                        "<td id='nom'>" + nomcre + "<input type='hidden' id ='idcre' value=" + idcre + "/></td>" +
                                        "<td>" + typecre + "</td>" +
                                        "<td>" + grpcre + "</td>" +
                                        "<td>" + hdeb + "</td>" +
                                        "<td>" + duree + "</td>" +
                                        "<td><input type='radio' id ='rd_pre' name = 'pres' value='" + idcre + "'/></td>" +
                                        "</tr>");
                        }


                }
        };

        xhr.send();

}

function etrePres()
{
        //alert("function etrePres");
        // Objet XMLHttpRequest.
        var xhr = new XMLHttpRequest();

        var idCreChoisi = document.getElementById("rd_pre").value;

        // Requête au serveur avec les paramètres éventuels.
        xhr.open("GET", "serveltEtuPreAct" + "?idcrechoisi=" + idCreChoisi, true);

        //Si la requête http s'est bien passée.
        xhr.onload = function ()
        {
                // Si la requête http s'est bien passée.
                if (xhr.status === 200)
                {
                        // Elément html que l'on va mettre à jour.
//                        var btn_valider = document.getElementById("ok");
//                        var res = xhr.responseXML.getElementsByTagName("res");
//                        btn_valider.insertAdjacentHTML('afterend',res[0].firstChild.nodeValue);
                        location.reload();
                }
        };
        xhr.send();
}

/**
 * Lancement après le chargement du DOM.
 */
document.addEventListener("DOMContentLoaded", () => {

        document.getElementById("datechoisi").addEventListener("change", l_creneauEtu);
        document.getElementById("ok").addEventListener("click", etrePres);

});

