package com.vicky.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vicky.binder.DashboardResponse;
import com.vicky.binder.EnquiryForm;
import com.vicky.binder.EnquirySearchCriteriaForm;
import com.vicky.binder.LoginForm;
import com.vicky.constants.AppConstants;
import com.vicky.entity.StudentEnqEntity;
import com.vicky.props.ApplicationProperties;
import com.vicky.repositaries.StudentEnqRepositary;
import com.vicky.service.EnquiryService;

@Controller
public class EnquiryController {

	@Autowired
	private EnquiryService enqService;

	@Autowired
	private HttpSession session;

	@Autowired
	private StudentEnqRepositary studentRepo;
	
	@Autowired
	private ApplicationProperties props;

	@GetMapping("/addEnquiry")
	public String loadEnquiryForm(Model model) {

		model.addAttribute(AppConstants.ADD_ENQUIRY, new EnquiryForm());
		addEnqInit(model);

		return AppConstants.ADD_ENQUIRY;

	}

	@PostMapping("/addEnquiry")
	public String addEnquiry(@Valid @ModelAttribute(AppConstants.ADD_ENQUIRY) EnquiryForm enquiry, BindingResult result,
			Model model, HttpSession session) {

		Boolean status = enqService.addEnquiry(enquiry);

		if (status) {
			model.addAttribute(AppConstants.SUCCESS_MSG, props.getMessages().get(AppConstants.ADD_ENQUIRY_SCC_MSG));
		} else {
			model.addAttribute(AppConstants.ERROR_MSG, props.getMessages().get(AppConstants.ADD_ENQUIRY_ERR_MSG));

		}

		return AppConstants.ADD_ENQUIRY;

	}

	private void addEnqInit(Model model) { 
		model.addAttribute("courseName", enqService.getCourses());
		model.addAttribute("enqStatus", enqService.getEnqStatus());
	}

	@GetMapping("/viewEnquiry")
	public String viewEnquiryPage(Model model, EnquirySearchCriteriaForm searchCriteriaForm) {

		Integer userId = (Integer) session.getAttribute(AppConstants.USER_ID);
		List<StudentEnqEntity> enqDetails = enqService.getEnqDetails(userId, searchCriteriaForm);

		model.addAttribute(AppConstants.VIEW_ENQUIRY, enqDetails);
		addEnqInit(model);

		return AppConstants.VIEW_ENQUIRY;

	}

	@GetMapping("/filtered-enq")
	public String viewEnquiry(@RequestParam("cname") String cname, @RequestParam("status") String status,
			@RequestParam("mode") String mode, Model model) {

		EnquirySearchCriteriaForm searchEnquiry = new EnquirySearchCriteriaForm();

		searchEnquiry.setCourseName(cname);
		searchEnquiry.setEnqStatus(status);
		searchEnquiry.setClassMode(mode);

		Integer userId = (Integer) session.getAttribute(AppConstants.USER_ID);

		List<StudentEnqEntity> enqDetails = enqService.getEnqDetails(userId, searchEnquiry);

		model.addAttribute(AppConstants.VIEW_ENQUIRY, enqDetails);

		return "filteredEnqs";

	}

	@GetMapping("/dashboard")
	public String viewDashboard(Model model, HttpSession session) {

		Integer userId = (Integer) session.getAttribute(AppConstants.USER_ID);

		DashboardResponse dashboardData = enqService.getDashboardRecords(userId);

		model.addAttribute("dashboardData", dashboardData);

		return "dashboard";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session, Model model) {
		session.invalidate();
		LoginForm login = new LoginForm();
		model.addAttribute(AppConstants.LOGIN, login);
		return "redirect:/login";
	}

	@GetMapping("/edit")
	public String editform(  @RequestParam("enqId") Integer enqId, Model model) {


		Optional<StudentEnqEntity> findById = studentRepo.findById(enqId);

		if (findById.isPresent()) {

			model.addAttribute(AppConstants.ADD_ENQUIRY, findById);
			addEnqInit(model);


		}

		return AppConstants.ADD_ENQUIRY;

	}

}
