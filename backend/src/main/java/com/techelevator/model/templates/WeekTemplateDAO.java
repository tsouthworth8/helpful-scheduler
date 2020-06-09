package com.techelevator.model.templates;

import java.util.List;

public interface WeekTemplateDAO {
	
	public boolean saveWeekTemplate(long companyId, WeekTemplate template);
	public List<WeekTemplate> getAllWeekTemplates(long companyId);
	public boolean deleteWeekTemplate(long templateId);

}
