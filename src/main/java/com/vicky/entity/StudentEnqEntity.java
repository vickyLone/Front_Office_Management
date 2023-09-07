package com.vicky.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
@Entity
public class StudentEnqEntity {
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer enqId;
	private String studentName;
	private Long phNo;
	private String classMode;
	private String courseName;
	private String enqStatus;
	
	@CreationTimestamp
	private LocalDate createdDate;
	
	@UpdateTimestamp
	private LocalDate updateDate;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private UserDtlsEntity user;
	
	
	
	
}
