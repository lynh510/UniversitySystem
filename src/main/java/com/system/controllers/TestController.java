package com.system.controllers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class TestController {

	@RequestMapping("/")
	public ModelAndView firstPage() {
		return new ModelAndView("redirect:/student/login");
	}

	@GetMapping("/image/{name:.+}")
	public ResponseEntity<byte[]> getImage(@PathVariable("name") String name) throws IOException {
		try {
			File imgFile = new File("/file/" + name);
			byte[] bytes = Files.readAllBytes(imgFile.toPath());
			return ResponseEntity.ok().contentType(org.springframework.http.MediaType.IMAGE_JPEG).body(bytes);
		} catch (NoSuchFileException e) {
			File imgFile = new File("src\\main\\resources\\uploads\\default_avatar.png");
			byte[] bytes = Files.readAllBytes(imgFile.toPath());
			return ResponseEntity.ok().contentType(org.springframework.http.MediaType.IMAGE_JPEG).body(bytes);
		}

	}

	@RequestMapping("/file/{name:.+}")
	public ResponseEntity<byte[]> downloadFile(@PathVariable("name") String name) throws IOException {
		String filename = "/file/" + name + ".jpg";
		FileInputStream inputImage = new FileInputStream(filename);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[512];
		int l = inputImage.read(buffer);
		while (l >= 0) {
			outputStream.write(buffer, 0, l);
			l = inputImage.read(buffer);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "image/jpeg");
		headers.set("Content-Disposition", "attachment; filename=\"" + name + ".jpg\"");
		return new ResponseEntity<byte[]>(outputStream.toByteArray(), headers, HttpStatus.OK);
	}

}
