package com.lomebook.enti;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Register {

    @NotEmpty(message = "couldn't be empty")
    @Size(min=3,max=20)
    private String username;

//    private String name;
//    private String address;
//    private String phone;

    @NotEmpty(message = "couldn't be empty")
    @Size(min=8,max=20)
    private String password;

    @NotEmpty(message = "couldn't be empty")
    @Size(min=8,max=20)
    private String password_r;

    @NotEmpty(message = "couldn't be empty")
    @Email(message = "plz input correct email")
    private String email;

    public String getPassword_r(){
        return password_r;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword_r(String password_r) {
        this.password_r = password_r;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
