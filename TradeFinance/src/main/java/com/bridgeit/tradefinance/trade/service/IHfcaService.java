package com.bridgeit.tradefinance.trade.service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric_ca.sdk.exception.EnrollmentException;

import com.bridgeit.tradefinance.trade.model.AppUser;

public interface IHfcaService {
	
	void queryBlockChain(String function, String[] args) throws ProposalException, InvalidArgumentException;
	boolean invokeBlockChain(String function, String[] args) throws ProposalException, InvalidArgumentException,
			InterruptedException, ExecutionException, TimeoutException;
}
