package com.vicky.controller;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.vicky.binder.ForgotPwdForm;
import com.vicky.binder.LoginForm;
import com.vicky.binder.SignUpForm;
import com.vicky.binder.UnlockForm;
import com.vicky.constants.AppConstants;
import com.vicky.props.ApplicationProperties;
import com.vicky.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ApplicationProperties props;


	@GetMapping("/login")
	public String loginformPage(Model model) {

		LoginForm login = new LoginForm();
		model.addAttribute(AppConstants.LOGIN, login);

		return AppConstants.LOGIN;

	}

	@PostMapping("/login")
	public String login(@ModelAttribute(AppConstants.LOGIN) LoginForm login, BindingResult result, Model model, HttpSession session) {
		
		   String status = userService.userLogin(login);
		     
		     if(status.contains(AppConstants.SUCCESS)) {
		    	 return "redirect:/dashboard";
		     }
		     
		model.addAttribute(AppConstants.ERROR_MSG,status);
		
		
		return AppConstants.LOGIN; 
		

	}

	// handler method to handle user registration form request
	@GetMapping("/signup")
	public String showRegistrationForm(Model model) {
		// create model object to store form data
		SignUpForm signup = new SignUpForm();
		model.addAttribute(AppConstants.SIGNUP, signup);
		return AppConstants.SIGNUP;
	}

	@PostMapping("/signup")
	public String signup(@Valid @ModelAttribute(AppConstants.SIGNUP) SignUpForm signup, BindingResult result, Model model)
			throws MessagingException {

		boolean status = userService.saveUser(signup);

		if (status) {
			model.addAttribute(AppConstants.SUCCESS_MSG,props.getMessages().get(AppConstants.SIGNUP_SCC_MSG) );
		} else {
			model.addAttribute(AppConstants.ERROR_MSG,props.getMessages().get(AppConstants.SIGNUP_ERR_MSG));
		}
		return AppConstants.SIGNUP;

	}

	@GetMapping("/unlock")
	public String unlockForm(Model model) {
		UnlockForm unlock = new UnlockForm();
		model.addAttribute(AppConstants.UNLOCKED, unlock);
		return AppConstants.UNLOCKED;
	}

	@PostMapping("/unlock")
	public String unlockUser(@Valid @ModelAttribute(AppConstants.UNLOCKED) UnlockForm unlock, BindingResult result, Model model) {

		boolean status = userService.unlockUser(unlock);

		if (status) {
			model.addAttribute(AppConstants.SUCCESS_MSG, props.getMessages().get(AppConstants.UNLOCK_SCC_MSG));
		} else {
			model.addAttribute(AppConstants.ERROR_MSG, props.getMessages().get(AppConstants.UNLOCK_ERR_MSG));
		}

		return AppConstants.UNLOCKED;

	}

	@GetMapping("/forgot")
	public String forgetPwdPage(Model model) {

		ForgotPwdForm forgotPwd = new ForgotPwdForm();
		model.addAttribute(AppConstants.FORGET_PZZWD, forgotPwd);

		return AppConstants.FORGET_PZZWD;

	}

	@PostMapping("/forgot")
	public String forgotPwd(@Valid @ModelAttribute(AppConstants.FORGET_PZZWD) ForgotPwdForm forgotPwd, BindingResult result,
			Model model) throws MessagingException {

		Boolean status = userService.forgotPwd(forgotPwd);

		if (status) {
			model.addAttribute(AppConstants.SUCCESS_MSG, props.getMessages().get(AppConstants.FORGOT_PZD_SCC_MSG));
		} else {
			model.addAttribute(AppConstants.ERROR_MSG, props.getMessages().get(AppConstants.FORGOT_PZD_ERR_MSG));
		}

		return AppConstants.FORGET_PZZWD;
	}

}
