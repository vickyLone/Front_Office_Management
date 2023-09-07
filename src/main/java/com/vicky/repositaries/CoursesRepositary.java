package com.vicky.repositaries;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vicky.entity.CoursesEntity;

public interface CoursesRepositary extends JpaRepository<CoursesEntity, Integer> {
     
	@Query("Select distinct(courseName) from CoursesEntity")
	public List<String> getCourseNames();
	
}
