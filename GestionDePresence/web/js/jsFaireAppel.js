/**
 * onload Initialiser la page d'appel.
 * @returns {undefined}
 */
$(function () {
        var xhr = new XMLHttpRequest();
        var load = 1;
        xhr.open("GET", "servletAppel?load=" + load);
        xhr.onload = function () {
                if (xhr.status === 200) {

                        //formation
                        var selectFormation = document.getElementById("saisirFormation");
                        var tabFormation = xhr.responseXML.getElementsByTagName("formation");
                        for (i = 0; i < tabFormation.length; i++) {
                                var idF = tabFormation[i].getElementsByTagName("id")[0].firstChild.nodeValue;
                                var nomF = tabFormation[i].getElementsByTagName("nom")[0].firstChild.nodeValue;
                                selectFormation.insertAdjacentHTML("beforeend", "<option value=" + idF + ">" + nomF + "</option>");
                        }
                        //cours
                        var selectCours = document.getElementById("saisirCours");
                        var tabCours = xhr.responseXML.getElementsByTagName("cours");
                        for (i = 0; i < tabCours.length; i++) {
                                var idC = tabCours[i].getElementsByTagName("id")[0].firstChild.nodeValue;
                                var nomC = tabCours[i].getElementsByTagName("nom")[0].firstChild.nodeValue;

                                selectCours.insertAdjacentHTML("beforeend", "<option value=" + idC + ">" + nomC + "</option>");
                        }
                        //groupe
                        var saisirGroup = document.getElementById("saisirGroupe");
                        var tabGroup = xhr.responseXML.getElementsByTagName("groupe");
                        for (i = 0; i < tabGroup.length; i++) {
                                var idG = tabGroup[i].getElementsByTagName("id")[0].firstChild.nodeValue;
                                var libG = tabGroup[i].getElementsByTagName("lib")[0].firstChild.nodeValue;
                                saisirGroup.insertAdjacentHTML("beforeend", "<option value=" + idG + ">" + libG + "</option>");
                        }
                        //date  et heure
                        document.getElementById("date").value = getNowDate();
                        changeDate();
                        document.getElementById("time").value = getNowTimeSeg(0);
                        document.getElementById("timefin").value = getNowTimeSeg(90);
                }
        };
        // Envoie de la requête.
        xhr.send();
});
/**
 * Charger liste etudiants par formation.
 * @returns {undefined}
 */
function changeFormation() {
        var xhr = new XMLHttpRequest();
        var idF = document.getElementById("saisirFormation").value;
        var idC = document.getElementById("saisirCours").value;
        //remettre liste
        xhr.open("GET", "servletAppel?formation=" + idF + "&groupe=none&cours=" + idC);
        xhr.onload = function () {
                if (xhr.status === 200) {
                        putEtudiant(xhr);
                }
        };
        uncocherConfirmation();
// Envoie de la requête.
        xhr.send();
        //quand change formation le liste groupe s'initialise
        var idG = document.getElementById("nongroupe");
        idG.selected = true;
}

/**
 * Charger liste etudiants par groupe.
 * @returns {undefined}
 */
function changeGroupe() {
        var xhr = new XMLHttpRequest();
        var idG = document.getElementById("saisirGroupe").value;
        var idC = document.getElementById("saisirCours").value;
        xhr.open("GET", "servletAppel?groupe=" + idG + "&formation=none&cours=" + idC);
        xhr.onload = function () {
                if (xhr.status === 200) {
                        putEtudiant(xhr);
                }
        };
        uncocherConfirmation();
// Envoie de la requête.
        xhr.send();
}
/**
 * Pour afficher les last présence d'un cours.
 * @returns {undefined}
 */
function changeCours() {
        var xhr = new XMLHttpRequest();
        var idC = document.getElementById("saisirCours").value;
        xhr.open("GET", "servletAppel?cours=" + idC + "&formation=none&groupe=none");
        xhr.onload = function () {
                if (xhr.status === 200) {
                        putEtudiant(xhr);
                }
        };
        uncocherConfirmation();
// Envoie de la requête.
        xhr.send();
}

/**
 * afficher les creneaux de ce jour.
 * @returns {undefined}
 */
