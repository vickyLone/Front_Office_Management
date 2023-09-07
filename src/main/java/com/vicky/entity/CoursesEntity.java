package com.vicky.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class CoursesEntity {
      
	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Integer courseId;
      private String courseName;
	
}
