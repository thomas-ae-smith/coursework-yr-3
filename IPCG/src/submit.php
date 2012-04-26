<?php
$data = $_POST['data'];
if (isset($_POST['submit']) && filter_var($data, FILTER_SANITIZE_STRING)) {
$fileName = "data/" . strtotime(' ') . ".txt";
$fileHandle = fopen($fileName, 'w') or $success = False;
if ($fileHandle) {
	fwrite($fileHandle, $data);
	fclose($fileHandle);
	$success = True;
}

echo("<!--" . $success . "-->");

}

?>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta content="text/html; charset=ISO-8859-1" http-equiv="content-type">
<title>Manual Submission</title>
</head>
<body>

<h1>Submit Data</h1>
<form method="post" action="" name="submission">
<label for="data">Paste experiment data here:</label> <br />
	<input type="textarea" name="data" value="<?php echo $data;?>" rows="3" 
		<?php 
		if ($success) {
			echo( "readonly=\"readonly\"");
		}
		?>
	/>
	<br />
<br />
<input type="submit" name="submit" value="Submit" 
	<?php 
		if ($success) {
			echo( "disabled=\"disabled\"");
		}
		?>
	/>
<br />
</form>
</div>
<?php
	if ($success) {echo("<div style=\"color:green\">Data submission successful.</div>");}
	else {echo("<div style=\"color:red\">Write error: can't open file.</div>");}
?>

<br />
</body>
</html>