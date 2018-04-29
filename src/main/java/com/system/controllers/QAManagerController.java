package com.system.controllers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.system.ApiResponse;
import com.system.Helper;
import com.system.entity.*;
import com.system.models.*;

@Controller
@RequestMapping("/qamanager")
public class QAManagerController {
	private AuthorizationManagement am;
	private ExternalLoginManagement elm;
	private ReportManagement rm;
	private DepartmentManagement dm;
	private QAManagerManagement qm;
	private IdeaAttachFileManagement iafm;
	private Helper helper;

	public QAManagerController() {
		elm = new ExternalLoginManagement();
		am = new AuthorizationManagement();
		rm = new ReportManagement();
		dm = new DepartmentManagement();
		qm = new QAManagerManagement();
		iafm = new IdeaAttachFileManagement();
		helper = new Helper();
	}

	@RequestMapping("/login")
	public ModelAndView qamanagerLogin() {
		ModelAndView model = new ModelAndView("login");
		model.addObject("role", "qamanager");
		model.addObject("displayName", "QA Manager");
		return model;
	}

	@RequestMapping("/contributions")
	public ModelAndView get_contributions() {
		try {
			Person qamanager = qm.getQAManagerSession();
			ModelAndView model = new ModelAndView("list_files");
			model.addObject("departments", dm.getDepartments());
			model.addObject("qaManager", qamanager);
			return model;
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/qamanager/login");
		}

	}

	@PostMapping("/authorization")
	@ResponseBody
	public ResponseEntity<ApiResponse> check_login(@RequestParam("username") String user_name,
			@RequestParam("password") String password) {
		Person p = new Person();
		p = am.QAManagerLogin(user_name, password);
		if (user_name.isEmpty() && password.isEmpty()) {
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, "Please fill all fields");
		} else if (p.getId() != 0) {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			HttpSession session = request.getSession(true);
			p.setPerson_picture("/image/" + p.getPerson_picture());
			session.setAttribute("QAManager", p);
			return new ApiResponse().send(HttpStatus.ACCEPTED, "/tag/listCategory");
		} else {
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid username and password!");
		}
	}

	@RequestMapping("/chart")
	public ModelAndView chartPage() {
		ModelAndView mv = new ModelAndView("chart");
		mv.addObject("numberOfIdeas", rm.NummberOfIdeas());
		mv.addObject("numberOfContributor", rm.NummberOfContributor());
		mv.addObject("percentageOfIdeas", rm.PercentageOfIdeas());
		mv.addObject("ideasWithoutComment", rm.IdeasWithoutComment());
		mv.addObject("anonymousIdeaAndComment", rm.anonymousIdeaAndComment());
		return mv;
	}

	@PostMapping("/external_login")
	@ResponseBody
	public ResponseEntity<ApiResponse> external_login(@RequestParam("email") String email,
			RedirectAttributes redirectAttributes) {
		Person p = elm.isExist(email);
		if (p.getId() != 0) {
			if (p.getPerson_role() != 3) {
				return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR,
						"Your QA Manager account doesn't exist, contact administrator for more technical supports");
			} else {
				HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
						.getRequest();
				HttpSession session = request.getSession(true);
				session.setAttribute("QAManager", p);
				return new ApiResponse().send(HttpStatus.ACCEPTED, "/tag/listCategory");
			}
		} else {
			return new ApiResponse().send(HttpStatus.INTERNAL_SERVER_ERROR,
					"Your QA Manager account doesn't exist, contact administrator for more technical supports");
		}
	}

	@GetMapping("/logout")
	public ModelAndView logout(RedirectAttributes redirectAttributes) {
		ModelAndView model = new ModelAndView("redirect:/qamanager/login");
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			HttpSession session = request.getSession(false);
			session.removeAttribute("QAManager");
		} catch (Exception e) {
			return model;
		}
		return model;
	}

	@PostMapping("/zip")
	@ResponseBody
	public ResponseEntity<byte[]> downloadzipfile(@RequestParam(value = "file_id", required = false) List<String> ids,
			RedirectAttributes redirectAttributes) {
		try {
			if (ids != null) {
				String file_zip_name = write_file_to_zip(iafm.getFileNameByIDs(ids));
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				try {
					FileInputStream inputImage = new FileInputStream(file_zip_name);
					byte[] buffer = new byte[512];
					int l = inputImage.read(buffer);
					while (l >= 0) {
						outputStream.write(buffer, 0, l);
						l = inputImage.read(buffer);
					}
					outputStream.close();
					inputImage.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				HttpHeaders headers = new HttpHeaders();
				headers.set("Content-Type", "application/zip");
				headers.set("Content-Disposition", "attachment; filename=\"" + file_zip_name + "\"");
				File f = new File(file_zip_name);
				if (f.exists()) {
					f.delete();
				}

				return new ResponseEntity<byte[]>(outputStream.toByteArray(), headers, HttpStatus.OK);
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@RequestMapping("/downloadfile/{name}")
	public ResponseEntity<byte[]> downloadFile(@PathVariable("name") String name) throws IOException {
		Idea_attachfiles ia = iafm.getFileNameByID(helper.decodeID(name));
		FileInputStream inputImage = new FileInputStream(ia.getLink() + ia.getNew_name());
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[512];
		int l = inputImage.read(buffer);
		while (l >= 0) {
			outputStream.write(buffer, 0, l);
			l = inputImage.read(buffer);
		}
		inputImage.close();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", getContent_Type(ia.getFile_type()));
		headers.set("Content-Disposition", "attachment; filename=\"" + ia.getOld_name() + "\"");
		return new ResponseEntity<byte[]>(outputStream.toByteArray(), headers, HttpStatus.OK);
	}

	private String getContent_Type(String file_extension) {
		String value = "";
		if (file_extension.equalsIgnoreCase("pdf")) {
			value = "application/pdf";
		} else if (file_extension.equalsIgnoreCase("png") || file_extension.equalsIgnoreCase("jpg")
				|| file_extension.equalsIgnoreCase("jpeg")) {
			value = "image/jpeg";
		} else if (file_extension.equalsIgnoreCase("xls")) {
			value = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		} else if (file_extension.equalsIgnoreCase("doc")) {
			value = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
		}
		return value;
	}

	private String write_file_to_zip(List<Idea_attachfiles> listFile) {
		String file_name = "/file/" + System.currentTimeMillis() + ".zip";
		try {
			FileOutputStream fos = new FileOutputStream(file_name);
			ZipOutputStream zipOut = new ZipOutputStream(fos);
			for (Idea_attachfiles srcFile : listFile) {
				File fileToZip = new File(srcFile.getLink().substring(1) + srcFile.getNew_name());
				FileInputStream fis = new FileInputStream(fileToZip);
				ZipEntry zipEntry = new ZipEntry(srcFile.getOld_name());
				zipOut.putNextEntry(zipEntry);
				byte[] bytes = new byte[1024];
				int length;
				while ((length = fis.read(bytes)) >= 0) {
					zipOut.write(bytes, 0, length);
				}
				fis.close();
			}
			zipOut.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file_name;
	}
}
