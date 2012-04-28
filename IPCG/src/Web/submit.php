<?php
$success = False;
$data = $_POST['data'];
if (isset($_POST['submit']) && filter_var($data, FILTER_SANITIZE_STRING)) {
	$fileName = "data/" . strtotime(' ') . ".txt";
	$fileHandle = fopen($fileName, 'w') or $fail = True;
	if ($fileHandle) {
		fwrite($fileHandle, $data);
		fclose($fileHandle);
		$success = True;
	}
}

echo("<!--" . (($success)? 1 : 0) . "-->");

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
<div>
<?php
	if ($success) {echo("<span style=\"color:green\">Data submission successful.</span>");}
	else if ($fail) {echo("<span style=\"color:red\">Write error: can't open file.</span>");}
	else echo("<br />");
?>
</div>
<div>Contact <a href="mailto:taes1g09@ecs.soton.ac.uk?Subject=IPCG">taes1g09@ecs.soton.ac.uk</a> if you have any questions.</div>
<br />
</body>
</html>