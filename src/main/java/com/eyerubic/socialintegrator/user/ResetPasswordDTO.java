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
public class ResetPasswordDTO {

    @NotEmpty
    @Size(max = 100)
    @Email
    private String email;

    @NotEmpty
    @Size(min = 4, max = 4)
    private String verificationCode;

    @JsonProperty(access = Access.WRITE_ONLY)
    @NotEmpty
    private String password;
}
