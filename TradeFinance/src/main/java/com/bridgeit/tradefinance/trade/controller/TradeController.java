package com.bridgeit.tradefinance.trade.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hyperledger.fabric_ca.sdk.exception.EnrollmentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bridgeit.tradefinance.trade.model.CreateContractDto;
import com.bridgeit.tradefinance.trade.model.ResponseContractDto;
import com.bridgeit.tradefinance.trade.service.ITradeService;
import com.bridgeit.tradefinance.util.ResponseDto;

@RestController
public class TradeController {

	@Autowired
	ITradeService tradeService;
	
	@RequestMapping(value = "/createcontract", method = RequestMethod.POST)
	ResponseEntity<?> createContract(@RequestBody CreateContractDto contract, HttpServletRequest request) throws EnrollmentException, org.hyperledger.fabric.sdk.exception.InvalidArgumentException, Exception {

		String token = request.getHeader("Authorization");
		ResponseContractDto response = tradeService.createcontract(contract, token);
		if (response != null) {
			ResponseDto res = new ResponseDto();
			res.setCode(200);
			res.setMessage("Created successfully");
			return new ResponseEntity<>(res, HttpStatus.CREATED);
		}
		return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
	}
	
	@RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
	public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {

		String status = tradeService.uploadfile(file);
		// System.out.println(status);
		ResponseDto res = new ResponseDto();
		res.setCode(200);
		res.setMessage(status);
		return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
	}
	
	@RequestMapping(value = "/getfile/{filename:.+}", method = RequestMethod.GET)
	public ResponseEntity<?> loadfile(@PathVariable String filename, HttpServletRequest request,
			HttpServletResponse response) {
		
		ByteArrayResource resource = tradeService.loadfile(filename);
	
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
//	@RequestMapping(value = "/hfca", method = RequestMethod.POST)
//	ResponseEntity<?> hfca() throws EnrollmentException, InvalidArgumentException, Exception {
//
//		AppUser admin = service.getAdmin();
//		service.getUser(admin);
//		HFClient client = service.getHfClient(admin);
//		service.getChannel(client);
//		service.queryBlockChain(client);
//		return new ResponseEntity<>(HttpStatus.OK);
//	}
	
	
}
