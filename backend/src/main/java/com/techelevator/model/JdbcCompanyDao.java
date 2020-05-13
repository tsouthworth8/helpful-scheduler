package com.techelevator.model;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.pojo.Company;

@Component
public class JdbcCompanyDao implements CompanyDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JdbcCompanyDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Company addCompany(String companyName) {
		Company company = new Company();
		long companyId = getCompanyId();
		
		String addCompany = "INSERT INTO company VALUES (?, ?)";
		jdbcTemplate.update(addCompany, companyId, companyName);
		
		company.setId(companyId);
		company.setName(companyName);
		
		return company;
	}
	
	@Override
	public long getCompanyIdByName(String companyName) {
		
		String getId = "SELECT id FROM company WHERE name = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(getId, companyName);
		
		results.next();
		long id = results.getLong(1);
		
		return id;
	}
	
	@Override
	public String getCompanyNameById(long companyId) {
		
		String getName = "SELECT name FROM company WHERE id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(getName, companyId);
		
		results.next();
		String name = results.getString(1);
		
		return name;
	}

	private long getCompanyId() {
		String sqlNextId = "SELECT nextval('company_id_seq')";
		SqlRowSet nextId = jdbcTemplate.queryForRowSet(sqlNextId);

		nextId.next();
		return nextId.getLong(1);
	}

	
}
