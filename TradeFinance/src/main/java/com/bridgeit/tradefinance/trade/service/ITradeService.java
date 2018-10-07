package com.bridgeit.tradefinance.trade.service;

import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric_ca.sdk.exception.EnrollmentException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import com.bridgeit.tradefinance.trade.model.CreateContractDto;
import com.bridgeit.tradefinance.trade.model.ResponseContractDto;

public interface ITradeService {
	ResponseContractDto createcontract(CreateContractDto contract, String token) throws EnrollmentException, InvalidArgumentException, Exception;

	String uploadfile(MultipartFile file);

	ByteArrayResource loadfile(String filename);
}