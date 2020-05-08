package com.techelevator.templates;

import static org.mockito.Mockito.mock;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techelevator.model.ShiftRoleDAO;
import com.techelevator.pojo.ShiftRole;

@Component
public class JDBCShiftRoleDAOTest {
	
	private ShiftRoleDAO srDAO;
	
	@Before
	public void before() {
		srDAO = mock(ShiftRoleDAO.class);
	}
	
	@Test
	public void insert_new_shift_role() {
		ShiftRole testRole = createTestShiftRole();
		srDAO.saveShiftRole(testRole);
		List<ShiftRole> list = srDAO.getAllShiftRoles(99);
		
		Assert.assertEquals(1, list.size());
		
		
	}
	
	//HELPER METHODS
	
	public ShiftRole createTestShiftRole() {
		ShiftRole role = new ShiftRole(srDAO.getNextShiftRoleId(), 99, "Shift Supervisor");
		return role;
	}

}
