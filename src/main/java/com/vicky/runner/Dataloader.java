package com.vicky.runner;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.vicky.constants.AppConstants;
import com.vicky.entity.CoursesEntity;
import com.vicky.entity.EnqStatusEntity;
import com.vicky.repositaries.CoursesRepositary;
import com.vicky.repositaries.EnquiryStatusRepositary;

@Component
public class Dataloader implements ApplicationRunner {

	@Autowired
	private CoursesRepositary courseRepo;

	@Autowired
	private EnquiryStatusRepositary statusRepo;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		courseRepo.deleteAll();

		CoursesEntity course1 = new CoursesEntity();
		course1.setCourseName("Java");

		CoursesEntity course2 = new CoursesEntity();
		course2.setCourseName("DevOps");

		CoursesEntity course3 = new CoursesEntity();
		course3.setCourseName("Python");

		courseRepo.saveAll(Arrays.asList(course1, course2, course3));
		
		statusRepo.deleteAll();

		EnqStatusEntity status1 = new EnqStatusEntity();
		status1.setEnqStatus("New");

		EnqStatusEntity status2 = new EnqStatusEntity();
		status2.setEnqStatus(AppConstants.ENROLLED);

		EnqStatusEntity status3 = new EnqStatusEntity();
		status3.setEnqStatus(AppConstants.LOST);

		statusRepo.saveAll(Arrays.asList(status1, status2, status3));

	}

}