function changeDate() {
        // Objet XMLHttpRequest.
        var xhr = new XMLHttpRequest();
        var date = document.getElementById("date").value;
        // Requête au serveur avec les paramètres éventuels.
        xhr.open("GET", "servletEnsCre" + "?date=" + date, true);
        // On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
        xhr.onload = function ()
        {
                // Si la requête http s'est bien passée.
                if (xhr.status === 200)
                {
                        // Elément html que l'on va mettre à jour.
                        var TabCreneau = document.getElementById("listCreneau");
                        TabCreneau.innerHTML = null;
                        TabCreneau.insertAdjacentHTML("afterbegin", "\
                                                                                                        <thead>\n\
                                                                                                                <tr>\n\
                                                                                                                        <th>Cours</th>\n\
                                                                                                                        <th>Type d'activit&eacute;</th>\n\
                                                                                                                        <th>Enseignant</th>\n\
                                                                                                                         <th>Groupe</th>\n\
                                                                                                                        <th>Heure d&eacute;but</th>\n\
                                                                                                                        <th>Heure fin</th>\n\
                                                                                                                </tr>\n\
                                                                                                        </thead>");
                        var tab = xhr.responseXML.getElementsByTagName("creneau");
                        for (i = 0; i < tab.length; i++)
                        {
                                //alert("okk");
                                var idcre = tab[i].getElementsByTagName("id")[0].firstChild.nodeValue;
                                var nomcre = tab[i].getElementsByTagName("nom")[0].firstChild.nodeValue;
                                var typecre = tab[i].getElementsByTagName("type")[0].firstChild.nodeValue;
                                var ens = tab[i].getElementsByTagName("ens")[0].firstChild.nodeValue;
                                var grpcre = tab[i].getElementsByTagName("grp")[0].firstChild.nodeValue;
                                var hdeb = tab[i].getElementsByTagName("h")[0].firstChild.nodeValue;
                                var duree = tab[i].getElementsByTagName("duree")[0].firstChild.nodeValue;
                                TabCreneau.insertAdjacentHTML('beforeend', "<tr>" +
                                        "<td id='nom'>" + nomcre + "<input type='hidden' id ='idcre' value=" + idcre + "/></td>" +
                                        "<td>" + typecre + "</td>" +
                                        "<td>" + ens + "</td>" +
                                        "<td>" + grpcre + "</td>" +
                                        "<td>" + hdeb + "</td>" +
                                        "<td>" + duree + "</td>" +
                                        "</tr>");
                        }
                }
        };
        uncocherConfirmation();
        xhr.send();


}

/**
 * charger liste etudiants,html  Table id = listEtudiant.
 * @param {type} xhr     request à demender la liste etudiant <etudiant>
 * @returns {undefined}
 */
function putEtudiant(xhr) {
        var listEtudiant = document.getElementById("listEtudiant");
        //header de la table
        listEtudiant.innerHTML = "";
        //recuperer la liste etudiants
        var tabEtudiant = xhr.responseXML.getElementsByTagName("etudiant");
        for (i = 0; i < tabEtudiant.length; i++) {
                //get les attributs d'eudiant
                var id = tabEtudiant[i].getElementsByTagName("id")[0].firstChild.nodeValue;
                var nom = tabEtudiant[i].getElementsByTagName("nom")[0].firstChild.nodeValue;
                var prenom = tabEtudiant[i].getElementsByTagName("prenom")[0].firstChild.nodeValue;
                var tyEtu = tabEtudiant[i].getElementsByTagName("typeetu")[0].firstChild.nodeValue;
                var lastpre = tabEtudiant[i].getElementsByTagName("lastpre")[0].firstChild.nodeValue;
                var nbpre = tabEtudiant[i].getElementsByTagName("nbpre")[0].firstChild.nodeValue;
                //alimenter html
                var row = "<tr class='table-success' id=etu" + id + " >\n\
                                        <td class=colPresence> \n\
                                                <select class='form-control' id=etat" + id + " name='preEtu[]' onchange=' faireAppel(id)' >\n\
                                                        <option selected value=1>P</option>\n\
                                                        <option value=2>Absent</option>\n\
                                                        <option value=3>Abj</option>\n\
                                                        <option value=4>R</option>\n\
                                                </select>\n\
                                        </td>\n\
                                        <td onclick=' afficherPortait(" + id + ")'>" + nom + "</td>\n\
                                        <td onclick=' afficherPortait(" + id + ")'>" + prenom + "</td>\n\
                                        <td>" + tyEtu + "</td>\n\
                                        <td>" + lastpre + "</td>\n\
                                        <td>" + nbpre + "</td>\n\
                                </tr>\n\
                                <input type='hidden' name='idsEtu[]' value=" + id + ">";
                listEtudiant.insertAdjacentHTML("beforeend", row);
        }
}

/**
 * get date aujourd'hui.
 * @returns {String} format dd-MM-yyyy
 */
