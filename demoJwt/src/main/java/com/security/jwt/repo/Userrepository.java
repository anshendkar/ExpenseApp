package com.security.jwt.repo;

import com.security.jwt.entity.Userdata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Userrepository extends JpaRepository<Userdata, Long> {

    Userdata findByemail(String email);

}
