package com.mycompany.organizationdemo.businesslayer.managers;

import com.mycompany.organizationdemo.businesslayer.managers.interfaces.IDepartmentManager;
import com.mycompany.organizationdemo.domain.dtos.DepartmentDto;
import com.mycompany.organizationdemo.domaindatalayer.interfaces.IDepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public final class DepartmentManager implements IDepartmentManager {

    private final Logger logger;

    private final IDepartmentRepository deptRepo;

    /* The Inject annotation marks which constructor to use for IoC when there are multiple constructors */
    @Inject
    public DepartmentManager(IDepartmentRepository deptRepo) {
        this(LoggerFactory.getLogger(DepartmentManager.class), deptRepo);
    }

    public DepartmentManager(Logger lgr, IDepartmentRepository deptRepo) {
        if (null == lgr) {
            throw new IllegalArgumentException("Logger is null");
        }

        if (null == deptRepo) {
            throw new IllegalArgumentException("IDepartmentDomainData is null");
        }

        this.logger = lgr;
        this.deptRepo = deptRepo;
    }

    @Override
    public Collection<DepartmentDto> getAll() {
        /* use .findEmAll() or .findEmAllByMyCoolProjectionExample() here */
        Collection<DepartmentDto> returnItems = this.deptRepo.findEmAllByMyCoolProjectionExample();
        return returnItems;
    }

    @Override
    public Collection<DepartmentDto> getAllNoEmployees() {
        Collection<DepartmentDto> returnItems = this.deptRepo.findOrphanedDepartments();
        return returnItems;
    }

    @Override
    public Optional<DepartmentDto> getSingle(long key) {
        this.logger.info(String.format("Method getSingle called. (key=\"%1s\")", key));
        Optional<DepartmentDto> returnItem = this.deptRepo.findById(key);
        return returnItem;
    }

    @Override
    public Optional<DepartmentDto> getSingleByName(String deptName) {
        this.logger.info(String.format("Method getSingleByName called. (deptName=\"%1s\")", deptName));
        Optional<DepartmentDto> returnItem = this.deptRepo.findByDepartmentName(deptName);
        return returnItem;
    }

    @Override
    public Collection<DepartmentDto> getDepartmentsOlderThanDate(OffsetDateTime zdt) {
        this.logger.info(String.format("Method getDepartmentsOlderThanDate called. (zdt=\"%1s\")", zdt));
        Collection<DepartmentDto> returnItems = this.deptRepo.findByCreateOffsetDateTimeBefore(zdt);
        return returnItems;
    }

    @Override
    public Collection<DepartmentDto> getByKeys(Set<Long> departmentKeys) {
        this.logger.info(String.format("Method getByKeys called. (departmentKeys=\"%1s\")", departmentKeys));
        Collection<DepartmentDto> returnItems = this.deptRepo.findBySurrogateKeyIn(departmentKeys);
        return returnItems;
    }

    @Override
    public DepartmentDto saveSingle(DepartmentDto item) {
        DepartmentDto returnItem = this.deptRepo.save(item);
        return returnItem;
    }

    @Override
    public int deleteByKey(long key) {
        this.logger.info(String.format("Method deleteByKey called. (deptName=\"%1s\")", key));
        int returnValue = this.deptRepo.deleteByKey(key);
        return returnValue;
    }
}
