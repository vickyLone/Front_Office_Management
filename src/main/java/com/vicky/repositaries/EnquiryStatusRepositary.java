package com.vicky.repositaries;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vicky.entity.EnqStatusEntity;

public interface EnquiryStatusRepositary extends JpaRepository<EnqStatusEntity, Integer> {
	
	@Query("Select distinct(enqStatus)from EnqStatusEntity")
	public List<String> getEnquiryStatus();

}
