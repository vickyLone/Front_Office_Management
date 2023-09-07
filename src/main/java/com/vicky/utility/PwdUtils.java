package com.vicky.utility;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
public class PwdUtils {

	public String generatePwd() {

		return RandomStringUtils.randomAlphanumeric(5);

	}

}
