package com.vicky.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.vicky.binder.DashboardResponse;
import com.vicky.binder.EnquiryForm;
import com.vicky.binder.EnquirySearchCriteriaForm;
import com.vicky.constants.AppConstants;
import com.vicky.entity.StudentEnqEntity;
import com.vicky.entity.UserDtlsEntity;
import com.vicky.repositaries.CoursesRepositary;
import com.vicky.repositaries.EnquiryStatusRepositary;
import com.vicky.repositaries.StudentEnqRepositary;
import com.vicky.repositaries.UserDtlsRepositary;

@Service
public class EnquiryServiceImpl implements EnquiryService {

	@Autowired
	private StudentEnqRepositary studentRepo;

	@Autowired
	private CoursesRepositary courseRepo;

	@Autowired
	private EnquiryStatusRepositary statusRepo;

	@Autowired
	private UserDtlsRepositary userDtlsRepo;

	@Autowired
	private HttpSession session;

	@Override
	public Boolean addEnquiry(EnquiryForm enquiry) {

		StudentEnqEntity studentEnq = new StudentEnqEntity();
		BeanUtils.copyProperties(enquiry, studentEnq);

		Integer userId = (Integer) session.getAttribute(AppConstants.USER_ID);
		 Optional<UserDtlsEntity> findById = userDtlsRepo.findById(userId);
		 if(findById.isPresent()) {
			 UserDtlsEntity dtlsEntity = findById.get();
			 studentEnq.setUser(dtlsEntity);
				studentRepo.save(studentEnq);

				return true;
		 }
		return false;
		
	}

	@Override
	public List<StudentEnqEntity> getEnqDetails(Integer userId, EnquirySearchCriteriaForm criteriaSearch) {

		StudentEnqEntity enquiry = new StudentEnqEntity();
		UserDtlsEntity entity = new UserDtlsEntity();
		entity.setUserId(userId);
		enquiry.setUser(entity);

		if (null != criteriaSearch.getEnqStatus() && !"".equals(criteriaSearch.getEnqStatus())) {

			enquiry.setEnqStatus(criteriaSearch.getEnqStatus());
		}
		if (null != criteriaSearch.getCourseName() && !"".equals(criteriaSearch.getCourseName())) {

			enquiry.setCourseName(criteriaSearch.getCourseName());
		}
		if (null != criteriaSearch.getClassMode() && !"".equals(criteriaSearch.getClassMode())) {

			enquiry.setClassMode(criteriaSearch.getClassMode());
		}

		Example<StudentEnqEntity> exmp = Example.of(enquiry);
		return studentRepo.findAll(exmp);


	}

	@Override
	public List<String> getCourses() {
		return courseRepo.getCourseNames();

	}

	@Override
	public List<String> getEnqStatus() {
		return statusRepo.getEnquiryStatus();
	}

	@Override
	public DashboardResponse getDashboardRecords(Integer userId) {

		DashboardResponse response = new DashboardResponse();

		Optional<UserDtlsEntity> findById = userDtlsRepo.findById(userId);

		if (findById.isPresent()) {
			UserDtlsEntity userEntity = findById.get();

			List<StudentEnqEntity> enquiries = userEntity.getEnquiries();

			Integer totalCnt = enquiries.size();

			Integer enrolledCnt = enquiries.stream().filter(e -> e.getEnqStatus().equals(AppConstants.ENROLLED))
					.collect(Collectors.toList()).size();

			Integer lostCnt = enquiries.stream().filter(e -> e.getEnqStatus().equals(AppConstants.LOST))
					.collect(Collectors.toList()).size();

			response.setTotalEnquriesCnt(totalCnt);
			response.setEnrolledCnt(enrolledCnt);
			response.setLostCnt(lostCnt);

		}

		return response;
	}

}
