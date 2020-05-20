package com.mycompany.organizationdemo.businesslayer.managers;

import com.mycompany.organizationdemo.businesslayer.managers.converters.interfaces.IDepartmentEntityDtoConverter;
import com.mycompany.organizationdemo.businesslayer.managers.interfaces.IDepartmentManager;
import com.mycompany.organizationdemo.domain.dtos.DepartmentDto;
import com.mycompany.organizationdemo.domain.entities.Department;
import com.mycompany.organizationdemo.domaindatalayer.interfaces.IDepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class DepartmentManager implements IDepartmentManager {

    private final Logger logger;
    private final IDepartmentEntityDtoConverter deptConverter;
    private final IDepartmentRepository deptRepo;

    /* The Inject annotation marks which constructor to use for IoC when there are multiple constructors */
    @Inject
    public DepartmentManager(IDepartmentEntityDtoConverter deptConverter, IDepartmentRepository deptRepo) {
        this(LoggerFactory.getLogger(DepartmentManager.class), deptConverter, deptRepo);
    }

    public DepartmentManager(Logger lgr, IDepartmentEntityDtoConverter deptConverter, IDepartmentRepository deptRepo) {
        if (null == lgr) {
            throw new IllegalArgumentException("Logger is null");
        }

        if (null == deptConverter) {
            throw new IllegalArgumentException("IDepartmentEntityDtoConverter is null");
        }

        if (null == deptRepo) {
            throw new IllegalArgumentException("IDepartmentDomainData is null");
        }

        this.logger = lgr;
        this.deptConverter = deptConverter;
        this.deptRepo = deptRepo;
    }

    @Override
    public Collection<DepartmentDto> getAll() {
        List<Department> entities = this.deptRepo.findAll();
        Collection<DepartmentDto> returnItems = this.deptConverter.convertToDtos(entities);
        return returnItems;
    }

    @Override
    public Optional<DepartmentDto> getSingle(long key) {
        this.logger.info(String.format("Method getSingle called. (key=\"%1s\")", key));

        Optional<DepartmentDto> returnItem = Optional.empty();

        Optional<Department> entity = this.deptRepo.findById(key);
        if (entity.isPresent()) {
            returnItem = Optional.of(this.deptConverter.convertToDto(entity.get()));
        }

        return returnItem;
    }

    @Override
    public Optional<DepartmentDto> getSingleByName(String deptName) {
        this.logger.info(String.format("Method getSingleByName called. (deptName=\"%1s\")", deptName));

        Optional<DepartmentDto> returnItem = Optional.empty();

        Optional<Department> entity = this.deptRepo.findDepartmentByDepartmentNameEquals(deptName);

        if (entity.isPresent()) {
            returnItem = Optional.of(this.deptConverter.convertToDto(entity.get()));
        }

        return returnItem;
    }

    @Override
    public Collection<DepartmentDto> getDepartmentsOlderThanDate(OffsetDateTime zdt) {
        this.logger.info(String.format("Method getDepartmentsOlderThanDate called. (zdt=\"%1s\")", zdt));
        Collection<Department> entities = this.deptRepo.findByCreateOffsetDateTimeBefore(zdt);
        Collection<DepartmentDto> returnItems = this.deptConverter.convertToDtos(entities);
        return returnItems;
    }

    @Override
    public Collection<DepartmentDto> getByKeys(Set<Long> departmentKeys) {
        this.logger.info(String.format("Method getByKeys called. (departmentKeys=\"%1s\")", departmentKeys));
        Collection<Department> entities = this.deptRepo.findDepartmentByDepartmentKeyIn(departmentKeys);
        Collection<DepartmentDto> returnItems = this.deptConverter.convertToDtos(entities);
        return returnItems;
    }

    @Override
    public Department saveSingle(Department item) {
        Department returnItem = this.deptRepo.save(item);
        return returnItem;
    }

    @Override
    public int deleteByKey(long key) {
        this.logger.info(String.format("Method deleteByKey called. (deptName=\"%1s\")", key));
        int returnValue  = this.deptRepo.deleteDepartmentByDepartmentKey(key);
        return returnValue;
    }
}
