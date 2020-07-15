/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
// 获取当前日期
function getDate()
{
    var date = new Date();

    var jour = date.getDate();
    var mois = date.getMonth();
    var annee = date.getFullYear();

    var today = jour+"/"+mois+"/"+annee;
}

