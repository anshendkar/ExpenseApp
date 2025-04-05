package com.expense.api.service;

import com.expense.api.CustomUserDetails;
import com.expense.api.entity.Userdata;
import com.expense.api.repo.Userrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService  implements UserDetailsService {

    @Autowired
    Userrepository userrepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Userdata userdata= userrepository.findByemail(email);
        if(userdata==null){
            throw new UsernameNotFoundException("User with email "+ email+" not found");
        }
        return new CustomUserDetails(userdata);
    }
}
