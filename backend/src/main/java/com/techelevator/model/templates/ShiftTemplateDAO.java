package com.techelevator.model.templates;

import java.util.List;

public interface ShiftTemplateDAO {
	
	public boolean saveShiftTemplate(int companyId, ShiftTemplate template);
	public List<Template> getAllShiftTemplates(int companyId);

}
