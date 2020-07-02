package com.mycompany.organizationdemo.businessservices.configuration;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ParsedAuthToken {

  String issuedBy;
  String subject;
  String tokenVersion;
  Map<String, Object> claims = new HashMap<>();
  private boolean isValid;

  public ParsedAuthToken() {
    this.issuedBy = "MyIssuedBy";
    this.subject = "MySubject";
    this.tokenVersion = "MytokenVersion";

    this.isValid = true;
  }

  public String getIssuedBy() {
    return issuedBy;
  }

  public void setIssuedBy(String issuedBy) {
    this.issuedBy = issuedBy;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getTokenVersion() {
    return tokenVersion;
  }

  public void setTokenVersion(String tokenVersion) {
    this.tokenVersion = tokenVersion;
  }

  public Map<String, Object> getClaims() {
    return claims;
  }

  public void setClaims(Map<String, Object> claims) {
    this.claims = claims;
  }

  public boolean getIsValid() {
    return isValid;
  }

  public void setIsValid(boolean valid) {
    isValid = valid;
  }
}
