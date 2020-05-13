package com.techelevator.model;

import com.techelevator.pojo.Company;

public interface CompanyDao {
	
	public Company addCompany (String companyName);
	public long getCompanyIdByName (String companyName);
	public String getCompanyNameById(long companyId);

}
