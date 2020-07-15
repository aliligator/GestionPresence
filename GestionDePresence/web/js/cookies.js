function setCookie(){
    // set cookie
    var loginCode = $("#login_code").val(); // récupérer compte de utilisateur
    var pwd = $("#login_password").val(); // récupérer mot de pass de utilisateur
    var checked = $("[name='checkbox']:checked"); // récupérer mot de pass de utilisateur
    
    if(checked){
        $.cookie("login_code",loginCode); 
        $.cookie("login_password",$.base64.encode(pwd));      
    } else {
        $.cookie(pwd,null);
    }
}

function getCookie(){
    var loginCode = $.cookie(loginCode);
    var pwd = $.cookie(pwd);
    if(pwd){
        $("[name='checkbox']").attr("checked","true");
    }
    if(loginCode){
        $("#login_code").val(loginCode);
    }
    if(pwd){
        $("#login_password").val($.base64.encode(pwd));
    }
}




