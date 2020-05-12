package com.mycompany.organizationdemo.businesslayer.managers;

import com.mycompany.organizationdemo.businesslayer.managers.interfaces.IDepartmentManager;
import com.mycompany.organizationdemo.domain.entities.Department;
import com.mycompany.organizationdemo.domaindatalayer.interfaces.IDepartmentDomainData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.time.OffsetDateTime;
import java.util.Optional;

public class DepartmentManager implements IDepartmentManager {

    private final Logger logger;
    private final IDepartmentDomainData deptDomainData;

    /* The Inject annotation marks which constructor to use for IoC when there are multiple constructors */
    @Inject
    public DepartmentManager(IDepartmentDomainData deptDomainData) {
        this(LoggerFactory.getLogger(DepartmentManager.class), deptDomainData);
    }

    public DepartmentManager(Logger lgr, IDepartmentDomainData deptDomainData) {
        if (null == lgr) {
            throw new IllegalArgumentException("Logger is null");
        }

        if (null == deptDomainData) {
            throw new IllegalArgumentException("IDepartmentDomainData is null");
        }

        this.logger = lgr;
        this.deptDomainData = deptDomainData;
    }

    @Override
    public Iterable<Department> getAll() {
        Iterable<Department> returnItems = this.deptDomainData.findAll();
        return returnItems;
    }

    @Override
    public Optional<Department> getSingle(long key) {
        Optional<Department> returnItem = this.deptDomainData.findById(key);
        return  returnItem;
    }

    public Iterable<Department> getAllBeforeCreateDate(OffsetDateTime zdt)
    {
        Iterable<Department> returnItems = this.deptDomainData.getByCreateBeforeDate(zdt);
        return returnItems;
    }

    @Override
    public Department save(Department item) {
        Department returnItem = this.deptDomainData.save(item);
        return  returnItem;
    }
}
