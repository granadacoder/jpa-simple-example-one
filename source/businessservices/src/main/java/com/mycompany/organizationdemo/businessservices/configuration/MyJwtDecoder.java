package com.mycompany.organizationdemo.businessservices.configuration;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;

public class MyJwtDecoder implements JwtDecoder {

  private final Logger logger;

  /* The Inject annotation is the signal for which constructor to use for IoC when there are multiple constructors.  Not needed in single constructor scenarios */
  @Inject
  public MyJwtDecoder() {
    this(LoggerFactory.getLogger(MyJwtDecoder.class));
  }

  public MyJwtDecoder(Logger lgr) {
    if (null == lgr) {
      throw new IllegalArgumentException("Logger is null");
    }

    this.logger = lgr;
  }

  @Override
  public Jwt decode(String token) throws JwtException {
    try {
      ParsedAuthToken parsedAuthToken = new ParsedAuthToken();//authTokenHandler.parseToken(token);

      this.logger.info(String.format("MyJwtDecoder received a token.  No real validation here, but showing it was triggered.  (Token='%1s')", token));

      if (!parsedAuthToken.getIsValid()) {
        //throw new InvalidJwtTokenException("Authentication token was invalid");
        throw new RuntimeException("Authentication token was invalid");
      }

      List<String> audienceList = new ArrayList<>(); //singletonList(parsedAuthToken.getAudience());
      Jwt.Builder builder = Jwt.withTokenValue(token)
              .audience(audienceList)
              .issuer(parsedAuthToken.getIssuedBy())
              .subject(parsedAuthToken.getSubject())
              .header("ver", parsedAuthToken.getTokenVersion());

      parsedAuthToken.getClaims()/*.getClaimsMap()*/.forEach(builder::claim);

      return builder.build();
    } catch (Exception e) {
      throw new JwtException("error processing JWT", e);
    }
  }

}
