package com.mycompany.organizationdemo.businesslayer.managers.queries;

import com.mycompany.organizationdemo.businesslayer.managers.queries.interfaces.IDepartmentQueryManager;
import com.mycompany.organizationdemo.domain.dtos.DepartmentDto;
import com.mycompany.organizationdemo.domaindatalayer.interfaces.IDepartmentQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public final class DepartmentQueryManager implements IDepartmentQueryManager {

    public static final String ERROR_MSG_LOGGER_IS_NULL = "Logger is null";

    private final Logger logger;

    private final IDepartmentQueryRepository deptQueryRepo;

    /* The Inject annotation marks which constructor to use for IoC when there are multiple constructors */
    @Inject
    public DepartmentQueryManager(IDepartmentQueryRepository deptQueryRepo) {
        this(LoggerFactory.getLogger(DepartmentQueryManager.class), deptQueryRepo);
    }

    public DepartmentQueryManager(Logger lgr, IDepartmentQueryRepository deptQueryRepo) {
        if (null == lgr) {
            throw new IllegalArgumentException(ERROR_MSG_LOGGER_IS_NULL);
        }

        if (null == deptQueryRepo) {
            throw new IllegalArgumentException("IDepartmentQueryRepository is null");
        }

        this.logger = lgr;
        this.deptQueryRepo = deptQueryRepo;
    }

    @Override
    public long getAllCount() {
        long returnValue = this.deptQueryRepo.getTheAllCount();
        return returnValue;
    }

    @Override
    public Collection<DepartmentDto> getAll() {
        /* use .findEmAll() or .findEmAllByMyCoolProjectionExample() here */
        Collection<DepartmentDto> returnItems = this.deptQueryRepo.findEmAllByMyCoolProjectionExample();
        return returnItems;
    }

    @Override
    public Collection<DepartmentDto> getAllNoEmployees() {
        Collection<DepartmentDto> returnItems = this.deptQueryRepo.findOrphanedDepartments();
        return returnItems;
    }

    @Override
    public Optional<DepartmentDto> getSingle(long key) {
        this.logger.info(String.format("Method getSingle called. (key=\"%1s\")", key));
        Optional<DepartmentDto> returnItem = this.deptQueryRepo.findById(key);
        return returnItem;
    }

    @Override
    public Optional<DepartmentDto> getSingleByName(String deptName) {
        this.logger.info(String.format("Method getSingleByName called. (deptName=\"%1s\")", deptName));
        Optional<DepartmentDto> returnItem = this.deptQueryRepo.findByDepartmentName(deptName);
        return returnItem;
    }

    @Override
    public Collection<DepartmentDto> getDepartmentsOlderThanDate(OffsetDateTime zdt) {
        this.logger.info(String.format("Method getDepartmentsOlderThanDate called. (zdt=\"%1s\")", zdt));
        Collection<DepartmentDto> returnItems = this.deptQueryRepo.findByCreateOffsetDateTimeBefore(zdt);
        return returnItems;
    }

    @Override
    public Collection<DepartmentDto> getByKeys(Set<Long> departmentKeys) {
        this.logger.info(String.format("Method getByKeys called. (departmentKeys=\"%1s\")", departmentKeys));
        Collection<DepartmentDto> returnItems = this.deptQueryRepo.findBySurrogateKeyIn(departmentKeys);
        return returnItems;
    }
}