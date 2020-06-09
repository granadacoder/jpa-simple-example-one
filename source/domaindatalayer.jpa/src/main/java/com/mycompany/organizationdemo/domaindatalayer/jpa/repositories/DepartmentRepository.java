package com.mycompany.organizationdemo.domaindatalayer.jpa.repositories;

import com.mycompany.organizationdemo.domain.dtos.DepartmentDto;
import com.mycompany.organizationdemo.domaindatalayer.interfaces.IDepartmentRepository;
import com.mycompany.organizationdemo.domaindatalayer.jpa.converters.interfaces.IDepartmentEntityDtoConverter;
import com.mycompany.organizationdemo.domaindatalayer.jpa.entities.DepartmentJpaEntity;
import com.mycompany.organizationdemo.domaindatalayer.jpa.repositories.internal.InternalDepartmentJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class DepartmentRepository implements IDepartmentRepository {

    private final Logger logger;
    private final IDepartmentEntityDtoConverter deptConverter;
    private final InternalDepartmentJpaRepository deptRepo;

    /* The Inject annotation marks which constructor to use for IoC when there are multiple constructors */
    @Inject
    public DepartmentRepository(IDepartmentEntityDtoConverter deptConverter, InternalDepartmentJpaRepository deptRepo) {
        this(LoggerFactory.getLogger(DepartmentRepository.class), deptConverter, deptRepo);
    }

    public DepartmentRepository(Logger lgr, IDepartmentEntityDtoConverter deptConverter, InternalDepartmentJpaRepository deptRepo) {
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
    public Collection<DepartmentDto> findAll() {
        List<DepartmentJpaEntity> entities = this.deptRepo.findAll();
        /* right here, desperately hoping for each DepartmentJpaEntity in the "entities" to NOT have employees hydrated */
        Collection<DepartmentDto> returnItems = this.deptConverter.convertToDtos(entities);
        return returnItems;
    }

    @Override
    public Optional<DepartmentDto> findById(long key) {
        Optional<DepartmentDto> returnItem = Optional.empty();
        Optional<DepartmentJpaEntity> entity = this.deptRepo.findById(key);
        if (entity.isPresent()) {
            returnItem = Optional.of(this.deptConverter.convertToDto(entity.get()));
        }
        return returnItem;
    }

    @Override
    public Optional<DepartmentDto> findByDepartmentName(String departmentName) {
        Optional<DepartmentDto> returnItem = Optional.empty();
        Optional<DepartmentJpaEntity> entity = this.deptRepo.findDepartmentByDepartmentNameEquals(departmentName);
        if (entity.isPresent()) {
            returnItem = Optional.of(this.deptConverter.convertToDto(entity.get()));
        }
        return returnItem;
    }

    @Override
    public Collection<DepartmentDto> findByCreateOffsetDateTimeBefore(OffsetDateTime zdt) {
        Collection<DepartmentJpaEntity> entities = this.deptRepo.findByCreateOffsetDateTimeBefore(zdt);
        /* right here, desperately hoping for each DepartmentJpaEntity in the "entities" to NOT have employees hydrated */
        Collection<DepartmentDto> returnItems = this.deptConverter.convertToDtos(entities);
        return returnItems;
    }

    @Override
    public Collection<DepartmentDto> findBySurrogateKeyIn(Set<Long> departmentKeys) {
        Collection<DepartmentJpaEntity> entities = this.deptRepo.findDepartmentByDepartmentKeyIn(departmentKeys);
        /* right here, desperately hoping for each DepartmentJpaEntity in the "entities" to NOT have employees hydrated */
        Collection<DepartmentDto> returnItems = this.deptConverter.convertToDtos(entities);
        return returnItems;
    }

    @Override
    public DepartmentDto save(DepartmentDto item) {
        DepartmentJpaEntity entity = this.deptConverter.convertToEntity(item);
        DepartmentJpaEntity savedEntity = this.deptRepo.save(entity);
        DepartmentDto returnItem = this.deptConverter.convertToDto(savedEntity);
        return returnItem;
    }

    @Override
    public int deleteByKey(long departmentKey) {
        int returnValue = this.deptRepo.deleteDepartmentByDepartmentKey(departmentKey);
        return returnValue;
    }
}
