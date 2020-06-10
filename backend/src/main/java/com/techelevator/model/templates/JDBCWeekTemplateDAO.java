package com.techelevator.model.templates;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JDBCWeekTemplateDAO implements WeekTemplateDAO {
	
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	WeekTemplateDAO weekDAO;
	
	public JDBCWeekTemplateDAO(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}
	
	
	public boolean saveWeekTemplate(long companyId, WeekTemplate template) {
		boolean response = false;
		int nextId = getNextWeekTemplateId();
		
		String sqlSaveNewWeekTemplate = "INSERT INTO week_templates (id, company_id, nickname) VALUES (?, ?, ?)";
		int result1 = jdbcTemplate.update(sqlSaveNewWeekTemplate, nextId, companyId, template.getNickname());
		
		String sqlSaveWeekDayJoin = "INSERT INTO day_week_templates (day_id, week_id) VALUES (?, ?)";
		for(Integer dayId : template.getDayIds()) {
			jdbcTemplate.update(sqlSaveWeekDayJoin, dayId, nextId);
		}
		
		if(result1 > 0) {
			response = true;
		}
		
		return response;
	}
	
	public List<WeekTemplate> getAllWeekTemplates(long companyId) {
		List<WeekTemplate> list = new ArrayList<WeekTemplate>();
		
		String sqlGetAllWeekTemplates = "SELECT id FROM week_templates WHERE company_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllWeekTemplates, companyId);
		
		while(results.next()) {
			list.add(mapRowToWeekTemplate(results));
		}
		
		return list;
	}
	
	public boolean deleteWeekTemplate(long templateId) {
		boolean response = false;
		
		String sqlDeleteWeekDayJoin = "DELETE * FROM day_week_templates WHERE week_id = ?";
		int result1 = jdbcTemplate.update(sqlDeleteWeekDayJoin, templateId);
		
		String sqlDeleteWeekTemplate = "DELETE * FROM week_templates WHERE id = ?";
		int result2 = jdbcTemplate.update(sqlDeleteWeekTemplate, templateId);
		
		if (result1 > 0 && result2 > 0) {
			response = true;
		}
		
		return response;
	}
	
	//HELPER METHODS
	private WeekTemplate mapRowToWeekTemplate(SqlRowSet result) {
		WeekTemplate template = new WeekTemplate();
		int weekId = result.getInt("id");
		
		template.setId(weekId);
		template.setNickname(result.getString("nickname"));
		template.setDayIds(getDayIds(weekId));
		
		return template;
	}
	
	private int getNextWeekTemplateId() {
		SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet("SELECT nextval('week_templates_id_seq'::regclass)");
		if(nextIdResult.next()) {
			return nextIdResult.getInt(1);
		} else {
			throw new RuntimeException("Something went wrong while getting an id for the new week template.");
		}
	}
	
	private List<Integer> getDayIds(int weekTempId) {
		String sqlGetDayIds = "SELECT day_id FROM day_week_templates WHERE week_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetDayIds, weekTempId);
		
		List<Integer> idList = new ArrayList<Integer>();
		while(results.next()) {
			idList.add(results.getInt("day_id"));
		}
		
		return idList;
	}

}
