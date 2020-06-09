package com.mycompany.organizationdemo.businessservices.restcontrollers;

import com.mycompany.organizationdemo.businesslayer.managers.interfaces.IDepartmentManager;
import com.mycompany.organizationdemo.domain.dtos.DepartmentDto;
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
@RequestMapping("/api/v1")
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
  Collection<DepartmentDto> getAllDepartments() {
    Collection<DepartmentDto> returnItems = this.deptManager.getAll();
    return returnItems;
  }

  @RequestMapping(value = "/departments/beforecreatedate/{zdt}", method = RequestMethod.GET)
  Collection<DepartmentDto> getAllDepartmentsByBeforeCreateDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime zdt) {
    this.logger.info(String.format("Method getAllDepartmentsByBeforeCreateDate called. (zdt=\"%1s\")", zdt));
    Collection<DepartmentDto> returnItems = this.deptManager.getDepartmentsOlderThanDate(zdt);
    return returnItems;
  }

  @RequestMapping(value = "/departments/bykeys/{deptKeys}", method = RequestMethod.GET)
  Collection<DepartmentDto> getDepartmentByKeys(@PathVariable Set<Long> deptKeys) {

    this.logger.info(String.format("Method getDepartmentByKeys called. (deptKey=\"%1s\")", deptKeys));
    Collection<DepartmentDto> returnItems = this.deptManager.getByKeys(deptKeys);
    return returnItems;
  }

  @RequestMapping(method = RequestMethod.GET, value = "departments/{deptKey}")
  ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable Long deptKey) {

    this.logger.info(String.format("Method getDepartmentById called. (deptKey=\"%1s\")", deptKey));

    Optional<DepartmentDto> foundItem = this.deptManager.getSingle(deptKey);
    ResponseEntity<DepartmentDto> responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);

    if (foundItem.isPresent()) {
      responseEntity = new ResponseEntity<>(foundItem.get(), HttpStatus.OK);
    }

    return responseEntity;
  }

  @RequestMapping(method = RequestMethod.GET, value = "departments/name/{deptName}")
  ResponseEntity<DepartmentDto> getDepartmentByName(@PathVariable String deptName) {

    this.logger.info(String.format("Method getDepartmentByName called. (deptName=\"%1s\")", deptName));

    Optional<DepartmentDto> foundItem = this.deptManager.getSingleByName(deptName);
    ResponseEntity<DepartmentDto> responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);

    if (foundItem.isPresent()) {
      responseEntity = new ResponseEntity<>(foundItem.get(), HttpStatus.OK);
    }

    return responseEntity;
  }


  @RequestMapping(value = "departments/{deptKey}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Integer> deleteUser(@PathVariable("deptKey") Long deptKey) {
    this.logger.info(String.format("Method deleteUser called. (deptKey=\"%1s\")", deptKey));

    int rowCount = this.deptManager.deleteByKey(deptKey);
    ResponseEntity<Integer> responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);

    if (rowCount > 0) {
      responseEntity = new ResponseEntity<>(rowCount, HttpStatus.OK);
    }

    return responseEntity;
  }

  @RequestMapping(method = RequestMethod.POST, value = "departments/department", produces = {"application/json"}, consumes = {"application/json"})
  ResponseEntity<DepartmentDto> createDepartment(@RequestBody DepartmentDto inputItem) {

    this.logger.info(String.format("Method createDepartment called. (inputItem=\"%1s\")", inputItem));

    DepartmentDto foundItem = this.deptManager.saveSingle(inputItem);
    ResponseEntity<DepartmentDto> responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    responseEntity = new ResponseEntity<>(foundItem, HttpStatus.OK);
    return responseEntity;
  }

}