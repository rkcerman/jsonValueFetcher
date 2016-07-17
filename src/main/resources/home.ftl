<head>
  <title>Welcome!</title>
</head>
<body>
  <#-- Greet the user with his/her name -->
  <h1>Welcome ${message}!</h1>
  <form action="/upload" enctype="multipart/form-data" method="post">
	  <label for="upfile">File to send</label>
	  <input type="file" name="upfile"><br>
	  <input type="submit" value="Upload">
  </form>
</body>
</html>
