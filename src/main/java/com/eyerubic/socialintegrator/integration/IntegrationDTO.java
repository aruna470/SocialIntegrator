package com.eyerubic.socialintegrator.integration;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter @Setter @NoArgsConstructor
public class IntegrationDTO {

    @NotEmpty
    @Size(max = 45)
    @Pattern(regexp = "[A-Z][a-z -_]*")
    private String name;

    private int id;

    private String userId;

    private String code;

    private String createdAt;

    private String updatedAt;

    private String waWebHookUrl;
}
