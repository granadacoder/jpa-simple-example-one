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

    public static final String ERROR_MSG_I_DEPARTMENT_QUERY_MANAGER_IS_NULL = "IDepartmentQueryManager is null";

    public static final String ERROR_MSG_I_DEPARTMENT_COMMAND_MANAGER_IS_NULL = "IDepartmentCommandManager is null";

    public static final String ERROR_MSG_IDEPARTMENT_QUERY_MANAGER_IS_NULL_IN_LOAD_DATA = "IDepartmentQueryManager is null in 'loadData'";

    public static final String ERROR_MSG_IDEPARTMENT_COMMAND_MANAGER_IS_NULL_IN_LOAD_DATA = "IDepartmentCommandManager is null in 'loadData'";

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
            throw new IllegalArgumentException(ERROR_MSG_I_DEPARTMENT_QUERY_MANAGER_IS_NULL);
        }

        if (null == deptCommandManager) {
            throw new IllegalArgumentException(ERROR_MSG_I_DEPARTMENT_COMMAND_MANAGER_IS_NULL);
        }

        this.logger = lgr;
        this.deptQueryManager = deptQueryManager;
        this.deptCommandManager = deptCommandManager;
    }

    @PostConstruct
    public void loadData() {
////    public void run(ApplicationArguments args) {

        if (null == this.deptQueryManager) {
            throw new IllegalArgumentException(ERROR_MSG_IDEPARTMENT_QUERY_MANAGER_IS_NULL_IN_LOAD_DATA);
        }

        if (null == this.deptCommandManager) {
            throw new IllegalArgumentException(ERROR_MSG_IDEPARTMENT_COMMAND_MANAGER_IS_NULL_IN_LOAD_DATA);
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
