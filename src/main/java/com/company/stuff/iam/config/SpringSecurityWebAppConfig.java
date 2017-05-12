package com.company.stuff.iam.config;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.company.stuff.iam.usermanagement.UserManagementService;
import com.fasterxml.jackson.databind.ObjectMapper;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityWebAppConfig extends WebSecurityConfigurerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(SpringSecurityWebAppConfig.class);

    private static final String ACTUATOR_PATH = "/actuator/**";


    @Value("#{'${security.ignoreUris}'.split(',')}")
    private List<String> ignoreUris;

    @Autowired
    public SpringSecurityWebAppConfig(ObjectMapper objectMapper) {
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        for (String ignoreUri : ignoreUris) {
            web.ignoring()
               .antMatchers(ignoreUri);
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf()
            .disable()
            .exceptionHandling()
            .and()
            .authorizeRequests()
            .antMatchers(ACTUATOR_PATH)
            .hasAuthority(UserManagementService.DEV_OPS_GROUP);
    }
}
