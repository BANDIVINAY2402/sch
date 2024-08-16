package com.synectiks.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


import com.synectiks.app.entity.SignUpDetails;
import com.synectiks.app.service.SignupServiceInterface;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
public class SignUpController {
	
	@Autowired
	private SignupServiceInterface ssi;
	
	@PostMapping("signupsss")
	public ResponseEntity<String> uploadCsv(@RequestBody SignUpDetails signupdetails) {
	    try {
	        String response=ssi.Signup(signupdetails);
	        return ResponseEntity.status(HttpStatus.OK).body(response);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
	    }
	}

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        try {
        	System.out.println(email);
        	ssi.sendOtpToUser(email);
        	
            return ResponseEntity.ok("OTP sent to your email.");
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
    @PostMapping("/validate-otp")
    public ResponseEntity<?> validateOtp(@RequestParam String email, @RequestParam String otp) {
        if (ssi.validateOtp(email, otp)) {
            return ResponseEntity.ok("OTP verified.");
        } else {
            return ResponseEntity.status(401).body("Invalid or expired OTP.");
        }
    }
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String email, @RequestParam String otp, @RequestParam String newPassword) {
        try {
        	ssi.resetPassword(email, otp, newPassword);
            return ResponseEntity.ok("Password reset successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    @PostMapping("/sign-in")
    public ResponseEntity<String> signIn(@RequestParam String email, @RequestParam String password) {
        boolean success = ssi.signIn(email, password);

        if (success) {
            return ResponseEntity.ok("Sign-in successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password.");
        }
    }

}
