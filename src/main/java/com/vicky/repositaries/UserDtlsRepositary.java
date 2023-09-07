package com.vicky.repositaries;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vicky.entity.UserDtlsEntity;

public interface UserDtlsRepositary extends JpaRepository<UserDtlsEntity, Integer> {
	
	public UserDtlsEntity findByEmail(String email);
	
	public UserDtlsEntity findByPwd(String pwd);
	
    public UserDtlsEntity findByEmailAndPwd(String email, String pwd);
    
    public UserDtlsEntity findByUserId(Integer userId);
    
	
	

}
