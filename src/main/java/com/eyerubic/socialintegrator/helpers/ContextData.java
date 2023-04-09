package com.eyerubic.socialintegrator.helpers;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
public class ContextData {
    private String firstName;
    private String lastName;
    private String userId;
    private String email;

    public ContextData() {
        // Do nothing.
    }

    @Bean
    @RequestScope
    public ContextData requestScopedBeanContextData() {
        return new ContextData();
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return this.userId;
    }
}
