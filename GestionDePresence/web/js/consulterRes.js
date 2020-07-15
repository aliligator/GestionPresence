/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function l_etufiche()
{
     //alert("function l_fichier");
        // Objet XMLHttpRequest.
        var xhr = new XMLHttpRequest();

        var moisannee = document.getElementById("mois").value;
        //alert(moisannee);

        // Requête au serveur avec les paramètres éventuels.
        xhr.open("GET", "servletFichiersRes" + "?moisannee=" + moisannee, true);

        // On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
        xhr.onload = function ()
        
        {
                // Si la requête http s'est bien passée.
                if (xhr.status === 200)
                {
                        // Elément html que l'on va mettre à jour.
                        var TabFichier = document.getElementById("listfichier");
                        TabFichier.innerHTML = null;
                        TabFichier.insertAdjacentHTML("afterbegin", "<thead><tr><th>Déposer</th><th>NuméroEtu</th><th>Nom</th><th>Prenom</th><th>EtatValide</th><th>Télécharger</th></tr></thead>");
                        var tab = xhr.responseXML.getElementsByTagName("etufichier");
                        for (i = 0; i < tab.length; i++)
                        {
                            var numetu = tab[i].getElementsByTagName("numetu")[0].firstChild.nodeValue;
                            var nometu = tab[i].getElementsByTagName("nometu")[0].firstChild.nodeValue;
                            var prenometu = tab[i].getElementsByTagName("prenometu")[0].firstChild.nodeValue;
                            var etat = tab[i].getElementsByTagName("etat")[0].firstChild.nodeValue;
                            var idfi = tab[i].getElementsByTagName("idfi")[0].firstChild.nodeValue;
                            var nomfi = tab[i].getElementsByTagName("nomfi")[0].firstChild.nodeValue;

                                TabFichier.insertAdjacentHTML('beforeend', "<tr>" +
                                        "<td ><input type='radio' id= 'idfi'name ='idfis' value='"+idfi+"'/></td>" +
                                        "<td >" + numetu + "</td>" +
                                        "<td>" + nometu + "</td>" +
                                        "<td>" + prenometu + "</td>" +
                                        "<td>" + etat + "</td>" +
                                        "<td><a href ='DownloadServlet?fileName="+nomfi+"'>Télécharger</a></td>" +
                                        "</tr>");
                        }
                }
                
        

        };
        xhr.send();

}

function activeDepo(){
    document.getElementById("btn_vali").disabled = "";
}

function deleteFi()
{
        // Objet XMLHttpRequest.
        var xhr = new XMLHttpRequest();

        var idfi = document.getElementById("idfi").value;

        // Requête au serveur avec les paramètres éventuels.
        xhr.open("GET", "servletUpdateFi"+ "?idfi=" + idfi, true);
        

        // On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
        xhr.onload = function ()
        
        {
                // Si la requête http s'est bien passée.
                if (xhr.status === 200)
                {
                      
                        
                }
                
        };
        xhr.send();

    
}


/**
 * Lancement après le chargement du DOM.
 */
document.addEventListener("DOMContentLoaded", () => {

        document.getElementById("mois").addEventListener("change", l_etufiche);
        document.getElementById("filedeposer").addEventListener("change", activeDepo);
        //document.getElementById("btn_vali").addEventListener("click",deleteFi);
});