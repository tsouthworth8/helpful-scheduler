package com.techelevator.model.templates;

import java.time.LocalTime;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JDBCShiftTemplateDAO implements ShiftTemplateDAO {
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JDBCShiftTemplateDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	

	@Override
	public boolean saveShiftTemplate(int companyId, ShiftTemplate template) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Template> getAllShiftTemplates(int companyId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	//HELPER METHODS
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
