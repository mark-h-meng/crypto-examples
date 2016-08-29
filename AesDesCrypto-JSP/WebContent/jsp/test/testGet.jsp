<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<jsp:useBean id="date" class="java.util.Date" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Cache-Control" content="no-cache, no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>HTTP Header Request Example (Get & Using Bean)</title>
</head>

<script src="${pageContext.request.contextPath}/js/rollups/tripledes.js"></script>
<script src="${pageContext.request.contextPath}/js/components/core.js"></script>
<script src="${pageContext.request.contextPath}/js/rollups/aes.js"></script>
<script
	src="${pageContext.request.contextPath}/js/components/enc-base64-min.js"></script>
<script
	src="${pageContext.request.contextPath}/js/components/enc-utf16-min.js"></script>

<script type="text/javascript">
	function vectorToBytes(v) {
		var a = v.split(/ /);
		for (var i = 0; i < a.length; i++)
			a[i] = parseInt('0x' + a[i]);
		return a;
	}

	function bytesToVector(b) {
		var v = b.slice(); // clone b
		for (var i = 0; i < v.length; i++) {
			v[i] = v[i] < 0x10 ? '0' + v[i].toString(16) : v[i].toString(16);
		}
		return v.join(' ');
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

	function validate() {
		var pt = document.getElementById("text").innerText;
		var ct = document.getElementById("ctext").innerText;
		var key = document.getElementById("secretText").innerText;
		var algo = document.getElementById("algorithm").innerText;
		var isPassed = false;
		var decryptedHex, decryptedLatin;
		if (pt == "null" || ct == "null" || key == "null" || ct.length == 0
				|| key.length == 0) {
			alert("Invalid input detected!");
			isPassed = false;
		} else {
			if (algo.indexOf('AES') > -1) {
				decryptedHex = CryptoJS.AES.decrypt(ct, key);
				decryptedLatin = CryptoJS.enc.Latin1.stringify(decryptedHex);
			} else {
				decryptedHex = CryptoJS.DES.decrypt(ct, key);
				decryptedLatin = CryptoJS.enc.Latin1.stringify(decryptedHex);
			}
			if (decryptedLatin == pt) {
				isPassed = true;
			} else {
				alert("VALIDATION FAILED!\nDECRYPTED TEXT " + decryptedLatin
						+ "\nPLAINTEXT TEXT [" + pt + "]");
				isPassed = false;
			}
		}
		if (isPassed) {
			document.getElementById("validationResult").innerHTML = "Pass - TEXT is "
					+ decryptedLatin;
			document.getElementById("validationResult").style.color = "#00AA00";
		} else {
			document.getElementById("validationResult").innerHTML = "Failed";
			document.getElementById("validationResult").style.color = "#FF0000";
		}
		document.getElementById("button_home").style.visibility = "visible";
		document.getElementById("button_validate").style.visibility = "hidden";
	}
</script>

<body bgcolor="#DDDDDD">
	<center>
		<h2>HTTP HEADER TEST (CRYPTO-GET)</h2>
		<p>
			The date/time is
			<%=date%></p>

		<p>
			<b>NRIC Plaintext</b></br>
		<div id="text"><%=request.getParameter("plaintext")%></div>
		</p>
		<p>
			<b>Encrypted NRIC</b></br>
		<div id="ctext"><%=request.getParameter("ciphertext")%></div>
		</p>
		<p>
			<b>Secret</b></br>
		<div id="secretText"><%=request.getParameter("secret")%></div>
		</p>
		<p>
			<b>Algorithm</b></br>
		<div id="algorithm"><%=request.getParameter("encryption")%>
		</div>
		</p>
		<button id="button_validate" onclick="validate()">Check
			Result</button>
		<p>
			<b><u>Decrypted Result</u></b></br>
		<h2>
			<div id="validationResult">Please press "Check Result" button
				to validate</div>
		</h2>
		</p>
		<form action="http://localhost:8080/AesDesCrypto-JSP/homepage.jsp">
			<input id="button_home" type="submit" value="Go to Homepage"
				style="visibility: hidden">
		</form>

	</center>
</body>
</html>