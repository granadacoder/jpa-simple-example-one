package com.mycompany.organizationdemo.businessservices.restcontrollers.commands;

import com.mycompany.organizationdemo.businesslayer.managers.commands.interfaces.IDepartmentCommandManager;
import com.mycompany.organizationdemo.domain.dtos.DepartmentDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("/api/v1")
public final class DepartmentCommandRestController {

    public static final String ERROR_MSG_LOGGER_IS_NULL = "Logger is null";

    public static final String ERROR_MSG_I_DEPARTMENT_COMMAND_MANAGER_IS_NULL = "IDepartmentCommandManager is null";

    private final Logger logger;

    private final IDepartmentCommandManager deptCommandManager;

    /* The Inject annotation is the signal for which constructor to use for IoC when there are multiple constructors.  Not needed in single constructor scenarios */
    @Inject
    public DepartmentCommandRestController(IDepartmentCommandManager deptCommandManager) {
        this(LoggerFactory.getLogger(DepartmentCommandRestController.class), deptCommandManager);
    }

    public DepartmentCommandRestController(Logger lgr, IDepartmentCommandManager deptCommandManager) {
        if (null == lgr) {
            throw new IllegalArgumentException(ERROR_MSG_LOGGER_IS_NULL);
        }

        if (null == deptCommandManager) {
            throw new IllegalArgumentException(ERROR_MSG_I_DEPARTMENT_COMMAND_MANAGER_IS_NULL);
        }

        this.logger = lgr;
        this.deptCommandManager = deptCommandManager;
    }

    @RequestMapping(value = "departments/{deptKey}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> deleteUser(@PathVariable("deptKey") Long deptKey) {
        this.logger.info(String.format("Method deleteUser called. (deptKey=\"%1s\")", deptKey));

        int rowCount = this.deptCommandManager.deleteByKey(deptKey);
        ResponseEntity<Integer> responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if (rowCount > 0) {
            responseEntity = new ResponseEntity<>(rowCount, HttpStatus.OK);
        }

        return responseEntity;
    }

    @RequestMapping(method = RequestMethod.POST, value = "departments/department", produces = {"application/json"}, consumes = {"application/json"})
    ResponseEntity<DepartmentDto> createDepartment(@RequestBody DepartmentDto inputItem) {

        this.logger.info(String.format("Method createDepartment called. (inputItem=\"%1s\")", inputItem));

        DepartmentDto foundItem = this.deptCommandManager.saveSingle(inputItem);
        ResponseEntity<DepartmentDto> responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        responseEntity = new ResponseEntity<>(foundItem, HttpStatus.OK);
        return responseEntity;
    }

}
