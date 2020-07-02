
package com.mycompany.organizationdemo.businessservices.restcontrollers;

        import com.mycompany.organizationdemo.businesslayer.managers.interfaces.IFaqManager;
        import com.mycompany.organizationdemo.domain.dtos.FaqDto;
        import java.time.OffsetDateTime;
        import java.util.Collection;
        import java.util.Optional;
        import java.util.Set;
        import javax.inject.Inject;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;
        import org.springframework.format.annotation.DateTimeFormat;
        import org.springframework.http.HttpStatus;
        import org.springframework.http.MediaType;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.PathVariable;
        import org.springframework.web.bind.annotation.RequestBody;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestMethod;
        import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/open")
public class FaqController {

  private final Logger logger;
  private final IFaqManager faqManager;

  /* The Inject annotation is the signal for which constructor to use for IoC when there are multiple constructors.  Not needed in single constructor scenarios */
  @Inject
  public FaqController(IFaqManager faqManager) {
    this(LoggerFactory.getLogger(FaqController.class), faqManager);
  }

  public FaqController(Logger lgr, IFaqManager faqManager) {
    if (null == lgr) {
      throw new IllegalArgumentException("Logger is null");
    }

    if (null == faqManager) {
      throw new IllegalArgumentException("IFaqManager is null");
    }

    this.logger = lgr;
    this.faqManager = faqManager;
  }

  @RequestMapping(value = "/faqs", method = RequestMethod.GET)
  Collection<FaqDto> getAllFaqs() {
    Collection<FaqDto> returnItems = this.faqManager.getAll();
    return returnItems;
  }


}