function getFiche() {
        var xhr = new XMLHttpRequest();
        var mois = document.getElementById("moischoisi").value;
        var idetu = document.getElementById("etuid").firstChild.nodeValue;
        xhr.open("GET", "ServletsEtuDownload?anneeMois=" + mois + "&idetu=" + idetu);
        xhr.onload = function () {
                if (xhr.status === 200) {
                        var tab = xhr.responseXML.getElementsByTagName("url")[0].firstChild.nodeValue;
                        var lien = document.getElementById("zoneurl");
                        lien.innerHTML = "<a target='_blank' href=\"" + tab + "\">Cliquer pour t&eacute;l&eacute;charger</a>";
                }
        };
// Envoie de la requête.
        xhr.send();
}
document.addEventListener("DOMContentLoaded", () => {
        document.getElementById("btnSubmit").addEventListener("onclick", getFiche);
}
);