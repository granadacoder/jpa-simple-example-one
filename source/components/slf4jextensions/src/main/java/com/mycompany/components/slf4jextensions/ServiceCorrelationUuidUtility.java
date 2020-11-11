package com.mycompany.components.slf4jextensions;

import org.slf4j.MDC;

import java.util.UUID;

/* See slf4j https://www.baeldung.com/mdc-in-log4j-2-logback and https://dzone.com/articles/mdc-better-way-of-logging-1 */

public final class ServiceCorrelationUuidUtility {

    /* use this to add to log messages, etc, instead of using random names in code */
    public static final String TRACKING_UUID_NAME = "TrackingUuid";

    /* use this to add to headers, instead of using random names in code */
    public static final String TRACKING_UUID_HEADER_NAME = "X-Tracking-Uuid";

    public static final String MDC_TRANSACTION_ID_KEY_NAME = "MDC_TRANSACTION_ID";

    private ServiceCorrelationUuidUtility() {
    }

    public static String getId() {
        String threadName = Thread.currentThread().getName();
        String returnValue = MDC.get(MDC_TRANSACTION_ID_KEY_NAME);
        return returnValue;
    }

    public static String setId() {
        String mdcDebuggingPrefix = "MDCRocks_";
        String threadName = Thread.currentThread().getName();
        String uuid = UUID.randomUUID().toString();
        String setAndReturnValue = mdcDebuggingPrefix + threadName + uuid;
        MDC.put(MDC_TRANSACTION_ID_KEY_NAME, setAndReturnValue);

        String sanityCheck = MDC.get(MDC_TRANSACTION_ID_KEY_NAME);
        if (null == sanityCheck || sanityCheck.length() <= 0) {
            throw new NullPointerException("MDC did not persist. How is this even happening?????");
        }

        return setAndReturnValue;
    }
}
