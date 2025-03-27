package com.expense.api.repo;

import com.expense.api.entity.Userdata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Userrepository extends JpaRepository<Userdata, Long> {

    Userdata findByemail(String email);

}
