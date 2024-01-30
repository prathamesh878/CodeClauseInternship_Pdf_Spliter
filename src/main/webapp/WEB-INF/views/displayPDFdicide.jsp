<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>PDF Information</title>

<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<style>
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
	width: 130px;
	font-size: 20px;
	border-radius: 10px;
}
</style>
</head>
<body>


	<div class="card custom-container">
		<div class="card-header">
			<h2 class="mt-4 mb-4">
				PDF Information &nbsp<span class="fa fa-file-pdf-o"></span>
			</h2>
			<div class="row">
				<div class="col-12">
					<h5>File Name: ${fileName}</h5>
				</div>
			</div>
			<div class="row">
				<div class="col-12">
					<h5>Number of Pages: ${numPages}</h5>
				</div>
			</div>
		</div>
		<div class="card-body">
			<form action="/pdf/split" method="post" enctype="multipart/form-data">
				<div class="row mt-4">
					<div class="col-12">
						<h4>
							Enter page numbers to split pdf &nbsp<span class="fa fa-map-o"></span>
						</h4>
					</div>
				</div>

				<div class="row">
					<div class="col-md-9 mt-3">
						<div class="container mt-4">
							<div class="row">
								<div class="col-md-3">
									<label for="startPage" class="form-label"><b>Start
											Page:</b></label> <input type="number" class="form-control"
										id="startPage" name="startPage" required>
								</div>
								<div class="col-md-3">
									<label for="endPage" class="form-label"><b>End
											Page:</b></label> <input type="number" class="form-control" id="endPage"
										name="endPage" required>
								</div>
							</div>
							<input type="hidden" name="pdfFilePath" value="${pdfFilePath}">
							<div class="row mt-3">
								<div class="col-md-3">
									<button type="submit" class="btn btn-primary btn-success">Split
										PDF</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>



	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.1/dist/umd/popper.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>




</body>
</html>
