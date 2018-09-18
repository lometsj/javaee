package com.lomebook.enti;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class Forget {

    @NotEmpty
    @Email(message = "plz input correct email")
    private String email;
}
