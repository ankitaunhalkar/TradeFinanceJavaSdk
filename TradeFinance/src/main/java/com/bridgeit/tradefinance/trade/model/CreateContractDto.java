package com.bridgeit.tradefinance.trade.model;

public class CreateContractDto {

	private String contract_id;
	
	private String contract_Description;

	private double contract_amount;

	private String contract_importer;

	private String contract_exporter;

	private String contract_importerbank;

	private String contract_insurance;

	private String contract_custom;

	private String contract_loadingport;

	private String contract_entryport;

	private String billofLading;

	private String letterofCredit;
	
	public String getContract_id() {
		return contract_id;
	}

	public void setContract_id(String contract_id) {
		this.contract_id = contract_id;
	}

	public String getContract_Description() {
		return contract_Description;
	}

	public void setContract_Description(String contract_Description) {
		this.contract_Description = contract_Description;
	}

	public double getContract_amount() {
		return contract_amount;
	}

	public void setContract_amount(double contract_amount) {
		this.contract_amount = contract_amount;
	}

	public String getContract_importer() {
		return contract_importer;
	}

	public void setContract_importer(String contract_importer) {
		this.contract_importer = contract_importer;
	}

	public String getContract_exporter() {
		return contract_exporter;
	}

	public void setContract_exporter(String contract_exporter) {
		this.contract_exporter = contract_exporter;
	}

	public String getContract_importerbank() {
		return contract_importerbank;
	}

	public void setContract_importerbank(String contract_importerbank) {
		this.contract_importerbank = contract_importerbank;
	}

	public String getContract_insurance() {
		return contract_insurance;
	}

	public void setContract_insurance(String contract_insurance) {
		this.contract_insurance = contract_insurance;
	}

	public String getContract_custom() {
		return contract_custom;
	}

	public void setContract_custom(String contract_custom) {
		this.contract_custom = contract_custom;
	}

	public String getContract_loadingport() {
		return contract_loadingport;
	}

	public void setContract_loadingport(String contract_loadingport) {
		this.contract_loadingport = contract_loadingport;
	}

	public String getContract_entryport() {
		return contract_entryport;
	}

	public void setContract_entryport(String contract_entryport) {
		this.contract_entryport = contract_entryport;
	}

	public String getBillofLading() {
		return billofLading;
	}

	public void setBillofLading(String billofLading) {
		this.billofLading = billofLading;
	}

	public String getLetterofCredit() {
		return letterofCredit;
	}

	public void setLetterofCredit(String letterofCredit) {
		this.letterofCredit = letterofCredit;
	}

}
