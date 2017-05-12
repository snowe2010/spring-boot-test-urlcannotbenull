package com.company.stuff.iam.usermanagement;

import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserManagementService {

    public static final String DEV_OPS_GROUP = "DEV_OPS";

    @Autowired
    public UserManagementService() {
    }

    public String inviteAccount() throws URISyntaxException {
        return "HELLO";
    }
}
