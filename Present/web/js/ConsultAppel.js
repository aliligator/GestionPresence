$(function () {
        // Objet XMLHttpRequest.
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "consultapp");
        xhr.onload = function () {
                if (xhr.status === 200) {
                        var lcours = xhr.responseXML.getElementsByTagName("cours");
                        // On construit les 'options' de notre liste déroulante.
                        var sel_cours = document.getElementById("saisirCours");
                        for (var i = 0; i < lcours.length; i++) {
                                var ab = lcours[i].firstChild.nodeValue;
                                alert(ab);
                                sel_cours.insertAdjacentHTML("beforeend", "<option" > +ab + " < /option>");
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






