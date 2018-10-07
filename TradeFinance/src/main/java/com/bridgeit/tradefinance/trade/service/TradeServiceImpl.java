package com.bridgeit.tradefinance.trade.service;



import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.transaction.Transactional;

import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric_ca.sdk.exception.EnrollmentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bridgeit.tradefinance.Exception.UserNotFoundException;
import com.bridgeit.tradefinance.trade.dao.ITradeDao;
import com.bridgeit.tradefinance.trade.model.Contract;
import com.bridgeit.tradefinance.trade.model.CreateContractDto;
import com.bridgeit.tradefinance.trade.model.ResponseContractDto;
import com.bridgeit.tradefinance.user.dao.IUserDao;
import com.bridgeit.tradefinance.user.model.User;
import com.bridgeit.tradefinance.util.TokenUtil;

@Service
public class TradeServiceImpl implements ITradeService {

	@Autowired
	ITradeDao tradeDao;

	@Autowired
	IUserDao userDao;

	@Autowired
	IHfcaService hfservice;

	@Override
	@Transactional
	public ResponseContractDto createcontract(CreateContractDto contract, String token)
			throws EnrollmentException, InvalidArgumentException, Exception {

		ResponseContractDto responsecontract = null;

		long userid = TokenUtil.parseJWT(token);

		User user = userDao.getById(userid);

		if (user != null) {
			Contract createcontract = new Contract(contract);
			createcontract.setUser(user);
			boolean status = invokeChaincode(createcontract, "createContractCC");

			if (status) {
				String savedContractId = tradeDao.save(createcontract);
				if (savedContractId != null) {
					responsecontract=updateContract(savedContractId);
				}
			}
		} else
			throw new UserNotFoundException("User Not Found");

		return responsecontract;
	}

	public ResponseContractDto updateContract(String contractId) throws EnrollmentException, InvalidArgumentException, Exception {
		ResponseContractDto responsecontract = null;

			Contract contract = tradeDao.getById(contractId);

			if (contract != null) {
				boolean customstatus = invokeChaincode(contract, "customAcceptanceCC");

				if (customstatus) {
					contract.setContract_customstatus(true);
					tradeDao.update(contract);
					Contract customupdatedcontract = tradeDao.getById(contractId);
					boolean insurancestatus = invokeChaincode(customupdatedcontract, "insuranceAcceptanceCC");

					if (insurancestatus) {
						customupdatedcontract.setContract_insurancestatus(true);
						tradeDao.update(customupdatedcontract);
						Contract insuranceupdatedcontract = tradeDao.getById(contractId);
						boolean ibstatus = invokeChaincode(insuranceupdatedcontract,
								"importerBankAcceptanceCC");

						if (ibstatus) {
							insuranceupdatedcontract.setContract_importerbankstatus(true);
							tradeDao.update(insuranceupdatedcontract);
							Contract ibupdatedcontract = tradeDao.getById(contractId);
							boolean transaction = invokeChaincode(ibupdatedcontract, "transferamountCC");

							if (transaction) {
								responsecontract = new ResponseContractDto(ibupdatedcontract);
								return responsecontract;
							}
						}
					}
				}
			}
			return responsecontract;		
	}
	
	@Override
	public String uploadfile(MultipartFile file) {
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(
						"/home/bridgeit/eclipse-workspace/tradefinance/TradeFinance/src/main/java/com/bridgeit/tradefinance/documents/"
								+ file.getOriginalFilename())));
				stream.write(bytes);
				stream.close();

				return "http://localhost:8080/tradefinance/getfile/" + file.getOriginalFilename();
			} catch (Exception e) {
				return "You failed to upload " + e.getMessage();
			}
		} else {
			return "You failed to upload because the file was empty.";
		}
	}
	
	@Override
	public ByteArrayResource loadfile(String filename) {
		String downloadFolder = "/home/bridgeit/eclipse-workspace/tradefinance/TradeFinance/src/main/java/com/bridgeit/tradefinance/documents/";
		Path file = Paths.get(downloadFolder, filename);

		byte[] data = null;
		ByteArrayResource resource = null;
		try {
			data = Files.readAllBytes(file);
			resource = new ByteArrayResource(data);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return resource;
	}
	
	public boolean invokeChaincode(Contract contract, String function)
			throws EnrollmentException, InvalidArgumentException, Exception {

		if (function.equals("createContractCC")) {
			
			String[] args = { contract.getContract_id(), contract.getContract_Description(),
					contract.getContract_amount() + "", contract.getContract_importer(), contract.getContract_exporter(),
					contract.getContract_importerbank(), contract.getContract_insurance(), contract.getContract_custom(),
					contract.getContract_loadingport(), contract.getContract_entryport(), "false", "false", "false",
					contract.getBillofLading(), contract.getLetterofCredit() };
			
			boolean status = hfservice.invokeBlockChain("createContract", args);
			String[] args1 = { contract.getContract_id() };
			hfservice.queryBlockChain("getContract", args1);
			return status;
						
		} else if (function.equals("customAcceptanceCC")) {

			String[] args = { contract.getContract_id() };
			boolean status = hfservice.invokeBlockChain("customAcceptance", args);
		
			return status;
			
		} else if (function.equals("insuranceAcceptanceCC")) {
			
			String[] args = { contract.getContract_id() };
			boolean status = hfservice.invokeBlockChain("insuranceAcceptance", args);
			
			return status;
			
			
		} else if (function.equals("importerBankAcceptanceCC")) {
			
			String[] args = { contract.getContract_id() };
			boolean status = hfservice.invokeBlockChain("importerBankAcceptance", args);
		
			return status;
			
		} else if (function.equals("transferamountCC")) {
			
			String[] args = { contract.getContract_id() };
			boolean status = hfservice.invokeBlockChain("transferamount", args);
			
			String[] args1 = { contract.getContract_custom() };
			hfservice.queryBlockChain("getBalance", args1);
			
			String[] args2 = { contract.getContract_importer() };
			hfservice.queryBlockChain("getBalance", args2);
			
			String[] args3 = { contract.getContract_exporter()};
			hfservice.queryBlockChain("getBalance", args3);
			
			return status;
			
		}

		return false;
	}

}