function getNowDate() {
        var date = new Date();
        var annee = date.getFullYear();
        var mois = date.getMonth() + 1;
        var jour = date.getDate();
        var moisCompelet = mois >= 10 ? mois : "0" + mois;
        var jourCompelet = jour >= 10 ? jour : "0" + jour;
        var dateEnFormat = annee + "-" + moisCompelet + "-" + jourCompelet;
        return dateEnFormat;
}

/**
 * get l'heure de maintenant. par quart. 15min,30min,45min,00min.
 * @param {type} duree
 * @returns {String} format hh-mm
 */
function getNowTimeSeg(duree) {
        var date = new Date();
        var h = date.getHours();
        var m = date.getMinutes();
        //segment du temps
        m = m + duree;
        h = h + parseInt(m / 60);
        h = h % 24;
        m = m % 60;
        m = parseInt(m / 15) * 15;
        var hh = h >= 10 ? h : "0" + h;
        var mm = m >= 10 ? m : "0" + m;
        var heureEnFormat = hh + ":" + mm;
        return heureEnFormat;
}
/**
 * afficher la présence en couleur : pre : vert, retard : jaune, abs : rouge, abj : grey. 
 * @param {type} etatId   format : "etat1", "etat2"
 * @returns {undefined}
 */
function faireAppel(etatId) {
        var idEtu = "etu" + etatId.slice(4);
        var pre = document.getElementById(etatId).value;
        var rowEtu = document.getElementById(idEtu);
        var classe;
        if (pre === "4") {
                classe = "table-warning";
        } else if (pre === "2") {
                classe = "table-danger";
        } else if (pre === "3") {
                classe = "table-secondary";
        } else if (pre === "1") {
                classe = "table-success";
        }
        rowEtu.setAttribute("class", classe);
}

/**
 * afficher le portait de l'étudiant.
 * @param {type} idEtu
 * @returns {undefined}
 */
function afficherPortait(idEtu) {
        var portait = document.getElementById("portait");
        var img = document.getElementById("photoid");
        var xhr = new XMLHttpRequest();
        portait.style.display = "block";
        // Requête au serveur avec les paramètres éventuels.
        xhr.open("GET", "servletPhoto" + "?id=" + idEtu, true);
        // On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
        xhr.onload = function ()
        {
                // Si la requête http s'est bien passée.
                if (xhr.status === 200)
                {
                        var ph = xhr.responseXML.getElementsByTagName("photo");
                        var lien = ph[0].firstChild.nodeValue;
                        img.setAttribute("src", "resource/images/portaits/etu/" + lien);

                        portait.style.display = "block";
                }
        };
        xhr.send();
}

/**
 * Valider que les paramètres du formulaire soient tous sélectionnées.
 * 
 * @returns {Boolean}
 */
function validerForm() {
        var f = document.forms["formList"]["saisirFormation"].value;
        var c = document.forms["formList"]["saisirCours"].value;
        var t = document.forms["formList"]["saisirAct"].value;
        var g = document.forms["formList"]["saisirGroupe"].value;
        var d = document.forms["formList"]["date"].value;
        var h = document.forms["formList"]["time"].value;
        h = h.substring(3);
        var hf = document.forms["formList"]["timefin"].value;
        hf = hf.substring(3);
//        alert(hf);
        if (f === null || f === "none")
        {
                alert("Veuillez choisir la formation");
                return false;
        }
        if (c === null || c === "none")
        {
                alert("Veuillez choisir le cours");
                return false;
        }
        if (t === null || t === "none")
        {
                alert("Veuillez choisir le type d'activit\351");
                return false;
        }
        if (g === null || g === "none")
        {
                alert("Veuillez choisir le groupe");
                return false;
        }
        if (d === null || d === "")
        {
                alert("Veuillez choisir la date");
                return false;
        }
        if (h === null || h === "")
        {
                alert("Veuillez choisir l'heure d\351but");
                return false;
        }
        if (hf === null || hf === "")
        {
                alert("Veuillez choisir l'heure fin");
                return false;
        }
        if (h % 15 !== 0)
        {
                alert("Veuillez choisir la bonne heure d\351but");
                return false;
        }
        if (hf % 15 !== 0)
        {
                alert("Veuillez choisir la bonne heure fin");
                return false;
        }




}

function uncocherConfirmation() {
        var elt = document.getElementById("checkSubmit");
        elt.checked = false;
}
/**
 * Lancement après le chargement du DOM.
 */
document.addEventListener("DOMContentLoaded", () => {
        document.getElementById("saisirFormation").addEventListener("change", changeFormation);
        document.getElementById("saisirGroupe").addEventListener("change", changeGroupe);
        document.getElementById("date").addEventListener("change", changeDate);
        document.getElementById("saisirCours").addEventListener("change", changeCours);
}
);
