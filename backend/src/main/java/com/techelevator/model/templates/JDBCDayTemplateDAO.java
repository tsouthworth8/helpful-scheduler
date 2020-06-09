package com.techelevator.model.templates;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCDayTemplateDAO implements DayTemplateDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	DayTemplateDAO dtDAO;
	
	@Autowired
	public JDBCDayTemplateDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public boolean saveDayTemplate(long companyId, DayTemplate template) {
		boolean response = false;
		int nextId = getNextDayTemplateId();
		
		String sqlSaveDayTemplate = "INSERT INTO day_templates (id, company_id, nickname) VALUES ?, ?, ?";
		int dayResult = jdbcTemplate.update(sqlSaveDayTemplate, nextId, companyId, template.getNickname());
		
		String sqlSaveShifts = "INSERT INTO shift_day_templates (shift_id, day_id) VALUES ?, ?";
		for(Integer shift : template.getShifts()) {
			jdbcTemplate.update(sqlSaveShifts, shift, nextId);
		}
		
		if(dayResult > 0) {
			response = true;
		}
		
		return response;
	}
	
	@Override
	public List<DayTemplate> getAllDayTemplates(long companyId) {
		List<DayTemplate> list = new ArrayList<DayTemplate>();
		
		String sqlGetAllDayTemplates = "SELECT id FROM day_templates WHERE company_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllDayTemplates, companyId);
		
		while(results.next()) {
			list.add(mapRowToDayTemplate(results));
		}
		
		return list;
	}
	
	//HELPER METHODS
	private DayTemplate mapRowToDayTemplate(SqlRowSet result) {
		DayTemplate template = new DayTemplate();
		int dayId = result.getInt("id");
		
		template.setId(dayId);
		template.setNickname(result.getString("nickname"));
		template.setShifts(getShiftIds(dayId));
		
		return template;
	}
	
	private int getNextDayTemplateId() {
		SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet("SELECT nextval('day_templates_id_seq'::regclass)");
		if(nextIdResult.next()) {
			return nextIdResult.getInt(1);
		} else {
			throw new RuntimeException("Something went wrong while getting an id for the new day template.");
		}
	}
	
	private List<Integer> getShiftIds(int dayTempId) {
		String sqlGetShiftIds = "SELECT shift_id FROM shift_day_templates WHERE day_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetShiftIds, dayTempId);
		
		List<Integer> idList = new ArrayList<Integer>();
		while(results.next()) {
			idList.add(results.getInt("shift_id"));
		}
		
		return idList;
	}

}
