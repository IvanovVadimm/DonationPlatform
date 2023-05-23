package com.example.DonationPlatform.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class ReturnLoginSecurityUser {
    public String getLoginOfSecurityUser(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
