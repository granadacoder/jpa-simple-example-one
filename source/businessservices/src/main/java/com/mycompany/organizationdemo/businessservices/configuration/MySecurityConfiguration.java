package com.mycompany.organizationdemo.businessservices.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/* the annotation and the superclass drive this functionality */
@Configuration
public class MySecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        /* not production grade quality */
        httpSecurity.csrf().disable(); /* had to add this "Cross Site Request Forgery" disable for DELETE operations */
        httpSecurity.authorizeRequests().anyRequest().permitAll();
    }

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.debug(true);
//    }

}
