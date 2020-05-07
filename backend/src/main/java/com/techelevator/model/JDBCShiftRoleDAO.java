package com.techelevator.model;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JDBCShiftRoleDAO implements ShiftRoleDAO {
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JDBCShiftRoleDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public boolean saveShiftRole(ShiftRole role) {
		boolean saved = false;
		
		String sqlInsertShiftRole = "INSERT INTO shift_roles (id, company_id, title) VALUES (?, ?, ?)";
		int result = jdbcTemplate.update(sqlInsertShiftRole, role.getId(), role.getCompanyId(), role.getTitle());
		
		if (result > 0) {
			saved = true;
		}
		
		return saved;
	}

	@Override
	public List<ShiftRole> getAllShiftRoles(int companyId) {
		String sqlRetrieveAllShiftRoles = "SELECT id FROM shift_roles WHERE company_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlRetrieveAllShiftRoles, companyId);
		
		List<ShiftRole> shiftRoles = new ArrayList<ShiftRole>();
		while(results.next()) {
			ShiftRole role = mapRowToShiftRole(results);
			shiftRoles.add(role);
		}
		
		return shiftRoles;	
	}
	
	public ShiftRole getShiftRoleById(int id) {
		String sqlRetrieveShiftRole = "SELECT * FROM shift_roles WHERE id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlRetrieveShiftRole, id);
		
		return mapRowToShiftRole(results);
	}
	
	//HELPER METHODS
	private static ShiftRole mapRowToShiftRole(SqlRowSet row) {
		ShiftRole shiftRole = new ShiftRole();
		shiftRole.setId(row.getInt("id"));
		shiftRole.setCompanyId(row.getInt("company_id"));
		shiftRole.setTitle(row.getString("title"));
		
		return shiftRole;
	}

}
