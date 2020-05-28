package com.techelevator.templates;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.stereotype.Component;

import com.techelevator.model.CompanyDao;
import com.techelevator.model.JDBCShiftRoleDAO;
import com.techelevator.model.JdbcCompanyDao;
import com.techelevator.model.ShiftRoleDAO;
import com.techelevator.pojo.Company;
import com.techelevator.pojo.ShiftRole;

@Component
public class JDBCShiftRoleDAOTest {
	
	private static SingleConnectionDataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	private ShiftRoleDAO srDAO;
	private CompanyDao compDAO;
	
	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/schedule");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		/* The following line disables autocommit for connections
		 * returned by this DataSource. This allows us to rollback
		 * any changes after each test */
		dataSource.setAutoCommit(false);
	}
	
//	@Before
//	public void before() {
//		jdbcTemplate = new JdbcTemplate(dataSource);
//		srDAO = new JDBCShiftRoleDAO(dataSource);
//		compDAO = new JdbcCompanyDao(dataSource);
//		
//		Company company = compDAO.createTestCompany();
//		String sqlInsertCompany = "INSERT INTO company VALUES (?, ?)";
//		int result = jdbcTemplate.update(sqlInsertCompany, company.getId(), company.getName());
//	}
//	
//	@After
//	public void after() {
//		compDAO.deleteCompanyById(99);
//		srDAO.clearAllShiftRoles(99);
//	}
//	
//	@Test
//	public void insert_new_shift_role_returns_additional_shift_role() {
//		int initial = srDAO.getAllShiftRoles(99).size();
//		
//		ShiftRole testRole = createNextTestShiftRole();
//		srDAO.saveShiftRole(testRole);
//		List<ShiftRole> list = srDAO.getAllShiftRoles(99);
//		
//		Assert.assertEquals(initial + 1, list.size());	
//	}
//	
//	@Test
//	public void get_shift_role_by_id_returns_correct_role() {
//		ShiftRole testRole = createTestShiftRole();
//		srDAO.saveShiftRole(testRole);
//		
//		ShiftRole returnedRole = srDAO.getShiftRoleById(420);
//		Assert.assertEquals(testRole.getTitle(), returnedRole.getTitle());
//	}
//	
//	//HELPER METHODS
//	public ShiftRole createNextTestShiftRole() {
//		ShiftRole role = new ShiftRole(srDAO.getNextShiftRoleId(), 99, "Shift Supervisor");
//		srDAO.saveShiftRole(role);
//		return role;
//	}
//	public ShiftRole createTestShiftRole() {
//		ShiftRole role = new ShiftRole(420, 99, "Captain Dipstick");
//		srDAO.saveShiftRole(role);
//		return role;
//	}

}
