<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<title>File Upload Form</title>
<style>
body {
	padding: 20px;
}

.custom-container {
	background-color: #f8f9fa;
	width: 700px; 
	margin: auto; 
	border-radius: 10px; 
	background-color: #d1e9d1; 
	padding: 20px; 
	margin-top: 50px;
}

.btn-success {
	background-color: #006400; 
	border-color: #006400;
}

.custom-file-input {
	border-color: #006400; 
}

.input-group-prepend .btn-outline-secondary {
	background-color: #006400; 
	border-color: #006400;
	color: #fff; 
}
</style>
</head>
<body>

	<div class="container custom-container">
		<h2 class="mt-4 mb-4">
			Select File &nbsp<span class="fa fa-file-pdf-o"></span>
		</h2>

		<form id="fileUploadForm" action="/pdf/upload" method="post"
			enctype="multipart/form-data">
			<div class="row">
				<div class="col-12">
					<h5>Using our application you can split any pdf.</h5>
				</div>
			</div>

			<div class="row">
				<div class="col-md-9">
					<div class="input-group mb-3">

						<div class="custom-file">
							<input name="file" type="file" class="custom-file-input"
								id="inputGroupFile03" onchange="updateFileName()"> <label
								class="custom-file-label" for="inputGroupFile03">Choose
								file</label>
						</div>
					</div>
				</div>
			</div>

			<button type="submit" class="btn btn-success mt-2">
				Upload &nbsp <span class="fa fa-upload">
			</button>
		</form>
	</div>

	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.1/dist/umd/popper.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

	<script>
		function updateFileName() {
			var input = document.getElementById('inputGroupFile03');
			var label = input.nextElementSibling;
			var fileName = input.files[0].name;
			label.innerHTML = fileName;
		}
	</script>

</body>
</html>
