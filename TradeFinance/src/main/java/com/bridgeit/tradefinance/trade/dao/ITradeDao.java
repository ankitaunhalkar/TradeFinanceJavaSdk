package com.bridgeit.tradefinance.trade.dao;

import com.bridgeit.tradefinance.trade.model.Contract;

public interface ITradeDao {
	
	public String save(Contract contract);

	public boolean update(Contract contract);

	public Contract getById(String string);
	
}
