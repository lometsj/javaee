package com.lomebook.enti;

import javax.validation.constraints.NotEmpty;

public class Login {
    @NotEmpty(message = "couldn't be empty")
    private String user;

    @NotEmpty(message = "couldn't be empty")
    private String password;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
