package com.eyerubic.socialintegrator.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter @Setter @NoArgsConstructor
public class SigninDTO {

    @NotEmpty
    @Size(max = 100)
    @Email
    private String email;

    @JsonProperty(access = Access.WRITE_ONLY)
    @NotEmpty
    private String password;

    private String token;

    private String firstName;

    private String lastName;
}
