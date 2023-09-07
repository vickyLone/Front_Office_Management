package com.vicky.service;

import java.util.List;

import com.vicky.binder.DashboardResponse;
import com.vicky.binder.EnquiryForm;
import com.vicky.binder.EnquirySearchCriteriaForm;
import com.vicky.entity.StudentEnqEntity;

public interface EnquiryService {

	public Boolean addEnquiry(EnquiryForm enquiry);
	
	public List<StudentEnqEntity> getEnqDetails(Integer userId,EnquirySearchCriteriaForm criteriaSearch);
	
	public List<String> getCourses();
	
	public List<String> getEnqStatus();
	
	public DashboardResponse getDashboardRecords(Integer userId);
	
	
}
