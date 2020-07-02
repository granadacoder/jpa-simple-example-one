package com.mycompany.organizationdemo.businesslayer.managers;

import com.mycompany.organizationdemo.businesslayer.managers.interfaces.IFaqManager;
import com.mycompany.organizationdemo.domain.dtos.FaqDto;
import java.util.ArrayList;
import java.util.Collection;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FaqManager implements IFaqManager {

  private final Logger logger;


  /* The Inject annotation marks which constructor to use for IoC when there are multiple constructors */
  @Inject
  public FaqManager() {
    this(LoggerFactory.getLogger(DepartmentManager.class));
  }

  public FaqManager(Logger lgr) {
    if (null == lgr) {
      throw new IllegalArgumentException("Logger is null");
    }

    this.logger = lgr;

  }

  @Override
  public Collection<FaqDto> getAll() {
    Collection<FaqDto> returnItems = new ArrayList<>();
    FaqDto faq1 = new FaqDto() {{
      setFaqKey(111);
      setFaqQuestion("How are you?");
      setFaqAnswer("Well");
    }};
    FaqDto faq2 = new FaqDto() {{
      setFaqKey(111);
      setFaqQuestion("Do I need Jwts for Department Rest calls?");
      setFaqAnswer("Yes. (hopefully)");
    }};
    returnItems.add(faq1);
    returnItems.add(faq2);
    return returnItems;
  }
}