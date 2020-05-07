package com.techelevator.templates;

import java.util.List;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.model.JDBCShiftRoleDAO;
import com.techelevator.model.ShiftRole;
import com.techelevator.model.ShiftRoleDAO;

@Component
public class JDBCShiftRoleDAOTest {
	
	private ShiftRoleDAO srDAO;
	private DataSource datasource;
	private static JdbcTemplate jdbcTemplate;
	
	@Before
	public void before() {
		srDAO = new JDBCShiftRoleDAO(datasource);
		jdbcTemplate = new JdbcTemplate(datasource);
	}
	
	@Test
	public void insert_new_shift_role() {
		ShiftRole testRole = createTestShiftRole();
		srDAO.saveShiftRole(testRole);
		
		List<ShiftRole> list = srDAO.getAllShiftRoles(99);
		Assert.assertEquals(1, list.size());
		Assert.assertEquals("Shift Supervisor", list.get(0).getTitle());
		
		
	}
	
	//HELPER METHODS
	private static int getNextShiftRoleId() {
		SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet("SELECT nextval('shift_roles_id_seq'::regclass)");
		if(nextIdResult.next()) {
			return nextIdResult.getInt(1);
		} else {
			throw new RuntimeException("Something went wrong while getting an id for the new shift template.");
		}
	}
	
	private static ShiftRole createTestShiftRole() {
		ShiftRole role = new ShiftRole(getNextShiftRoleId(), 99, "Shift Supervisor");
		return role;
	}

}
