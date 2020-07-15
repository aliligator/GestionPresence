window.onload = function () {
        //Choisir Groupe
        // Objet XMLHttpRequest.
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "choixgroupe");
        xhr.onload = function () {
                if (xhr.status === 200) {
                        var lgp = xhr.responseXML.getElementsByTagName("groupe");
                        var lid = xhr.responseXML.getElementsByTagName("id");
                        // On construit les 'options' de notre liste déroulante.
                        var texte = "<option>--</option>";
                        for (var i = 0; i < lgp.length; i++)
                                texte += "<option value = " + lid[i].firstChild.nodeValue + ">" + lgp[i].firstChild.nodeValue + "</option>";
                        // Elément html que l'on va mettre à jour.
                        var sel_groupe = document.getElementById("saisirgroupe");
                        sel_groupe.innerHTML = texte;
                }
        };
        // Requête au serveur avec les paramètres éventuels.
        xhr.send(null);

        //Afficher tous les étudiants   
        var xhr1 = new XMLHttpRequest();
        //   var idR = document.getElementById("idResponsable").value;
        xhr1.open("GET", "toutetu");
        xhr1.onload = function () {
                if (xhr1.status === 200) {
                        var nav = document.getElementById("listEtudiant");
                        var listee = xhr1.responseXML.getElementsByTagName("etudiant");
                        for (var j = 0; j < listee.length; j++) {
                                var id = listee[j].getElementsByTagName("idE")[0].firstChild.nodeValue;
                                var nom = listee[j].getElementsByTagName("NomE")[0].firstChild.nodeValue;
                                var prenom = listee[j].getElementsByTagName("PrenomE")[0].firstChild.nodeValue;
                                var tyEtu = listee[j].getElementsByTagName("TypeE")[0].firstChild.nodeValue;
                                //    var groupeE = listee[j].getElementsByTagName("GroupeE")[0].firstChild.nodeValue;
                                var tab = "<tr>\n\
                                        <td>" + nom + "</td>\n\
                                        <td>" + prenom + "</td>\n\
                                        <td>" + tyEtu + "</td>\n\
                                        <td><input type=\"checkbox\" value=\"" + id + "\" name=\"oui\" /></td>\n\
                                    </tr><input type='hidden' name='idetu[]' value=" + id + ">";
                                nav.insertAdjacentHTML("beforeend", tab);
                        }
                }
        };
        xhr1.send();

//Choisir Formation
        // Objet XMLHttpRequest.
        var xhr2 = new XMLHttpRequest();
        xhr2.open("GET", "choisirformation");
        xhr2.onload = function () {
                if (xhr2.status === 200) {
                        var formation = xhr2.responseXML.getElementsByTagName("formation");
                        var idF = xhr2.responseXML.getElementsByTagName("id");
                        // On construit les 'options' de notre liste déroulante.
                        var texte1 = "<option>--</option>";
                        for (var i = 0; i < formation.length; i++)
                                texte1 += "<option value = " + idF[i].firstChild.nodeValue + ">" + formation[i].firstChild.nodeValue + "</option>";
                        //   texte += "<option style=\"visibility: hidden: \">" + id[i].firstChild.nodeValue + "</option>";
                        //value = \"id[i].firstChild.nodeValue\"
                        // Elément html que l'on va mettre à jour.
                        var sel_formation = document.getElementById("saisirformation");
                        sel_formation.innerHTML = texte1;

                }
        };

        // Requête au serveur avec les paramètres éventuels.
        xhr2.send(null);
};

function l_cours() {
        var xhr = new XMLHttpRequest();
        var nomformation = document.getElementById("saisirformation").value;
        xhr.open("GET", "choisircours?nomformation=" + nomformation);
        xhr.onload = function () {
                if (xhr.status === 200) {
                        var lnom = xhr.responseXML.getElementsByTagName("cours");
                        var lcode = xhr.responseXML.getElementsByTagName("id");
                        // On construit les 'options' de notre liste déroulante.
                        var texte = "<option>--</option>";
                        texte += "<option value = " + lcode[0].firstChild.nodeValue + " selected>" + lnom[0].firstChild.nodeValue + "</option>";
//                        for (var i = 0; i < lnom.length; i++){
//                              
//                                texte += "<option value = " + lcode[i].firstChild.nodeValue + ">" + lnom[i].firstChild.nodeValue + "</option>";
//                        }
                        // Elément html que l'on va mettre à jour.
                        var sel_cours = document.getElementById("saisircours");
                        sel_cours.innerHTML = texte;
                }
        };
        // Requête au serveur avec les paramètres éventuels.
        xhr.send(null);

}
;


function l_groupe() {
        var xhr = new XMLHttpRequest();
        var dansgroupe = document.getElementsByName("idetu[]");
        var idgroup = document.getElementById("saisirgroupe").value;
        var dans = new Array();
        var param = "";
        for (var i = 0; i < dansgroupe.length; i++) {
                dans.push(dansgroupe[i].value);
                param += "dans=" + dansgroupe[i].value + "&";
        }
        xhr.open("GET", "servletaffichergroupe?idg=" + idgroup + "&" + param);
        xhr.onload = function () {
                if (xhr.status === 200) {
                        var est = xhr.responseXML.getElementsByTagName("est");
                        var checklst = document.getElementsByName("oui");

                        for (var i = 0; i < est.length; i++) {
                                var val = est[i].firstChild.nodeValue;

                                if (val === "1") {
                                        checklst[i].checked = true;
                                } else if (val === "0") {
                                        checklst[i].checked = false;
                                }
                        }

                }
        };
        // Requête au serveur avec les paramètres éventuels.
        xhr.send(null);

}
;
function toggle(source) {
        var checkboxes = document.getElementsByName('oui');
        for (var i = 0, n = checkboxes.length; i < n; i++) {
                checkboxes[i].checked = source.checked;
        }
}



$("#afficherajouter").click(function () {
        if ($("#ajouter").css("display") === "none") {
                $("#ajouter").show();
        } else {
                $("#ajouter").hide();
        }
});

document.addEventListener("DOMContentLoaded", () => {
        document.getElementById("saisircours").addEventListener("change", l_groupe);
});





