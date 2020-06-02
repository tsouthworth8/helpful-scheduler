package com.techelevator.model;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.pojo.ShiftRole;

@Component
public class JDBCShiftRoleDAO implements ShiftRoleDAO {
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JDBCShiftRoleDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public boolean saveShiftRole(long companyId, String title) {
		boolean saved = false;
		
		String sqlInsertShiftRole = "INSERT INTO shift_roles (id, company_id, title) VALUES (?, ?, ?)";
		int nextId = getNextShiftRoleId();
		int result = jdbcTemplate.update(sqlInsertShiftRole, nextId, companyId, title);
		
		if (result > 0) {
			saved = true;
		}
		
		return saved;
	}

	@Override
	public List<ShiftRole> getAllShiftRoles(long companyId) {
		String sqlRetrieveAllShiftRoles = "SELECT * FROM shift_roles WHERE company_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlRetrieveAllShiftRoles, companyId);
		
		List<ShiftRole> shiftRoles = new ArrayList<ShiftRole>();
		while(results.next()) {
			ShiftRole role = mapRowToShiftRole(results);
			shiftRoles.add(role);
		}
		
		return shiftRoles;	
	}
	
	@Override
	public List<ShiftRole> getShiftRolesByTemplateId(long templateId) {
		List<ShiftRole> roleList = new ArrayList<ShiftRole>();
		
		String sqlGetRoleIds = "SELECT shift_role_id FROM roles_templates WHERE shift_template_id = ?";
		SqlRowSet idResults = jdbcTemplate.queryForRowSet(sqlGetRoleIds, templateId);
		
		List<Long> roleIdList = new ArrayList<Long>();
		while(idResults.next()) {
			roleIdList.add(idResults.getLong("shift_role_id"));
		}
		
		String sqlGetShiftRoles = "SELECT * FROM shift_roles WHERE id = ?";
		for(long roleId : roleIdList) {
			SqlRowSet result = jdbcTemplate.queryForRowSet(sqlGetShiftRoles, roleId);
			result.next();
			roleList.add(mapRowToShiftRole(result));
		}
		
		return roleList;
	}
	
	@Override
	public ShiftRole getShiftRoleById(int id) {
		String sqlRetrieveShiftRole = "SELECT * FROM shift_roles WHERE id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlRetrieveShiftRole, id);
		results.next();
		
		return mapRowToShiftRole(results);
	}
	
	@Override
	public boolean clearAllShiftRoles(int companyId) {
		boolean answer = false;
		
		String sqlDeleteAllShiftRoles = "DELETE * FROM shift_roles WHERE company_id = ?";
		int results = jdbcTemplate.update(sqlDeleteAllShiftRoles, companyId);
		
		if(results > 0) {
			answer = true;
		}
		return answer;
	}
	
	//HELPER METHODS
	private ShiftRole mapRowToShiftRole(SqlRowSet row) {
		ShiftRole shiftRole = new ShiftRole();
		shiftRole.setId(row.getInt("id"));
		shiftRole.setCompanyId(row.getInt("company_id"));
		shiftRole.setTitle(row.getString("title"));
		
		return shiftRole;
	}
	
	public int getNextShiftRoleId() {
		SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet("SELECT nextval('shift_roles_id_seq'::regclass)");
		if(nextIdResult.next()) {
			return nextIdResult.getInt(1);
		} else {
			throw new RuntimeException("Something went wrong while getting an id for the new shift template.");
		}
	}

}
