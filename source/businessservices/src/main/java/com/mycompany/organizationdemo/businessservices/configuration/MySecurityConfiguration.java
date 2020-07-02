package com.mycompany.organizationdemo.businessservices.configuration;

import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/* the annotation and the superclass drive this functionality */
@Configuration
public class MySecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final Logger logger;
  private final org.springframework.security.oauth2.jwt.JwtDecoder jwtDecoder;

  /* The Inject annotation is the signal for which constructor to use for IoC when there are multiple constructors.  Not needed in single constructor scenarios */
  @Inject
  public MySecurityConfiguration(org.springframework.security.oauth2.jwt.JwtDecoder jwtDecoder) {
    this(LoggerFactory.getLogger(MySecurityConfiguration.class), jwtDecoder);
  }

  public MySecurityConfiguration(Logger lgr, org.springframework.security.oauth2.jwt.JwtDecoder jwtDecoder) {
    if (null == lgr) {
      throw new IllegalArgumentException("Logger is null");
    }

    if (null == jwtDecoder) {
      throw new IllegalArgumentException("JwtDecoder is null");
    }

    this.logger = lgr;
    this.jwtDecoder = jwtDecoder;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    /* not production grade quality */
    http.csrf().disable(); /*  ComingSoon.  Create yml for CORS.  As is, had to add this "Cross Site Request Forgery" disable for DELETE operations */
    //original very/too open //http.authorizeRequests().anyRequest().permitAll();

    /* hard coded, a little better */

    String[] openPaths = {"/api/open", "/api/open99"};
    String[] securedPaths = {"/api/v1", "/api/v99"};

    /* see https://stackoverflow.com/questions/24696717/spring-security-permitall-not-allowing-anonymous-access/31792102#31792102 */

    http.authorizeRequests()
            .antMatchers(openPaths)//"/ping**")
            .permitAll()
            .and()
            .authorizeRequests()
            .antMatchers(securedPaths).authenticated()
            .and().oauth2ResourceServer().jwt().decoder(this.jwtDecoder);
  }

//  @Inject
//  public org.springframework.security.oauth2.jwt.JwtDecoder getAJwtDecoder() {
//    return new MyJwtDecoder();
//  }


//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.debug(true);
//    }

}
