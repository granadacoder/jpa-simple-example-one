package com.mycompany.organizationdemo.businesslayer.managers;

import com.mycompany.organizationdemo.businesslayer.managers.interfaces.IDepartmentCommandManager;
import com.mycompany.organizationdemo.domain.dtos.DepartmentDto;
import com.mycompany.organizationdemo.domaindatalayer.interfaces.IDepartmentCommandRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public final class DepartmentCommandManager implements IDepartmentCommandManager {

    public static final String ERROR_MSG_LOGGER_IS_NULL = "Logger is null";

    private final Logger logger;

    private final IDepartmentCommandRepository deptCommandRepo;

    /* The Inject annotation marks which constructor to use for IoC when there are multiple constructors */
    @Inject
    public DepartmentCommandManager(IDepartmentCommandRepository deptCommandRepo) {
        this(LoggerFactory.getLogger(DepartmentCommandManager.class), deptCommandRepo);
    }

    public DepartmentCommandManager(Logger lgr, IDepartmentCommandRepository deptCommandRepo) {
        if (null == lgr) {
            throw new IllegalArgumentException(ERROR_MSG_LOGGER_IS_NULL);
        }

        if (null == deptCommandRepo) {
            throw new IllegalArgumentException("IDepartmentDomainData is null");
        }

        this.logger = lgr;
        this.deptCommandRepo = deptCommandRepo;
    }

    @Override
    public DepartmentDto saveSingle(DepartmentDto item) {
        DepartmentDto returnItem = this.deptCommandRepo.save(item);
        return returnItem;
    }

    @Override
    public int deleteByKey(long key) {
        this.logger.info(String.format("Method deleteByKey called. (deptName=\"%1s\")", key));
        int returnValue = this.deptCommandRepo.deleteByKey(key);
        return returnValue;
    }
}
