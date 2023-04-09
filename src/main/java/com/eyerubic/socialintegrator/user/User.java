package com.eyerubic.socialintegrator.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "User")
@Getter @Setter @NoArgsConstructor 
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "firstName")
    @NotEmpty
    @Size(max = 30)
    @Pattern(regexp = "[A-Z][a-z]*")
    private String firstName;

    @Column(name = "lastName")
    @NotEmpty
    @Size(max = 100)
    @Pattern(regexp = "[A-Z][a-z]*")
    private String lastName;

    @Column(name = "email")
    @NotEmpty
    @Size(max = 100)
    @Email
    private String email;

    @JsonProperty(access = Access.WRITE_ONLY)
    @Column(name = "password")
    @NotEmpty
    private String password;

    @Column(name = "createdAt")
    private String createdAt;

    @JsonProperty(access = Access.WRITE_ONLY)
    @Column(name = "status")
    private int status = 0;
    
    @JsonProperty(access = Access.WRITE_ONLY)
    @Column(name = "emailVerified")
    private int emailVerified = 0;

    @JsonProperty(access = Access.WRITE_ONLY)
    @Column(name = "verificationCode")
    private int verificationCode;
}
