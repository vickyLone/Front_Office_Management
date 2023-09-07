package com.vicky.service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vicky.binder.ForgotPwdForm;
import com.vicky.binder.LoginForm;
import com.vicky.binder.SignUpForm;
import com.vicky.binder.UnlockForm;
import com.vicky.constants.AppConstants;
import com.vicky.entity.UserDtlsEntity;
import com.vicky.props.ApplicationProperties;
import com.vicky.repositaries.UserDtlsRepositary;
import com.vicky.utility.EmailUtils;
import com.vicky.utility.PwdUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDtlsRepositary userRepo;

	@Autowired
	private PwdUtils pwd;

	@Autowired
	private EmailUtils emailOtp;

	@Autowired
	private HttpSession session;

	@Autowired
	private ApplicationProperties props;

	@Override
	public Boolean saveUser(SignUpForm signup) throws MessagingException {

		UserDtlsEntity user = userRepo.findByEmail(signup.getEmail());
		if (user != null) {
			return false;
		}

		UserDtlsEntity userEntity = new UserDtlsEntity();
		BeanUtils.copyProperties(signup, userEntity);
		String newPwd = pwd.generatePwd();
		userEntity.setPwd(newPwd);
		userEntity.setUserStatus(AppConstants.LOCKED);
		userRepo.save(userEntity);
		String to = signup.getEmail();

		String subject = "Unlock Your AshokIt Account";

		StringBuilder body = new StringBuilder();

		body.append("<h2>Hi You have New Email</h2> </br> <h3> Unlock your Account. Your OTP is : </h3>" + newPwd);

		body.append("<h3><a href=\"http://localhost:8080/unlock?email=" + to + "\"> Unlock Here </a></h3>");

		emailOtp.generateEmail(to, subject, body.toString());

		return true;

	}

	@Override
	public String userLogin(LoginForm login) {
		UserDtlsEntity entity = userRepo.findByEmailAndPwd(login.getEmail(), login.getPwd());

		if (entity == null) {
			return props.getMessages().get(AppConstants.LOGIN_INVALID_CREDENTIALS_MSG);
		}

		if (entity.getUserStatus().equals(AppConstants.LOCKED)) {
			return props.getMessages().get(AppConstants.LOGIN_ACCOUNT_LOCKED_MSG);
		}

		session.setAttribute(AppConstants.USER_ID, entity.getUserId());

		return AppConstants.SUCCESS;

	}

	@Override
	public Boolean unlockUser(UnlockForm unlock) {

		if (!(unlock.getNewPwd()).equals(unlock.getConfirmPwd())) {

			return false;
		} 
		UserDtlsEntity dtls = new UserDtlsEntity();
			dtls.setPwd(unlock.getNewPwd());
			dtls.setUserStatus(AppConstants.UNLOCKED);
		
		userRepo.save(dtls);

		return userRepo.findByPwd(unlock.getPwd())==null;
	}

	@Override
	public Boolean forgotPwd(ForgotPwdForm forgotPwd) throws MessagingException {

		UserDtlsEntity userEntity = userRepo.findByEmail(forgotPwd.getEmail());

		if (userEntity == null) {

			return false;
		}

		String userPwd = userEntity.getPwd();

		String to = userEntity.getEmail();

		String subject = "Recover Your Password";

		StringBuilder body = new StringBuilder();

		body.append("<h2>Hi You have New Email</h2> </br> <h3> Your Password is : </h3>" + userPwd);

		emailOtp.generateEmail(to, subject, body.toString());

		return true;

	}

}
