package com.mycompany.organizationdemo.businessservices.configuration.seeddata;

import com.mycompany.organizationdemo.businesslayer.managers.commands.interfaces.IDepartmentCommandManager;
import com.mycompany.organizationdemo.businesslayer.managers.queries.interfaces.IDepartmentQueryManager;
import com.mycompany.organizationdemo.domain.dtos.DepartmentDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

public final class MySeedData { //implements ApplicationRunner {

    public static final String ERROR_MSG_LOGGER_IS_NULL = "Logger is null";

    private final Logger logger;

    private final IDepartmentQueryManager deptQueryManager;

    private final IDepartmentCommandManager deptCommandManager;

    /* The Inject annotation is the signal for which constructor to use for IoC when there are multiple constructors.  Not needed in single constructor scenarios */
    @Inject
    public MySeedData(IDepartmentQueryManager deptQueryManager, IDepartmentCommandManager deptCommandManager) {
        this(LoggerFactory.getLogger(MySeedData.class), deptQueryManager, deptCommandManager);
    }

    public MySeedData(Logger lgr, IDepartmentQueryManager deptQueryManager, IDepartmentCommandManager deptCommandManager) {

        if (null == lgr) {
            throw new IllegalArgumentException(ERROR_MSG_LOGGER_IS_NULL);
        }

        if (null == deptQueryManager) {
            throw new IllegalArgumentException("IDepartmentQueryManager is null");
        }

        if (null == deptCommandManager) {
            throw new IllegalArgumentException("IDepartmentCommandManager is null");
        }

        this.logger = lgr;
        this.deptQueryManager = deptQueryManager;
        this.deptCommandManager = deptCommandManager;
    }

    @PostConstruct
    public void loadData() {
////    public void run(ApplicationArguments args) {

        if (null == this.deptQueryManager) {
            throw new IllegalArgumentException("IDepartmentQueryManager is null in 'run'");
        }

        long currentDeptCount = this.deptQueryManager.getAllCount();

        long deptKey = Long.MAX_VALUE - currentDeptCount;

        DepartmentDto deptNineNineNine = new DepartmentDto();
        deptNineNineNine.setDepartmentName("DepartmentViaSeedData" + Long.toString(deptKey));
        deptNineNineNine.setDepartmentKey(deptKey);

        DepartmentDto saveResult = this.deptCommandManager.saveSingle(deptNineNineNine);
        //this.deptQueryManager.save(new DepartmentJpaEntity() {{setDepartmentName("DepartmentTwo"); }});

        this.logger.info(String.format("SeedData SaveSingle Result.  (DepartmentKey=\"%1$s\", DepartmentName=\"%2$s\")", saveResult.getDepartmentKey(), saveResult.getDepartmentName()));
    }
}
