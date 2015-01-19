<!DOCTYPE html>
<html>
<head>
<title>NoteIt Create Account</title>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7; IE=EmulateIE9">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no"/>
    <link rel="stylesheet" type="text/css" href="style.css" media="all" />
    <link rel="stylesheet" type="text/css" href="demo.css" media="all" />
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.0/jquery.min.js" type="text/javascript"></script>
	<script type="text/javascript" charset="utf-8" src="js/jquery.tubular.1.0.js"></script> 
	<script>
		$().ready(function() {
	        $('#wrapper').tubular({videoId: 'tAUuY0Yld0E'});
		});
	</script>
	
</head>
<body>
<div id="wrapper">
<div class="container">
			<header>
				<img src="./images/NoteItIcon_128x128.png"></img>
					<br/>
				<div class="headertext">NoteIt</div>
            </header>       
      		<div  class="form">
    		<form id="contactform" action="AddUser" method="post"> 
    			<p class="contact"><label for="FirstName">First Name</label></p> 
    			<input id="Fname" name="fname" placeholder="First name" required="" tabindex="1" type="text" autofocus>
    			 
    			<p class="contact"><label for="name">Last Name</label></p> 
    			<input id="Lname" name="lname" placeholder="Last name" required="" tabindex="2" type="text">
    			
    			<p class="contact"><label for="email">Email</label></p> 
    			<input id="email" name="email" placeholder="example@domain.com" tabindex="3" required="" type="email"> 
              
                <p class="contact"><label for="password">Create a password</label></p> 
    			<input type="password" id="password" name="password" required="" tabindex="4"> 
    			
                <p class="contact"><label for="repassword">Confirm your password</label></p> 
    			<input type="password" id="repassword" name="repassword" required="" tabindex="5"> 
    			
        	    <input class="buttom" name="submit" id="submit" tabindex="6" value="Sign me up!" type="submit"> 	 
   			</form> 
			</div>      
</div>
</div>
<%@ include file="footer.jsp" %>
