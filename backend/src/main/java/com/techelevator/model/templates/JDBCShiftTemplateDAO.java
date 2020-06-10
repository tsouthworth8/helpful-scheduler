package com.techelevator.model.templates;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.model.ShiftRoleDAO;
import com.techelevator.pojo.ShiftRole;

@Component
public class JDBCShiftTemplateDAO implements ShiftTemplateDAO {
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ShiftRoleDAO srDAO;

	@Autowired
	public JDBCShiftTemplateDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public boolean saveCompanyShiftTemplate(long companyId, ShiftTemplate template) {
		boolean answer = false;
		int nextId = getNextShiftTemplateId();
		
		String sqlSaveShiftTemplate = "INSERT INTO shift_templates (id, start_time, end_time) VALUES (?, ?, ?)";
		int shiftResults = jdbcTemplate.update(sqlSaveShiftTemplate, nextId, template.getStartTime(), template.getEndTime());
		
		String sqlSaveAllowedShiftRoles = "INSERT INTO roles_templates VALUES (?, ?)";
		for(ShiftRole role : template.getAllowedShiftRoles()) {
			jdbcTemplate.update(sqlSaveAllowedShiftRoles, role.getId(), nextId);
		}
		
		String sqlSaveTemplateToCompany = "INSERT INTO shift_templates_company (shift_template_id, company_id) VALUES (?, ?)";
		int companyResults = jdbcTemplate.update(sqlSaveTemplateToCompany, nextId, companyId);
		
		if (shiftResults > 0 && companyResults > 0) {
			answer = true;
		}
		
		return answer;
	}
	
	public boolean deleteCompanyShiftTemplate(long templateId, long companyId) {
		boolean answer = false;
		
		String sqlDeleteRoleJoin = "DELETE FROM roles_templates WHERE shift_template_id = ?";
		int roleResult = jdbcTemplate.update(sqlDeleteRoleJoin, templateId);
		
		String sqlDeleteCompanyJoin = "DELETE FROM shift_templates_company WHERE shift_template_id = ? AND company_id = ?";
		int companyResult = jdbcTemplate.update(sqlDeleteCompanyJoin, templateId, companyId);
		
		String sqlDeleteShiftTemplate = "DELETE FROM shift_templates WHERE id = ?";
		int templateResult = jdbcTemplate.update(sqlDeleteShiftTemplate, templateId);
		
		if (templateResult > 0 && companyResult > 0 && roleResult > 0) {
			answer = true;
		}
		
		return answer;
	}
	
	@Override
	public boolean saveEmployeeShiftTemplate(long employeeId, ShiftTemplate template) {
		boolean answer = false;
		int nextId = getNextShiftTemplateId();
		
		String sqlSaveShiftTemplate = "INSERT INTO shift_templates (id, start_time, end_time) VALUES (?, ?, ?)";
		int shiftResults = jdbcTemplate.update(sqlSaveShiftTemplate, nextId, template.getStartTime(), template.getEndTime());
		
		String sqlSaveTemplateToEmployee = "INSERT INTO shift_templates_employee (shift_template_id, employee_id) VALUES (?, ?)";
		int employeeResults = jdbcTemplate.update(sqlSaveTemplateToEmployee, nextId, employeeId);
		
		if (shiftResults > 0 && employeeResults > 0) {
			answer = true;
		}
		
		return answer;
	}

	@Override
	public List<ShiftTemplate> getAllShiftTemplates(long companyId) {
		String sqlGetTemplateIds = "SELECT shift_template_id FROM shift_templates_company WHERE company_id = ?";
		SqlRowSet idsResult = jdbcTemplate.queryForRowSet(sqlGetTemplateIds, companyId);
		
		List<Integer> idList = new ArrayList<Integer>();
		while(idsResult.next()) {
			idList.add(idsResult.getInt("shift_template_id"));
		}
		
		List<ShiftTemplate> templateList = new ArrayList<ShiftTemplate>();
		String sqlGetCompanyTemplates = "SELECT * FROM shift_templates WHERE id = ?";
		for(Integer id : idList) {
			SqlRowSet result = jdbcTemplate.queryForRowSet(sqlGetCompanyTemplates, id);
			result.next();
			templateList.add(mapRowToShiftTemplate(result));
		}
		
		return templateList;
	}
	
	
	//HELPER METHODS
	private ShiftTemplate mapRowToShiftTemplate(SqlRowSet result) {
		ShiftTemplate template = new ShiftTemplate();
		List<ShiftRole> roleList = srDAO.getShiftRolesByTemplateId(result.getInt("id"));
		
		template.setId(result.getInt("id"));
		template.setStartTime(result.getTime("start_time").toLocalTime());
		template.setEndTime(result.getTime("end_time").toLocalTime());
		template.setAllowedShiftRoles(roleList);
		return template;
	}
	
	private ShiftTemplate createTestShiftTemplate() {
		ShiftTemplate template = new ShiftTemplate();
		
		LocalTime startTime = LocalTime.parse("01:00:00");
		LocalTime endTime = LocalTime.parse("08:30:00");
		
		template.setId(getNextShiftTemplateId());
		template.setStartTime(startTime);
		template.setEndTime(endTime);
		//template.setAllowedShiftRoles(allowedShiftRoles);
		
		return template;
	}
	
	private int getNextShiftTemplateId() {
		SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet("SELECT nextval('shift_templates_id_seq'::regclass)");
		if(nextIdResult.next()) {
			return nextIdResult.getInt(1);
		} else {
			throw new RuntimeException("Something went wrong while getting an id for the new shift template.");
		}
	}

}
