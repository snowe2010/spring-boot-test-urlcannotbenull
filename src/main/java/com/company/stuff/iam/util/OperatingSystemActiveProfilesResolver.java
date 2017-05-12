package com.company.stuff.iam.util;

import java.util.Map;

import org.springframework.test.context.ActiveProfilesResolver;

/**
 * @author tylerthrailkill
 */
public class OperatingSystemActiveProfilesResolver implements ActiveProfilesResolver {
    @Override
    public String[] resolve(Class<?> testClass) {
        String profile = "embedded";

        Map<String, String> env = System.getenv();
        for (String envName : env.keySet()) {
            if (envName.equals("CI"))
                profile = "default";
        }
        // determine the value of profile based on the operating system
        return new String[] {profile};
    }
}
