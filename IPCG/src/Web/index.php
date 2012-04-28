<?php

?>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta content="text/html; charset=ISO-8859-1" http-equiv="content-type">
<title>3YP: IPCG - taes1g09</title>
<link rel="stylesheet" type="text/css" href="basic.css" />
</head>
<body>
<img src="favicon.png" id="watermark" style="position: fixed; bottom: 10px; right: 10px">
<div id="page">
	<div id="header">
		<h1 id="title">Intelligent Procedural Content Generation for Computer Games</h1>
	</div><!--unheader-->
<?php 
	if (isset($_POST['continue']) && isset($_POST['understood']) && isset($_POST['agree']) && isset($_POST['voluntary']) && isset($_POST['age']) && isset($_POST['data'])) include('java.php');
	else if (isset($_POST['continue'])) include('form.php'); 
	else include('info.php'); 
	?>

	<div id="footer">
		<span id="general">This page was lovingly handcrafted on a mac, as well as a variety of other operating systems, sometimes simultaneously :p  Logos &#169 their respective owners.</span>
		<span id="copyright">Hosted on the ECS servers (somewhere)</span> <span id="date">Last updated: 28/04/12</span>
	</div>
</div>

</body>
</html>

