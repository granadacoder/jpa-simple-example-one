package com.mycompany.organizationdemo.businesslayer.managers;

import com.mycompany.organizationdemo.businesslayer.managers.interfaces.IDepartmentManager;
import com.mycompany.organizationdemo.domain.entities.Department;
import com.mycompany.organizationdemo.domaindatalayer.interfaces.IDepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class DepartmentManager implements IDepartmentManager {

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
    public Collection<Department> getAll() {
        List<Department> returnItems = this.deptRepo.findAll();
        return returnItems;
    }

    @Override
    public Optional<Department> getSingle(long key) {
        this.logger.info(String.format("Method getSingle called. (key=\"%1s\")", key));
        Optional<Department> returnItem = this.deptRepo.findById(key);
        return  returnItem;
    }

    @Override
    public Optional<Department> getSingleByName(String deptName) {
        this.logger.info(String.format("Method getSingleByName called. (deptName=\"%1s\")", deptName));

        Optional<Department> returnItem = this.deptRepo.findDepartmentByDepartmentNameEquals(deptName);
        return  returnItem;
    }

    public Collection<Department> getDepartmentsOlderThanDate(OffsetDateTime zdt)
    {
        this.logger.info(String.format("Method getDepartmentsOlderThanDate called. (zdt=\"%1s\")", zdt));

        Collection<Department> returnItems = this.deptRepo.findByCreateOffsetDateTimeBefore(zdt);
        return returnItems;
    }

    @Override
    public Department saveSingle(Department item) {
        Department returnItem = this.deptRepo.save(item);
        return  returnItem;
    }
}
