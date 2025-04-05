package com.expense.api.service;

import com.expense.api.entity.Userdata;
import com.expense.api.repo.Userrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    Userrepository userRepository;

    @Autowired
    JWTService jwtService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    public UserService(Userrepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public String registerUser(Userdata user) {
        // Check if the user with the same email already exists
        boolean userExists = userRepository.findByemail(user.getEmail()) != null;
        if (userExists) {
            return "User already registered";
        }
        // Encode password before saving
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully";
    }

    public String verify(Userdata user) {

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

        if (authenticate.isAuthenticated()) {
            return jwtService.generateToken(user);
        }
//        Userdata user = userRepository.findByemail(email);
//        if (user==null) {
//            return "Invalid email or user not registered.";
//        }
//            // Verify password
//            if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
//                return "Invalid password";
//            }
//
        return "Failure";

    }
}

//    public List<Userdata> getAllUsers() {
//        return userRepository.findAll();
//    }
