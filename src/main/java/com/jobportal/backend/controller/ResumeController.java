package com.jobportal.backend.controller;

import java.io.IOException;
import java.nio.file.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api/resume")
//@CrossOrigin("http://localhost:5173")
public class ResumeController {

	@GetMapping("/{fileName}")
	public ResponseEntity<Resource>
	getResume(
	        @PathVariable String fileName)
	        throws IOException {

	    Path path =

	            Paths.get(
	                    System.getProperty("user.dir")
	                            + "/uploads/"
	                            + fileName);

	    Resource resource =
	            new UrlResource(
	                    path.toUri());

	    return ResponseEntity.ok()

	            .contentType(
	                    MediaType.APPLICATION_PDF)

	            .header(
	                    HttpHeaders.CONTENT_DISPOSITION,

	                    "inline; filename=\""
	                            + fileName
	                            + "\"")

	            .body(resource);
	}
}