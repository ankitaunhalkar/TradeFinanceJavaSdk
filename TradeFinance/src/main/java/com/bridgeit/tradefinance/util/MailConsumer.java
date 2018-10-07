package com.bridgeit.tradefinance.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import com.bridgeit.tradefinance.user.service.IUserService;

@Service
public class MailConsumer {
	@Autowired
	IUserService userService;

	@JmsListener(destination = "email-queue")
	public void receiveMail(final Mail mailObj) {

		userService.mailSender(mailObj);
		
	}
}
