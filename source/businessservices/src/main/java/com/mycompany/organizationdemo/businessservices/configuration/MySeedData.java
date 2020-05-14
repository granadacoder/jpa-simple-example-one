package com.mycompany.organizationdemo.businessservices.configuration;

import com.mycompany.organizationdemo.businesslayer.managers.interfaces.IDepartmentManager;
import com.mycompany.organizationdemo.domain.entities.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Component
public class MySeedData { //implements ApplicationRunner {

    private final Logger logger;
    private final IDepartmentManager deptManager;

    /* The Inject annotation is the signal for which constructor to use for IoC when there are multiple constructors.  Not needed in single constructor scenarios */
    @Inject
    public MySeedData(IDepartmentManager deptManager) {
        this(LoggerFactory.getLogger(MySeedData.class), deptManager);
    }

    public MySeedData(Logger lgr, IDepartmentManager deptManager) {

        if (null == lgr) {
            throw new IllegalArgumentException("Logger is null");
        }

        if (null == deptManager) {
            throw new IllegalArgumentException("IDepartmentManager is null");
        }

        this.logger = lgr;
        this.deptManager = deptManager;
    }

    @PostConstruct
    public void loadData() {
////    public void run(ApplicationArguments args) {

        if (null == this.deptManager) {
            throw new IllegalArgumentException("IDepartmentManager is null in 'run'");
        }

        Department deptOne = new Department() {{setDepartmentName("DepartmentNineNineNine"); }};
        ////this.deptManager.saveSingle(deptOne);
        ////this.deptManager.save(new Department() {{setDepartmentName("DepartmentTwo"); }});
    }
}