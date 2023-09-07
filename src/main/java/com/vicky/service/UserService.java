package com.vicky.service;

import javax.mail.MessagingException;

import org.springframework.stereotype.Service;

import com.vicky.binder.ForgotPwdForm;
import com.vicky.binder.LoginForm;
import com.vicky.binder.SignUpForm;
import com.vicky.binder.UnlockForm;

@Service
public interface UserService {
	
	public Boolean saveUser(SignUpForm signup) throws MessagingException;
	public Boolean unlockUser(UnlockForm unlock);
	public Boolean forgotPwd(ForgotPwdForm forgtPwd) throws MessagingException;
	public String userLogin(LoginForm login);
	
}
