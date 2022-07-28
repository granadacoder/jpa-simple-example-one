package com.mycompany.organizationdemo.businessservices.configuration;

import com.mycompany.organizationdemo.businesslayer.managers.interfaces.IDepartmentManager;
import com.mycompany.organizationdemo.domain.dtos.DepartmentDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

public final class MySeedData { //implements ApplicationRunner {

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

        long currentDeptCount = this.deptManager.getAllCount();

        long deptKey = Long.MAX_VALUE - currentDeptCount;

        DepartmentDto deptNineNineNine = new DepartmentDto();
        deptNineNineNine.setDepartmentName("DepartmentViaSeedData" + Long.toString(deptKey));
        deptNineNineNine.setDepartmentKey(deptKey);

        DepartmentDto saveResult = this.deptManager.saveSingle(deptNineNineNine);
        //this.deptManager.save(new DepartmentJpaEntity() {{setDepartmentName("DepartmentTwo"); }});

        this.logger.info(String.format("SeedData SaveSingle Result.  (DepartmentKey=\"%1$s\", DepartmentName=\"%2$s\")", saveResult.getDepartmentKey(), saveResult.getDepartmentName()));
    }
}
