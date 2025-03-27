package com.expense.api.DTO;

public class UserDTO {

    private Long userid;
    private String firstname;
    private String lastname;

    public UserDTO(Long userid, String firstname, String lastname) {
        this.userid = userid;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

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
}
