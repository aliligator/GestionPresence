<%-- 
    Document   : connexion
    Created on : 16 Juillet 2020
    Author : Akhi & Aliénor
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
        <head>
                <meta charset="utf-8">
                <meta name="viewport" content="width=device-width, initial-scale=1">
                <title>Login</title>
                  <!-- Bootstrap core CSS -->
                  <link rel="stylesheet" type="text/css" href="vendor/bootstrap/css/bootstrap.min.css">
                  <link rel="icon" type="image/png" href="images/icons/favicon.ico"/>
                  <link rel="stylesheet" type="text/css" href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
                  <link rel="stylesheet" type="text/css" href="fonts/Linearicons-Free-v1.0.0/icon-font.min.css">
                  <link rel="stylesheet" type="text/css" href="css/util.css">
                  <link rel="stylesheet" type="text/css" href="css/main.css">
                   <script src="js/jquery.cookie.js"></script>  
                  <script src="js/jquery.base64.js"></script> 
                  <script src="js/jquery-3.4.1.slim.min.js"></script>  
                  <script src="js/cookie.js"></script>  
        </head>
        
        <body>
	
	<div class="limiter">
		<div class="container-login100" style="background-image: url('ressource/images/ut1.png');">
			<div class="wrap-login100 p-t-30 p-b-50">
				<span class="login100-form-title p-b-41">CONNEXION</span>
                                <form class="login100-form validate-form p-b-33 p-t-5" method="get" action="login">
                                    
					<div class="wrap-input100 validate-input" data-validate = "Enter Email">
						<input class="input100" type="text" name="email" placeholder="Email" required>
						<span class="focus-input100" data-placeholder="&#xe82a;"></span>
					</div>

					<div class="wrap-input100 validate-input" data-validate="Enter password">
						<input class="input100" type="password" name="pass" placeholder="Mot de passe" required>
						<span class="focus-input100" data-placeholder="&#xe80f;"></span>
					</div>
                                        <div class="espace"><input type="checkbox">&nbsp;&nbsp;Se souvenir de moi</div>
                                        <div class="espace">${msg_info}</div>
					<div class="container-login100-form-btn m-t-32">
                                            <input class="login100-form-btn" type="submit" value="Login"/>	
					</div>
				</form>
			</div>
		</div>
	</div>
	

	<div id="dropDownSelect1"></div>
	
<!--===============================================================================================-->
	<script src="js/main.js"></script>

</body>
</html>

