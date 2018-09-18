package com.lomebook.enti;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Resetpwd {

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword_r() {
        return password_r;
    }

    public void setPassword_r(String password_r) {
        this.password_r = password_r;
    }

    @NotEmpty(message = "couldn't be empty")
    @Size(min=8,max=20)
    private String password;

    @NotEmpty(message = "couldn't be empty")
    @Size(min=8,max=20)
    private String password_r;

}
