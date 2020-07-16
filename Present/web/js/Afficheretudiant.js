$(function () {
        // Objet XMLHttpRequest.
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "consultapp");
        xhr.onload = function () {
                if (xhr.status === 200) {
                        var lcours = xhr.responseXML.getElementsByTagName("cours");
                        var lid = xhr.responseXML.getElementsByTagName("id");
                        // On construit les 'options' de notre liste déroulante.
                        var sel_cours = document.getElementById("saisirCours");
                        for (var i = 0; i < lcours.length; i++) {
                                var ab = lcours[i].firstChild.nodeValue;
                                var id = lid[i].firstChild.nodeValue;
                                sel_cours.insertAdjacentHTML("beforeend", "<option value=" + id + ">" + ab + " </option>");
                        }
                        // Elément html que l'on va mettre à jour.



                        //      for (i = 0; i < listecours.length; i++) {
                        //                var idC = listecours[i].getElementsByTagName("id")[0].firstChild.nodeValue;
                        //               var nomC = listecours[i].getElementsByTagName("nom")[0].firstChild.nodeValue;
                        //               var typeC = listecours[i].getElementsByTagName("type")[0].firstChild.nodeValue;
                        //                sel_cours.insertAdjacentHTML("beforeend", "<option value=" + idC + ">" + nomC + " " + typeC + "</option>");
                        //        }
                }
        };

        // Requête au serveur avec les paramètres éventuels.
        xhr.send(null);

});








function presence() {
        var xhr = new XMLHttpRequest();
        var cours = document.getElementById("saisirCours").value;
        var datedeb = document.getElementById("start").value;
        var datefin = document.getElementById("end").value;
        xhr.open("GET", "servletetudiantpresence?idcours=" + cours + "&datedebut=" + datedeb + "&datefin=" + datefin);
        var nav = document.getElementById("listEtudiant");
        nav.innerHTML = "";
        xhr.onload = function () {
                if (xhr.status === 200) {
                        //user
                        var listepres = xhr.responseXML.getElementsByTagName("etudiant");
                        for (i = 0; i < listepres.length; i++) {
                                var dateC = listepres[i].getElementsByTagName("DateC")[0].firstChild.nodeValue;
                                var NomE = listepres[i].getElementsByTagName("NomE")[0].firstChild.nodeValue;
                                var TypeE = listepres[i].getElementsByTagName("TypeE")[0].firstChild.nodeValue;
                                var PrenomE = listepres[i].getElementsByTagName("PrenomE")[0].firstChild.nodeValue;
                                var Presence = listepres[i].getElementsByTagName("Presence")[0].firstChild.nodeValue;

                                nav.insertAdjacentHTML("beforeend", "<tr><td>" + NomE + "</td> <td>" + PrenomE + "</td> <td>" + TypeE + "</td> <td>" + Presence + "</td> <td>" + dateC + "</td> <td></tr>");
                        }
                }
        };
        // Envoie de la requÃªte.
        xhr.send();


}
;


/**
 * Lancement aprÃ¨s le chargement du DOM.
 */
document.addEventListener("DOMContentLoaded", () => {
        document.getElementById("btn_ok").addEventListener("click", presence);
});

