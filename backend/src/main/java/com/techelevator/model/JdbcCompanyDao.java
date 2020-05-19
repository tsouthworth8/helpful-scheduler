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
		long companyId = getNextCompanyId();
		
		String addCompany = "INSERT INTO company VALUES (?, ?)";
		jdbcTemplate.update(addCompany, companyId, companyName);
		
		company.setId(companyId);
		company.setName(companyName);
		
		return company;
	}
	
	@Override
	public boolean saveCompany(Company company) {
		boolean answer = false;
		
		String sqlInsertCompany = "INSERT INTO company VALUES (?, ?)";
		int result = jdbcTemplate.update(sqlInsertCompany, company.getId(), company.getName());
		
		if (result > 0) {
			answer = true;
		};
		
		return answer;
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
	
	@Override
	public boolean deleteCompanyById(long companyId) {
		boolean answer = false;
		String sqlDeleteCompany = "DELETE FROM shift_roles WHERE company_id = ?; DELETE FROM company WHERE id = ?";
		int results = jdbcTemplate.update(sqlDeleteCompany, companyId, companyId);
		
		if (results > 0) {
			answer = true;
		}
		
		return answer;
	}

	private long getNextCompanyId() {
		String sqlNextId = "SELECT nextval('company_id_seq')";
		SqlRowSet nextId = jdbcTemplate.queryForRowSet(sqlNextId);

		nextId.next();
		return nextId.getLong(1);
	}
	
	
//	@Override
//	public boolean createTestCompany() {
//		boolean answer = false;
//		
//		Company company = new Company();
//		company.setId(99);
//		company.setName("Test Company");
//		
//		String sqlInsertCompany = "INSERT INTO company VALUES (?, ?)";
//		int result = jdbcTemplate.update(sqlInsertCompany, company.getId(), company.getName());
//		
//		if (result > 0) {
//			answer = true;
//		};
//		
//		return answer;
//	}
	
	@Override
	public Company createTestCompany() {
		Company company = new Company();
		company.setId(99);
		company.setName("Test Company");
		
		return company;
	}
	
	

	
}
