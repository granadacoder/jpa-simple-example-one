package com.mycompany.organizationdemo.exceptions;

public final class BusinessLayerFinalException extends RuntimeException {

    public static final int PREFERRED_HTTP_STATUS_CODE_DEFAULT = 500;

    public static final boolean SHOW_INNER_EXCEPTION_TO_CUSTOMER_DEFAULT = false;

    public static final String CUSTOMER_FACING_MESSAGE_DEFAULT = "There was an error with your request";

    private final int preferredHttpStatusCode;

    private final boolean showInnerExceptionToCustomer;

    private final String customerFacingMessage;

    public BusinessLayerFinalException() {
        this.preferredHttpStatusCode = PREFERRED_HTTP_STATUS_CODE_DEFAULT;
        this.showInnerExceptionToCustomer = SHOW_INNER_EXCEPTION_TO_CUSTOMER_DEFAULT;
        this.customerFacingMessage = CUSTOMER_FACING_MESSAGE_DEFAULT;
    }

    public BusinessLayerFinalException(String message) {
        super(message);
        this.preferredHttpStatusCode = PREFERRED_HTTP_STATUS_CODE_DEFAULT;
        this.showInnerExceptionToCustomer = SHOW_INNER_EXCEPTION_TO_CUSTOMER_DEFAULT;
        this.customerFacingMessage = CUSTOMER_FACING_MESSAGE_DEFAULT;
    }

    public BusinessLayerFinalException(String message, Exception inner) {
        super(message, inner);
        this.preferredHttpStatusCode = PREFERRED_HTTP_STATUS_CODE_DEFAULT;
        this.showInnerExceptionToCustomer = SHOW_INNER_EXCEPTION_TO_CUSTOMER_DEFAULT;
        this.customerFacingMessage = CUSTOMER_FACING_MESSAGE_DEFAULT;
    }

    public BusinessLayerFinalException(
            String message,
            int preferredHttpStatusCode,
            boolean showInnerExceptionToCustomer,
            String customerFacingMessage) {
        super(message);
        this.preferredHttpStatusCode = preferredHttpStatusCode;
        this.showInnerExceptionToCustomer = showInnerExceptionToCustomer;
        this.customerFacingMessage = customerFacingMessage;
    }

    public BusinessLayerFinalException(
            String message,
            Exception innerException,
            int preferredHttpStatusCode,
            boolean showInnerExceptionToCustomer,
            String customerFacingMessage) {
        super(message, innerException);
        this.preferredHttpStatusCode = preferredHttpStatusCode;
        this.showInnerExceptionToCustomer = showInnerExceptionToCustomer;
        this.customerFacingMessage = customerFacingMessage;
    }

    public int getPreferredHttpStatusCode() {
        return preferredHttpStatusCode;
    }

    public boolean isShowInnerExceptionToCustomer() {
        return showInnerExceptionToCustomer;
    }

    public String getCustomerFacingMessage() {
        return customerFacingMessage;
    }
}
