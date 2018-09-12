package com.lomebook.enti;

import javax.validation.constraints.NotEmpty;


public class Code {
    @NotEmpty(message = "couldn't be empty")
    private String acticode;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @NotEmpty(message = "couldn't be empty")
    private String user;

    public String getActicode() {
        return acticode;
    }

    public void setActicode(String acticode) {
        this.acticode = acticode;
    }
}
