package com.synectiks.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synectiks.app.entity.SignUpDetails;

@Repository
public interface Signup extends JpaRepository<SignUpDetails, Long> {

	 SignUpDetails findByEmail(String email);

}
