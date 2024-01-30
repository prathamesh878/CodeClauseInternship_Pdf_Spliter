package com.pdf_spliter.prathamesh_pdf;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Mycontroller {

	@GetMapping("/showForm")
	public String showUploadForm() {
		return "select";
	}
}
