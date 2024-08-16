package com.synectiks.app.service;

import com.synectiks.app.entity.SignUpDetails;

public interface SignupServiceInterface {

	String Signup(SignUpDetails signupdetails);

	void sendOtpToUser(String email) throws Exception;

	boolean validateOtp(String email, String otp);

	void resetPassword(String email, String otp, String newPassword);

	boolean signIn(String email, String password);

}
