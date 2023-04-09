package com.eyerubic.socialintegrator.user;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter @Setter @NoArgsConstructor
public class ChangePasswordDTO {

    @JsonProperty(access = Access.WRITE_ONLY)
    @NotEmpty
    private String oldPassword;

    @JsonProperty(access = Access.WRITE_ONLY)
    @NotEmpty
    private String password;
}
