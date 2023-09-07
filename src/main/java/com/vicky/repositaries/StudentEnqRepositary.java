package com.vicky.repositaries;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vicky.entity.StudentEnqEntity;

public interface StudentEnqRepositary extends JpaRepository<StudentEnqEntity, Integer> {
	
	
	@Query("select count(*) from StudentEnqEntity where userId = :userId")
	public Integer totalEnquiries(@Param("userId") Integer userId);
	
	
	@Query("select count(*) from StudentEnqEntity where userId = :userId And enqStatus = 'New'")
	public Integer newEnquiries(@Param("userId") Integer userId);
	
	
	@Query("select count(*) from StudentEnqEntity where userId = :userId And enqStatus = 'Lost'")
	public Integer lostEnquiries(@Param ("userId") Integer userId);
	
	

}
