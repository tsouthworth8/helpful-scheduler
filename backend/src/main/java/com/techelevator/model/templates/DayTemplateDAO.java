package com.techelevator.model.templates;

import java.util.List;

public interface DayTemplateDAO {
	
	public boolean saveDayTemplate(long companyId, DayTemplate template);
	public List<DayTemplate> getAllDayTemplates(long companyId);

}
