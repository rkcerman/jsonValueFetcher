<head>
  <title>Welcome!</title>
</head>
<body>
  <h1>Welcome ${message}!</h1>
  <form action="/upload" enctype="multipart/form-data" method="post">
	  <label for="title">Title</label>
	  <input type="text" name="title"><br>
	  <label for="file">File to send</label>
	  <input type="file" name="file"><br>
	  <input type="submit" value="Upload">
  </form>
</body>
</html>
