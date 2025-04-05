package com.expense.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "userdetails")
@JsonIgnoreProperties("expenses")  // Prevents infinite recursion in JSON responses
public class Userdata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userid;  // Changed to Long for consistency

    private String firstname;
    private String lastname;

    private String email;
    private String password;

    @OneToMany(mappedBy = "userdata", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Expense> expenses;

    public Userdata() {}

    public Userdata(String firstname , String lastname , String email, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    public Long getUserid() { return userid; }

    public void setUserid(Long userid) { this.userid = userid; }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public List<Expense> getExpenses() { return expenses; }

    public void setExpenses(List<Expense> expenses) { this.expenses = expenses; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }
}
