<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page language="java" import="com.markmeng.*, java.util.*"%>
<jsp:useBean id="date" class="java.util.Date" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="Cache-Control" content="no-cache, no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">
<meta http-equiv="X-Frame-Options" content="SAMEORIGIN">
<meta http-equiv="X-XSS-Protection" content="1;mode=block">
<meta http-equiv="X-Content-Type-Options" content="nosniff">
<meta http-equiv="Strict-Transport-Security"
	content="max-age=31536000;includeSubDomains;preload">

<title>Homepage - AES/DES Crypto JSP Project</title>
</head>
<script src="js/rollups/tripledes.js"></script>
<script src="js/rollups/hmac-md5.js"></script>
<script src="js/rollups/aes.js"></script>
<script src="js/components/core.js"></script>
<script src="js/components/enc-base64-min.js"></script>
<script src="js/components/enc-utf16-min.js"></script>
<script type="text/javascript">
	function validateForm() {
		var age = document.forms["getForm"]["age"].value;
		if (age_new == null || age_new == "") {
			alert("Please type in your age.");
			return false;
		}
		if (isNaN(age_new)) {
			alert("Age must be a number!");
			return false;
		}
		alert("Your age is [" + age_new + "]");
		document.forms[1].age.value = age_new;
		return true;
	}
	function isEqual(a, b) {
		if (a.length !== b.length) {
			return false;
		}
		for (var i = 0; i < a.length; i++) {

			if (a[i] !== b[i]) {
				return false;
			}
		}
		return true;
	}

	function encryptIC(element) {
		var pt = document.forms[element.name]["plaintext"].value;
		var key = document.forms[element.name]["secret"].value;
		//alert("Plaintext: "+pt+"\nKey: "+key);
		var usingAES = document.forms[element.name]["AES"].checked;
		if (usingAES) {
			ct = CryptoJS.AES.encrypt(pt, key);

		} else {
			ct = CryptoJS.DES.encrypt(pt, key);
		}
		document.forms[element.name]["ciphertext"].value = ct;
		alert("Ciphertext Obtained: " + ct + "\nSubmit?");
	}
</script>

<body bgcolor="#EEEEEE">
	<center>
		<h2>AES/DES Crypto JSP Project</h2>
		<p>
			The date/time is
			<%=date%>
		</p>
		<form action="jsp/test/testGet.jsp" method="get"
			name="cryptoGetForm" onsubmit="return encryptIC(this)">
			<fieldset>
				<h4>Form - Get with Crypto</h4>
				<br /> Plaintext: <input type="text" value="HELLO WORLD!"
					name="plaintext" /> <br /> Secret: <input type="text"
					value="12aBcD@#*90" name="secret" /> <br /> Encryption Algorithm:
				<input id="AES" type="radio" name="encryption" value="AES" checked>AES
				<input value="DES" type="radio" name="encryption" value="DES">DES<br />
				<br />
			</fieldset>
			<br /> Ciphertext to be sent : <br /> <input type="text"
				name="ciphertext" readonly="readonly" /> <br /> <br /> <input
				type="submit" value="Encrypt & Submit Get" />
		</form>
		</br>
		<form action="jsp/test/testPost.jsp" method="post"
			name="cryptoPostForm" onsubmit="return encryptIC(this)">
			<fieldset>
				<h4>Form - Post with Crypto</h4>
				<br /> Plaintext: <input type="text" value="HELLO WORLD!"
					name="plaintext" /> <br /> Secret: <input type="text"
					value="12aBcD@#*90" name="secret" /> <br /> Encryption Algorithm:
				<input id="AES" type="radio" name="encryption" value="AES" checked>AES
				<input value="DES" type="radio" name="encryption" value="DES">DES<br />
				<br />
			</fieldset>
			<br /> Ciphertext to be sent <br /> <input type="text"
				name="ciphertext" readonly="readonly" /> <br /> <br /> <input
				type="submit" value="Encrypt & Submit Post" />
		</form>
		</center>
		<p>
		<center>The encryption feature is provided by CryptoJS v3.1.
		</center>
		</p>
</body>
</html>