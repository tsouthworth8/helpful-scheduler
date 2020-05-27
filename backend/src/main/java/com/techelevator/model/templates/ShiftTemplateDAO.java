package com.techelevator.model.templates;

import java.util.List;

public interface ShiftTemplateDAO {
	
	public boolean saveCompanyShiftTemplate(long companyId, ShiftTemplate template);
	public boolean saveEmployeeShiftTemplate(long employeeId, ShiftTemplate template);
	public List<ShiftTemplate> getAllShiftTemplates(long companyId);

}
