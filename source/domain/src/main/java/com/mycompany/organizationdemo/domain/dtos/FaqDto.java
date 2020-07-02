package com.mycompany.organizationdemo.domain.dtos;

public class FaqDto {

  private long faqKey;
  private String faqQuestion;
  private String faqAnswer;




  public long getFaqKey() {
    return faqKey;
  }

  public void setFaqKey(long faqKey) {
    this.faqKey = faqKey;
  }

  public String getFaqQuestion() {
    return faqQuestion;
  }

  public void setFaqQuestion(String faqQuestion) {
    this.faqQuestion = faqQuestion;
  }

  public String getFaqAnswer() {
    return faqAnswer;
  }

  public void setFaqAnswer(String faqAnswer) {
    this.faqAnswer = faqAnswer;
  }
}
