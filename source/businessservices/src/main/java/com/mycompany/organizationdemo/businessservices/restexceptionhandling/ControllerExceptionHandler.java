package com.mycompany.organizationdemo.businessservices.restexceptionhandling;

import com.mycompany.organizationdemo.exceptions.BusinessLayerFinalException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestControllerAdvice
public final class ControllerExceptionHandler {
    public static final String ERROR_MSG_INTERNAL_SERVER_ERROR = "Internal Server Error";

    public static final String TEMPLATE_CUSTOMER_AND_EX_MESSAGE = "%1$s %2$s";

    @ExceptionHandler(BusinessLayerFinalException.class)
    @ResponseBody
    public ResponseEntity<?> handleException(HttpServletResponse resp, BusinessLayerFinalException ex) {
        resp.setStatus(ex.getPreferredHttpStatusCode());

        ErrorMessage simpleError = new ErrorMessage(
                ex.getPreferredHttpStatusCode(),
                new Date(),
                ex.isShowInnerExceptionToCustomer() ? String.format(TEMPLATE_CUSTOMER_AND_EX_MESSAGE, ex.getCustomerFacingMessage(), ex.getMessage()) : ex.getCustomerFacingMessage());

        return new ResponseEntity<ErrorMessage>(
                simpleError,
                HttpStatus.valueOf(ex.getPreferredHttpStatusCode()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<?> handleException(HttpServletResponse resp, Exception ex) {
        resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

        ErrorMessage simpleError = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                ERROR_MSG_INTERNAL_SERVER_ERROR);

        return new ResponseEntity<ErrorMessage>(
                simpleError,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
