package com.pdf_spliter.prathamesh_pdf;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/pdf")
public class NEWHandlePdfUploded {

	@PostMapping("/upload")
	public String uploadPdf(@RequestParam("file") MultipartFile file, Model model) {

		int numPages = getNumberOfPages(file);


	    String tempFilePath = savePdfToTempLocation(file);


	    model.addAttribute("fileName", file.getOriginalFilename());
	    model.addAttribute("numPages", numPages);
	    model.addAttribute("pdfFilePath", tempFilePath); // Add the file path to the model

	    return "displayPDFdicide";
	}
	
	
	@PostMapping("/pages")
	@ResponseBody
	public int getNumberOfPages(@RequestParam("file") MultipartFile file) {
		try (PDDocument document = PDDocument.load(file.getInputStream())) {
			return document.getNumberOfPages();
		} catch (IOException e) {
			e.printStackTrace();
			return -1; 
		}
	}

	
	private String savePdfToTempLocation(MultipartFile file) {
	    try {

	    	String tempDirPath = "D:\\AISSMS\\Devlopment\\PDFSPLIT\\PDFSAVER";


	        Path tempDir = Paths.get(tempDirPath);
	        if (!Files.exists(tempDir)) {
	            Files.createDirectories(tempDir);
	        }


	        String uniqueFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();


	        Path filePath = tempDir.resolve(uniqueFileName);


	        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);


	        return filePath.toString();
	    } catch (IOException e) {
	        e.printStackTrace();

	        return null; 
	    }
	}
	
	
	
	@PostMapping("/split")
	public ResponseEntity<byte[]> splitPdf(
	        @RequestParam("pdfFilePath") String pdfFilePath,
	        @RequestParam("startPage") int startPage,
	        @RequestParam("endPage") int endPage) throws IOException {

	    File oldFile = new File(pdfFilePath);
	    PDDocument document = PDDocument.load(oldFile);

	    Splitter splitter = new Splitter();
	    splitter.setStartPage(startPage);
	    splitter.setEndPage(endPage);

	    List<PDDocument> splitPages = splitter.split(document);
	    PDDocument newDoc = new PDDocument();


	    for (PDDocument mydoc : splitPages) {
	        for (PDPage page : mydoc.getPages()) {
	            newDoc.addPage(page);
	        }
	    }

	    try {

	    	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        newDoc.save(outputStream);
	        newDoc.close();


	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	        headers.setContentDispositionFormData("attachment", "split.pdf");


	        return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);

	    } catch (IOException e) {
	        e.printStackTrace();

	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}



}
