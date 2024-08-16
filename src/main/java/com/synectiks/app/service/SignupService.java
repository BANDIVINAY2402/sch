package com.synectiks.app.service;

import java.security.SecureRandom;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.synectiks.app.entity.SignUpDetails;

@Service
public class SignupService implements SignupServiceInterface{

	@Autowired
	private com.synectiks.app.repo.Signup signuprepo;
    @Autowired
    private JavaMailSender mailSender;
    
	private final SecureRandom secureRandom = new SecureRandom();
	@Override
	public String Signup(SignUpDetails signupdetails) {
	
		System.out.println(signuprepo.findByEmail(signupdetails.getEmail()));
		if(signuprepo.findByEmail(signupdetails.getEmail())!=null)
		{
			return "user already exist";
		}
		else {
			SignUpDetails signup=new SignUpDetails();
			signup.setEmail(signupdetails.getEmail());
			signup.setFirstname(signupdetails.getFirstname());
			signup.setLastname(signupdetails.getLastname());
			signup.setPassword(signupdetails.getPassword());
			System.out.println(signup);
			signuprepo.save(signup);
			
			return "user registered successfully";
		}
	}
	
	 public void sendOtpToUser(String email) throws Exception {
	      Optional<SignUpDetails> signup= Optional.of(signuprepo.findByEmail(email));
	      System.out.println(signup.isPresent());
	        if (signup.isPresent()) {
	        	SignUpDetails user = signup.get();
	            String otp = generateOtp();
	            user.setOtp(otp);	            
	            signuprepo.save(user);
System.out.println(signuprepo);
	            sendOtpEmail(user.getEmail(), otp); // Implement this method in EmailService
	        } else {
	            throw new Exception("User with email " + email + " not found.");
	        }
	    }
	 
	 public void sendOtpEmail(String to, String otp) {
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setFrom("bandivinay24@gmail.com"); 
	        message.setTo(to);
	        message.setSubject("Your OTP Code");
	        message.setText("Your OTP code is: " + otp);
	        System.out.println(message);
	      mailSender.send(message);
	      System.out.println(mailSender.toString());
	      System.out.println(mailSender.hashCode());
	      
	    }

	private String generateOtp() {
		 int otp = 100000 + secureRandom.nextInt(900000);
	        return String.valueOf(otp);
	}

	@Override
	public boolean validateOtp(String email, String otp) {
	  
	   // Optional<SignUpDetails> signupOpt = Optional.ofNullable(signuprepo.findByEmail(email));
	    SignUpDetails signup=signuprepo.findByEmail(email);
	  System.out.println(signup.getEmail());
	  System.out.println(signup.getOtp());

	  
	       
	        if (signup.getOtp() != null && signup.getOtp().equals(otp)) {
	            return true;
	        }
	    

	    return false;
	}


	@Override
	public void resetPassword(String email, String otp, String newPassword) {
	
		
		    SignUpDetails signup=signuprepo.findByEmail(email);
		   
		        if (signup.getOtp().equals(otp)) {
		           
		            signup.setPassword(newPassword); 
		            signuprepo.save(signup);
		        } else {
		            throw new IllegalArgumentException("Invalid OTP or OTP has expired");
		        }
		    
		
	}

	@Override
	public boolean signIn(String email, String password) {
	    Optional<SignUpDetails> signupOpt = Optional.ofNullable(signuprepo.findByEmail(email));

	    if (signupOpt.isPresent()) {
	        SignUpDetails signup = signupOpt.get();

	        
	        if (password.equals(signup.getPassword())) {
	           
	            return true;
	        }
	    }
	    return false;
	}


}
