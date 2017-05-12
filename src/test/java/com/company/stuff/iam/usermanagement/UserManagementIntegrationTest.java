package com.company.stuff.iam.usermanagement;

import static org.junit.Assert.assertEquals;

import java.net.URISyntaxException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
//@ActiveProfiles("embedded") // uncomment to run against embedded?
@SpringBootTest
public class UserManagementIntegrationTest {
    @Autowired
    private UserManagementService userManagementService;

    @Test
    public void inviteAccountExisting() throws URISyntaxException {
        String response = userManagementService.inviteAccount();

        assertEquals("Applicant Id: ", "testApplicantId", "HELLO");
    }
}
