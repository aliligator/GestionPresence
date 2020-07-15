/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function l_fichier()
{
//     alert("function l_fichier");
        // Objet XMLHttpRequest.
        var xhr = new XMLHttpRequest();

        var annee = document.getElementById("anneesaisie").value;
//        alert(annee);

        // Requête au serveur avec les paramètres éventuels.
        xhr.open("GET", "servletFichiersEtu" + "?anneesaisie=" + annee, true);

        // On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
        xhr.onload = function ()
        
        {
                // Si la requête http s'est bien passée.
                if (xhr.status === 200)
                {
                        // Elément html que l'on va mettre à jour.
                        var TabFichier = document.getElementById("lFichieruEtu");
                        TabFichier.innerHTML = null;
                        TabFichier.insertAdjacentHTML("afterbegin", "<thead><tr><th>Année</th><th>Mois</th><th>Nom Fichier</th><th>EtatValide</th><th>Télécharger</th><th>Supprimer</th></tr></thead>");
                        var tab = xhr.responseXML.getElementsByTagName("fichier");
                        for (i = 0; i < tab.length; i++)
                        {
                            var idfi = tab[i].getElementsByTagName("idfichier")[0].firstChild.nodeValue;
                            var annee = tab[i].getElementsByTagName("annee")[0].firstChild.nodeValue;
                            var mois = tab[i].getElementsByTagName("mois")[0].firstChild.nodeValue;
                            //var url = tab[i].getElementsByTagName("url")[0].firstChild.nodeValue;
                            var nom = tab[i].getElementsByTagName("nom")[0].firstChild.nodeValue;
                            var etat = tab[i].getElementsByTagName("etat")[0].firstChild.nodeValue;

                                TabFichier.insertAdjacentHTML('beforeend', "<tr>" +
                                        "<td >" + annee + "</td>" +
                                        "<td>" + mois + "</td>" +
                                        //"<td>" + url + "</td>" +
                                        "<td id = nomfichier>" + nom + "</td>" +
                                        "<td>" + etat + "</td>" +
                                        "<td><a href ='DownloadServlet?fileName="+nom+"'>Télécharger</a></td>" +
                                        "<td><a href ='DeleteServlet?fileName="+nom+"&idfi="+idfi+"'>Supprimer</a></td>" +
                                        "</tr>");

                        }
                }
                
        

        };
        xhr.send();

}


/**
 * Lancement après le chargement du DOM.
 */
document.addEventListener("DOMContentLoaded", () => {

        document.getElementById("btn_valider").addEventListener("click", l_fichier);

});