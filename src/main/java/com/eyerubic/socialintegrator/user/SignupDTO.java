package com.eyerubic.socialintegrator.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter @Setter @NoArgsConstructor
public class SignupDTO {

    @NotEmpty
    @Size(max = 30)
    @Pattern(regexp = "[A-Z][a-z]*")
    private String firstName;

    @NotEmpty
    @Size(max = 100)
    @Pattern(regexp = "[A-Z][a-z]*")
    private String lastName;

    @NotEmpty
    @Size(max = 100)
    @Email
    private String email;

    @NotEmpty
    private String password;

}
