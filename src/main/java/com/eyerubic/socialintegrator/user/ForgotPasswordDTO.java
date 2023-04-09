package com.eyerubic.socialintegrator.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter @Setter @NoArgsConstructor
public class ForgotPasswordDTO {

    @NotEmpty
    @Size(max = 100)
    @Email
    private String email;
}
