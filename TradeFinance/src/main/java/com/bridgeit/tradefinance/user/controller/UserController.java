package com.bridgeit.tradefinance.user.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric_ca.sdk.exception.EnrollmentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.tradefinance.user.model.LoginDto;
import com.bridgeit.tradefinance.user.model.RegisterDto;
import com.bridgeit.tradefinance.user.service.IUserService;
import com.bridgeit.tradefinance.util.ResponseDto;

/**
 * Handles requests for the application home page.
 */
@RestController
public class UserController {

	@Autowired
	IUserService userService;

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "home";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> register(@Valid @RequestBody RegisterDto user, HttpServletRequest request) throws EnrollmentException, InvalidArgumentException, Exception {

		String url = request.getRequestURL().toString();
		String link = url.substring(0, url.lastIndexOf("/")).concat("/verify/");
		long status = userService.register(user, link);

		if (status > 0) {
			ResponseDto res = new ResponseDto();
			res.setMessage("User registration successfully! Verify your Email Id and Activate your account");
			res.setCode(200);
			System.out.println("success");
			return new ResponseEntity<>(res, HttpStatus.OK);
		}
		System.out.println("Failure");

		return new ResponseEntity<String>("User registration unsuccessfully", HttpStatus.CONFLICT);
	}

	@RequestMapping(value = "/verify/{token:.+}", method = RequestMethod.GET)
	public ResponseEntity<String> verify(@PathVariable String token) {

		System.out.println(token);

		boolean status = userService.verify(token);

		if (status) {
			return new ResponseEntity<String>("You have Successfully Verified your account", HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<String>("Sorry!, Cannot Verify", HttpStatus.EXPECTATION_FAILED);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> login(@Valid @RequestBody LoginDto user, HttpServletResponse header) {

		LoginDto userLoginToken = userService.login(user);

		if (userLoginToken != null) {

			header.setHeader("Authorization", userLoginToken.getToken());
			ResponseDto res = new ResponseDto();
			res.setMessage("Logged in succesfully");
			res.setCode(200);
			return new ResponseEntity<>(res, HttpStatus.OK);

		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
