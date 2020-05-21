package com.techelevator.model.templates;

import java.util.List;

public interface ShiftTemplateDAO {
	
	public boolean saveCompanyShiftTemplate(int companyId, ShiftTemplate template);
	public boolean saveEmployeeShiftTemplate(int employeeId, ShiftTemplate template);
	public List<ShiftTemplate> getAllShiftTemplates(int companyId);

}
