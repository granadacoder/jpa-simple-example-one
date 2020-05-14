package com.mycompany.organizationdemo.businessservices.restcontrollers;

import com.mycompany.organizationdemo.businesslayer.managers.interfaces.IDepartmentManager;
import com.mycompany.organizationdemo.domain.entities.Department;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/v1")
public class DepartmentController {

    private final Logger logger;
    private final IDepartmentManager deptManager;

    /* The Inject annotation is the signal for which constructor to use for IoC when there are multiple constructors.  Not needed in single constructor scenarios */
    @Inject
    public DepartmentController(IDepartmentManager deptManager) {
        this(LoggerFactory.getLogger(DepartmentController.class), deptManager);
    }

    public DepartmentController(Logger lgr, IDepartmentManager deptManager) {
        if (null == lgr) {
            throw new IllegalArgumentException("Logger is null");
        }

        if (null == deptManager) {
            throw new IllegalArgumentException("IDepartmentManager is null");
        }

        this.logger = lgr;
        this.deptManager = deptManager;
    }

    @RequestMapping(value = "/departments", method = RequestMethod.GET)
    Collection<Department> getAllDepartments() {

        Iterable<Department> depts = this.deptManager.getAll();

        Collection<Department> returnItems = StreamSupport.stream(depts.spliterator(), false)
                .collect(Collectors.toList());

        return returnItems;
    }

    @RequestMapping(value = "/departments/beforecreatedate/{zdt}", method = RequestMethod.GET)
    Collection<Department> getAllDepartmentsByBeforeCreateDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime zdt) {

        Iterable<Department> depts = this.deptManager.getDepartmentsOlderThanDate(zdt);

        Collection<Department> returnItems = StreamSupport.stream(depts.spliterator(), false)
                .collect(Collectors.toList());

        return returnItems;
    }

    @RequestMapping(method = RequestMethod.GET, value = "departments/{deptKey}")
    ResponseEntity<Department> getDepartmentById(@PathVariable Long deptKey) {

        Optional<Department> foundItem = this.deptManager.getSingle(deptKey);
        ResponseEntity<Department> responseEntity = new ResponseEntity<Department>(HttpStatus.NOT_FOUND);

        if (foundItem.isPresent()) {
            responseEntity = new ResponseEntity<>(foundItem.get(), HttpStatus.OK);
        }

        return responseEntity;
    }

    @RequestMapping(method = RequestMethod.GET, value = "departments/name/{deptName}")
    ResponseEntity<Department> getDepartmentByName(@PathVariable String deptName) {

        Optional<Department> foundItem = this.deptManager.getSingleByName(deptName);
        ResponseEntity<Department> responseEntity = new ResponseEntity<Department>(HttpStatus.NOT_FOUND);

        if (foundItem.isPresent()) {
            responseEntity = new ResponseEntity<>(foundItem.get(), HttpStatus.OK);
        }

        return responseEntity;
    }
}