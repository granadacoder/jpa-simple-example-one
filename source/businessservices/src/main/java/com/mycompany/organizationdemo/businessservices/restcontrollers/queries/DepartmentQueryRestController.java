package com.mycompany.organizationdemo.businessservices.restcontrollers.queries;

import com.mycompany.components.slf4jextensions.ServiceCorrelationUuidUtility;
import com.mycompany.organizationdemo.businesslayer.managers.queries.interfaces.IDepartmentQueryManager;
import com.mycompany.organizationdemo.domain.dtos.DepartmentDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/v1")
public final class DepartmentQueryRestController {

    public static final String ERROR_MSG_LOGGER_IS_NULL = "Logger is null";

    public static final String LOG_MSG_METHOD_GET_ALL_DEPARTMENTS_BY_BEFORE_CREATE_DATE_CALLED = "Method getAllDepartmentsByBeforeCreateDate called. (zdt=\"%1$s\")";

    public static final String LOG_MSG_METHOD_GET_DEPARTMENT_BY_KEYS_CALLED = "Method getDepartmentByKeys called. (deptKey=\"%1$s\")";

    public static final String LOG_MSG_METHOD_GET_DEPARTMENT_BY_ID_CALLED = "Method getDepartmentById called. (deptKey=\"%1$s\")";

    public static final String LOG_MSG_METHOD_GET_DEPARTMENT_BY_NAME_CALLED = "Method getDepartmentByName called. (deptName=\"%1$s\")";

    private final Logger logger;

    private final IDepartmentQueryManager deptQueryManager;

    /* The Inject annotation is the signal for which constructor to use for IoC when there are multiple constructors.  Not needed in single constructor scenarios */
    @Inject
    public DepartmentQueryRestController(IDepartmentQueryManager deptQueryManager) {
        this(LoggerFactory.getLogger(DepartmentQueryRestController.class), deptQueryManager);
    }

    public DepartmentQueryRestController(Logger lgr, IDepartmentQueryManager deptQueryManager) {
        if (null == lgr) {
            throw new IllegalArgumentException(ERROR_MSG_LOGGER_IS_NULL);
        }

        if (null == deptQueryManager) {
            throw new IllegalArgumentException("IDepartmentQueryManager is null");
        }

        this.logger = lgr;
        this.deptQueryManager = deptQueryManager;
    }

    @RequestMapping(value = "/departments", method = RequestMethod.GET)
    Collection<DepartmentDto> getAllDepartments() {

        String correlationUid = ServiceCorrelationUuidUtility.setId();

        String sanityCheck = ServiceCorrelationUuidUtility.getId();
        if (!correlationUid.equalsIgnoreCase(sanityCheck)) {
            throw new NullPointerException("MDC did not persist. How is this even happening?????");
        }

        this.logger.info("Departmeents Get All Start");
        Collection<DepartmentDto> returnItems = this.deptQueryManager.getAll();
        return returnItems;
    }

    @RequestMapping(value = "/departments/orphans", method = RequestMethod.GET)
    Collection<DepartmentDto> getAllOrphanedDepartments() {
        Collection<DepartmentDto> returnItems = this.deptQueryManager.getAllNoEmployees();
        return returnItems;
    }

    @RequestMapping(value = "/departments/beforecreatedate/{zdt}", method = RequestMethod.GET)
    Collection<DepartmentDto> getAllDepartmentsByBeforeCreateDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime zdt) {
        this.logger.info(String.format(LOG_MSG_METHOD_GET_ALL_DEPARTMENTS_BY_BEFORE_CREATE_DATE_CALLED, zdt));
        Collection<DepartmentDto> returnItems = this.deptQueryManager.getDepartmentsOlderThanDate(zdt);
        return returnItems;
    }

    @RequestMapping(value = "/departments/bykeys/{deptKeys}", method = RequestMethod.GET)
    Collection<DepartmentDto> getDepartmentByKeys(@PathVariable Set<Long> deptKeys) {

        this.logger.info(String.format(LOG_MSG_METHOD_GET_DEPARTMENT_BY_KEYS_CALLED, deptKeys));
        Collection<DepartmentDto> returnItems = this.deptQueryManager.getByKeys(deptKeys);
        return returnItems;
    }

    @RequestMapping(method = RequestMethod.GET, value = "departments/{deptKey}")
    ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable Long deptKey) {

        this.logger.info(String.format(LOG_MSG_METHOD_GET_DEPARTMENT_BY_ID_CALLED, deptKey));

        Optional<DepartmentDto> foundItem = this.deptQueryManager.getSingle(deptKey);
        ResponseEntity<DepartmentDto> responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if (foundItem.isPresent()) {
            responseEntity = new ResponseEntity<>(foundItem.get(), HttpStatus.OK);
        }

        return responseEntity;
    }

    @RequestMapping(method = RequestMethod.GET, value = "departments/name/{deptName}")
    ResponseEntity<DepartmentDto> getDepartmentByName(@PathVariable String deptName) {

        this.logger.info(String.format(LOG_MSG_METHOD_GET_DEPARTMENT_BY_NAME_CALLED, deptName));

        Optional<DepartmentDto> foundItem = this.deptQueryManager.getSingleByName(deptName);
        ResponseEntity<DepartmentDto> responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if (foundItem.isPresent()) {
            responseEntity = new ResponseEntity<>(foundItem.get(), HttpStatus.OK);
        }

        return responseEntity;
    }

}
