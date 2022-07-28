package com.mycompany.organizationdemo.domaindatalayer.jpa.repositories.commands;

import com.mycompany.organizationdemo.domain.dtos.DepartmentDto;
import com.mycompany.organizationdemo.domaindatalayer.interfaces.IDepartmentCommandRepository;
import com.mycompany.organizationdemo.domaindatalayer.jpa.converters.interfaces.IDepartmentEntityDtoConverter;
import com.mycompany.organizationdemo.domaindatalayer.jpa.entities.DepartmentJpaEntity;
import com.mycompany.organizationdemo.domaindatalayer.jpa.repositories.internal.InternalDepartmentJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public final class DepartmentCommandRepository implements IDepartmentCommandRepository {

    public static final String ERROR_MSG_LOGGER_IS_NULL = "Logger is null";

    public static final String ERROR_MSG_I_DEPARTMENT_ENTITY_DTO_CONVERTER_IS_NULL = "IDepartmentEntityDtoConverter is null";

    public static final String ERROR_MSG_INTERNAL_DEPARTMENT_JPA_REPOSITORY_IS_NULL = "InternalDepartmentJpaRepository is null";

    public static final String ERROR_MSG_ENTITY_MANAGER_IS_NULL = "EntityManager is null";

    private final Logger logger;

    private final IDepartmentEntityDtoConverter deptConverter;

    private final InternalDepartmentJpaRepository internalDepartmentJpaRepository;

    private final EntityManager entityManager;

    /* The Inject annotation marks which constructor to use for IoC when there are multiple constructors */
    @Inject
    public DepartmentCommandRepository(EntityManager em, IDepartmentEntityDtoConverter deptConverter, InternalDepartmentJpaRepository deptCommandRepo) {
        this(LoggerFactory.getLogger(DepartmentCommandRepository.class), em, deptConverter, deptCommandRepo);
    }

    public DepartmentCommandRepository(Logger lgr, EntityManager em, IDepartmentEntityDtoConverter deptConverter, InternalDepartmentJpaRepository internalDepartmentJpaRepository) {
        if (null == lgr) {
            throw new IllegalArgumentException(ERROR_MSG_LOGGER_IS_NULL);
        }

        if (null == deptConverter) {
            throw new IllegalArgumentException(ERROR_MSG_I_DEPARTMENT_ENTITY_DTO_CONVERTER_IS_NULL);
        }

        if (null == internalDepartmentJpaRepository) {
            throw new IllegalArgumentException(ERROR_MSG_INTERNAL_DEPARTMENT_JPA_REPOSITORY_IS_NULL);
        }

        if (null == em) {
            throw new IllegalArgumentException(ERROR_MSG_ENTITY_MANAGER_IS_NULL);
        }

        this.logger = lgr;

        /* converter to convert jpa entities to dto objects */
        this.deptConverter = deptConverter;

        /* pass the simple crud stuff off to the InternalDepartmentJpaRepository */
        this.internalDepartmentJpaRepository = internalDepartmentJpaRepository;

        /* keep the EM around for more advanced query needs */
        this.entityManager = em;

    /* there are other ways to deal with the "beyond the simple" functionality limits of JpaRepository.
    see https://dimitr.im/writing-dynamic-queries-with-spring-data-jpa
    which discusses
    https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Example.html
     and
    https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/domain/Specification.html
     */
    }

    @Override
    public DepartmentDto save(DepartmentDto item) {
        DepartmentJpaEntity entity = this.deptConverter.convertToEntity(item);
        DepartmentJpaEntity savedEntity = this.internalDepartmentJpaRepository.save(entity);
        DepartmentDto returnItem = this.deptConverter.convertToDto(savedEntity);
        return returnItem;
    }

    @Override
    public int deleteByKey(long departmentKey) {
        int returnValue = this.internalDepartmentJpaRepository.deleteDepartmentByDepartmentKey(departmentKey);
        return returnValue;
    }

}
