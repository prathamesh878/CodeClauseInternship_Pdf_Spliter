/*
 * package com.pdf_spliter.prathamesh_pdf;
 * 
 * import org.apache.pdfbox.multipdf.PDFMergerUtility; import
 * org.apache.pdfbox.pdmodel.PDDocument; import
 * org.apache.pdfbox.pdmodel.PDPage; import org.apache.pdfbox.cos.COSStream;
 * 
 * import java.io.ByteArrayOutputStream; import java.io.IOException; import
 * java.io.InputStream; import java.util.Iterator; import java.util.List; import
 * org.apache.pdfbox.pdmodel.PDPageContentStream; import
 * org.apache.pdfbox.pdmodel.PDPageTree; import
 * org.apache.pdfbox.pdmodel.common.PDStream; import
 * org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject; import
 * org.apache.tomcat.util.http.fileupload.IOUtils; import
 * org.springframework.http.HttpHeaders; import
 * org.springframework.http.HttpStatus; import
 * org.springframework.http.MediaType; import
 * org.springframework.http.ResponseEntity; import
 * org.springframework.stereotype.Controller; import
 * org.springframework.web.bind.annotation.PostMapping; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.bind.annotation.RequestParam; import
 * org.springframework.web.bind.annotation.ResponseBody; import
 * org.springframework.web.multipart.MultipartFile; import
 * java.io.ByteArrayInputStream; import java.io.ByteArrayOutputStream; import
 * java.io.File; import java.io.IOException; import
 * org.apache.pdfbox.pdmodel.PDResources; import java.io.InputStream; import
 * java.nio.file.Files; import java.nio.file.Path; import java.nio.file.Paths;
 * import java.nio.file.StandardCopyOption; import java.util.List;
 * 
 * import org.apache.pdfbox.util.Matrix; import
 * org.apache.pdfbox.pdmodel.PDPageContentStream;
 * 
 * 
 * import org.springframework.ui.Model;
 * 
 * @Controller
 * 
 * @RequestMapping("/pdf") public class HandlePdfUploded {
 * 
 * @PostMapping("/upload") public String uploadPdf(@RequestParam("file")
 * MultipartFile file, Model model) { // Implement logic to handle the uploaded
 * PDF file // You can save it to a temporary location or process it as needed
 * int numPages = getNumberOfPages(file);
 * 
 * // Save the PDF file to a temporary location String tempFilePath =
 * savePdfToTempLocation(file);
 * 
 * // Pass information to the displayPDFdicide.jsp
 * model.addAttribute("fileName", file.getOriginalFilename());
 * model.addAttribute("numPages", numPages); model.addAttribute("pdfFilePath",
 * tempFilePath); // Add the file path to the model
 * 
 * // Return the name of the JSP file to display the PDF information return
 * "displayPDFdicide"; }
 * 
 * 
 * @PostMapping("/pages")
 * 
 * @ResponseBody public int getNumberOfPages(@RequestParam("file") MultipartFile
 * file) { try (PDDocument document = PDDocument.load(file.getInputStream())) {
 * return document.getNumberOfPages(); } catch (IOException e) {
 * e.printStackTrace(); return -1; // Handle the exception as needed } }
 * 
 * 
 * private String savePdfToTempLocation(MultipartFile file) { try { // Define
 * the directory where you want to save the PDF files temporarily String
 * tempDirPath = "D:\\AISSMS\\Devlopment\\PDFSPLIT\\PDFSAVER";
 * 
 * // Create the directory if it doesn't exist Path tempDir =
 * Paths.get(tempDirPath); if (!Files.exists(tempDir)) {
 * Files.createDirectories(tempDir); }
 * 
 * // Generate a unique file name for the uploaded PDF String uniqueFileName =
 * System.currentTimeMillis() + "_" + file.getOriginalFilename();
 * 
 * // Build the full path to save the PDF Path filePath =
 * tempDir.resolve(uniqueFileName);
 * 
 * // Save the PDF to the temporary location Files.copy(file.getInputStream(),
 * filePath, StandardCopyOption.REPLACE_EXISTING);
 * 
 * // Return the file path or some identifier for later retrieval return
 * filePath.toString(); } catch (IOException e) { e.printStackTrace(); // Handle
 * the exception as needed return null; // Return null in case of an error } }
 * 
 * 
 * 
 * @PostMapping("/split") public ResponseEntity<byte[]> splitPdf(
 * 
 * @RequestParam("pdfFilePath") String pdfFilePath,
 * 
 * @RequestParam("startPage") int startPage,
 * 
 * @RequestParam("endPage") int endPage) {
 * 
 * // Implement logic to split the PDF using the file path byte[] splitPdfBytes
 * = getPdfSplitBytes(pdfFilePath, startPage, endPage);
 * 
 * HttpHeaders headers = new HttpHeaders();
 * headers.setContentType(MediaType.APPLICATION_PDF);
 * 
 * // Combine the original PDF file name with "split" String originalFileName =
 * new File(pdfFilePath).getName(); String fileName =
 * originalFileName.replaceFirst("[.][^.]+$", "") + "_split.pdf";
 * 
 * headers.setContentDispositionFormData("attachment", fileName);
 * headers.setContentLength(splitPdfBytes.length);
 * 
 * return new ResponseEntity<>(splitPdfBytes, headers, HttpStatus.OK); }
 * 
 * 
 * // Add this method to split the PDF using the file path
 * 
 * 
 * // Other imports...
 * 
 * private byte[] getPdfSplitBytes(String pdfFilePath, int startPage, int
 * endPage) { try (PDDocument document = PDDocument.load(new File(pdfFilePath)))
 * { // Validate startPage and endPage if (startPage < 1 || endPage >
 * document.getNumberOfPages() || startPage > endPage) { throw new
 * IllegalArgumentException("Invalid page range"); }
 * 
 * // Create a new document for the split pages try (PDDocument splitDocument =
 * new PDDocument()) { // Extract pages from the original document PDPageTree
 * originalPages = document.getPages(); for (int i = startPage - 1; i < endPage;
 * i++) { PDPage originalPage = originalPages.get(i);
 * 
 * // Create a new page for the split document PDPage clonedPage = new
 * PDPage(originalPage.getMediaBox()); splitDocument.addPage(clonedPage);
 * 
 * // Copy the content from the original page to the cloned page byte[]
 * pageContent = clonePageContent(document, originalPage); PDStream
 * newContentStream = new PDStream(document, new
 * ByteArrayInputStream(pageContent)); clonedPage.setContents(newContentStream);
 * }
 * 
 * // Save the split document to a byte array ByteArrayOutputStream outputStream
 * = new ByteArrayOutputStream(); splitDocument.save(outputStream); return
 * outputStream.toByteArray(); } } catch (IOException e) { e.printStackTrace();
 * // Handle the exception as needed return new byte[0]; } }
 * 
 * private byte[] clonePageContent(PDDocument document, PDPage sourcePage) { try
 * { // Create a new ByteArrayOutputStream to store the content
 * ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
 * 
 * // Get the content streams of the source page List<COSStream> contentStreams
 * = sourcePage.getContentStreams();
 * 
 * // Iterate through the content streams of the source page for (COSStream
 * contentStream : contentStreams) { try (InputStream inputStream =
 * contentStream.createInputStream()) { IOUtils.copy(inputStream, outputStream);
 * } }
 * 
 * return outputStream.toByteArray(); } catch (IOException e) {
 * e.printStackTrace(); // Handle the exception as needed return new byte[0]; }
 * }
 * 
 * }
 */