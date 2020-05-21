package com.overload.home;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.overload.util.SftpConnector;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		return "view.overload.home";
	}

	@ResponseBody
	@RequestMapping(value = "/upload_local_server", method = RequestMethod.POST)
	public String upload_local_server(MultipartHttpServletRequest request, HttpSession session) {
		// example for String parameter
		String title = request.getParameter("title");
		System.out.println("title: " + title);

		// get File Object at request
		MultipartFile mf = request.getFile("file");

		// get tomcat server path
		String root_path = session.getServletContext().getRealPath("/resources/storage");

		// make to file object
		File f_r = new File(root_path);

		// if root path is not exists, make dir
		if (!f_r.exists())
			f_r.mkdir();

		try {
			//if exists file...
			if (mf != null) {
				//get file's original name
				String video_origin_name = new String(mf.getOriginalFilename().getBytes("8859_1"), "UTF-8");
				//send file to server dir
				mf.transferTo(new File(f_r.getPath(), video_origin_name));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "true";
	}

	@ResponseBody
	@RequestMapping(value = "/upload_sftp_server", method = RequestMethod.POST)
	public String upload_sftp_server(MultipartHttpServletRequest request) {
		// example for String parameter
		String title = request.getParameter("title");
		System.out.println("title: " + title);
		
		//for send file object
		InputStream inputStream = null;
		
		// sftp server connection info
		String sftp_url = "110.12.59.11";
		int sftp_port = 9000;
		String sftp_user = "overload";
		String sftp_password = "Another_11";
		// ==================================

		//sftp connection setting
		SftpConnector sftpConnector = new SftpConnector(sftp_url, sftp_port, sftp_user, sftp_password);
		
		try {
			//get file object at request
			MultipartFile mf = request.getFile("file");
			String origin_name = new String(mf.getOriginalFilename().getBytes("8859_1"), "UTF-8");
			//get Stream to file
			inputStream = mf.getInputStream();

			//send to server
			sftpConnector.sendFile("/home/overload/hdd", inputStream, origin_name);
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// disconnection
			sftpConnector.disconnection();
		}

		return "true";
	}
}
