package com.mycompany.organizationdemo.domaindatalayer.jpa.repositories.queries;

import com.mycompany.organizationdemo.domain.dtos.DepartmentDto;
import com.mycompany.organizationdemo.domaindatalayer.interfaces.queries.IDepartmentQueryRepository;
import com.mycompany.organizationdemo.domaindatalayer.jpa.converters.interfaces.IDepartmentEntityDtoConverter;
import com.mycompany.organizationdemo.domaindatalayer.jpa.entities.DepartmentJpaEntity;
import com.mycompany.organizationdemo.domaindatalayer.jpa.entities.DepartmentJpaEntity_;
import com.mycompany.organizationdemo.domaindatalayer.jpa.entities.EmployeeJpaEntity;
import com.mycompany.organizationdemo.domaindatalayer.jpa.entities.EmployeeJpaEntity_;
import com.mycompany.organizationdemo.domaindatalayer.jpa.projections.DepartmentLiteProjection;
import com.mycompany.organizationdemo.domaindatalayer.jpa.repositories.internal.InternalDepartmentJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public final class DepartmentQueryRepository implements IDepartmentQueryRepository {

    public static final String ERROR_MSG_LOGGER_IS_NULL = "Logger is null";

    public static final String ERROR_MSG_I_DEPARTMENT_ENTITY_DTO_CONVERTER_IS_NULL = "IDepartmentEntityDtoConverter is null";

    public static final String ERROR_MSG_INTERNAL_DEPARTMENT_JPA_REPOSITORY_IS_NULL = "InternalDepartmentJpaRepository is null";

    public static final String ERROR_MSG_ENTITY_MANAGER_IS_NULL = "EntityManager is null";

    public static final int PAGE_SIZE_TEN = 10;

    private final Logger logger;

    private final IDepartmentEntityDtoConverter deptConverter;

    private final InternalDepartmentJpaRepository internalDepartmentJpaRepository;

    private final EntityManager entityManager;

    /* The Inject annotation marks which constructor to use for IoC when there are multiple constructors */
    @Inject
    public DepartmentQueryRepository(EntityManager em, IDepartmentEntityDtoConverter deptConverter, InternalDepartmentJpaRepository deptCommandRepo) {
        this(LoggerFactory.getLogger(DepartmentQueryRepository.class), em, deptConverter, deptCommandRepo);
    }

    public DepartmentQueryRepository(Logger lgr, EntityManager em, IDepartmentEntityDtoConverter deptConverter, InternalDepartmentJpaRepository internalDepartmentJpaRepository) {
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
    public long getTheAllCount() {
        long returnValue = this.internalDepartmentJpaRepository.count();
        return returnValue;
    }

    @Override
    public Collection<DepartmentDto> findEmAll() {
        List<DepartmentJpaEntity> entities = this.internalDepartmentJpaRepository.findAll();
        /* right here, desperately hoping for each DepartmentJpaEntity in the "entities" to NOT have employees hydrated */
        Collection<DepartmentDto> returnItems = this.deptConverter.convertToDtos(entities);
        return returnItems;
    }

    @Override
    public Collection<DepartmentDto> findEmAllByMyCoolProjectionExample() {
        Pageable topTen = PageRequest.of(0, PAGE_SIZE_TEN);
        List<DepartmentLiteProjection> projections = this.internalDepartmentJpaRepository.findAllProjectedBy(/*DepartmentLiteProjection.class, */topTen);

        /* change to use IDepartmentEntityDtoConverter , but hand map for now */
        Collection<DepartmentDto> returnItems = new ArrayList<>();
        if (null != projections) {
            for (DepartmentLiteProjection parentProjection : projections) {
                DepartmentDto dto = new DepartmentDto();
                dto.setDepartmentKey(parentProjection.getDepartmentKey());
                if (null != parentProjection.getDepartmentName()) {
                    dto.setDepartmentName(parentProjection.getDepartmentName());
                }

                returnItems.add(dto);
            }
        }

        return returnItems;
    }

    @Override
    public Optional<DepartmentDto> findById(long key) {
        Optional<DepartmentDto> returnItem = Optional.empty();
        Optional<DepartmentJpaEntity> entity = this.internalDepartmentJpaRepository.findById(key);
        if (entity.isPresent()) {
            returnItem = Optional.of(this.deptConverter.convertToDto(entity.get()));
        }
        return returnItem;
    }

    @Override
    public Optional<DepartmentDto> findByDepartmentName(String departmentName) {
        Optional<DepartmentDto> returnItem = Optional.empty();
        Optional<DepartmentJpaEntity> entity = this.internalDepartmentJpaRepository.findDepartmentByDepartmentNameEquals(departmentName);
        if (entity.isPresent()) {
            returnItem = Optional.of(this.deptConverter.convertToDto(entity.get()));
        }
        return returnItem;
    }

    @Override
    public Collection<DepartmentDto> findByCreateOffsetDateTimeBefore(OffsetDateTime zdt) {
        Collection<DepartmentJpaEntity> entities = this.internalDepartmentJpaRepository.findByCreateOffsetDateTimeBefore(zdt);
        /* right here, desperately hoping for each DepartmentJpaEntity in the "entities" to NOT have employees hydrated */
        Collection<DepartmentDto> returnItems = this.deptConverter.convertToDtos(entities);
        return returnItems;
    }

    @Override
    public Collection<DepartmentDto> findBySurrogateKeyIn(Set<Long> departmentKeys) {
        Collection<DepartmentJpaEntity> entities = this.internalDepartmentJpaRepository.findDepartmentByDepartmentKeyIn(departmentKeys);
        /* right here, desperately hoping for each DepartmentJpaEntity in the "entities" to NOT have employees hydrated */
        Collection<DepartmentDto> returnItems = this.deptConverter.convertToDtos(entities);
        return returnItems;
    }

    @Override
    public Collection<DepartmentDto> findOrphanedDepartments() {

    /*
    * Wow.  SpringJPA for "WHERE NOT EXISTS"
    *
    * C#/EFCore pseudo example equivalent.  #noMetaModels

          //find (and only hydrate) Customer(s) that have OrderDetails where the Product.Name is NOT CareBears
         IEnumerable<Customer> justCustomersHydrated = ormContext.Customers
            .Where(cust => cust.Orders
            .SelectMany(ord => ord.OrderDetails)
            .Select(det => det.Product.Where(det => det.Product.Name != "CareBears")
            .Any());
    * */

        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();

        //main query
        CriteriaQuery<DepartmentJpaEntity> departmentQuery = criteriaBuilder.createQuery(DepartmentJpaEntity.class);
        Root<DepartmentJpaEntity> departmentRoot = departmentQuery.from(DepartmentJpaEntity.class);

        //subquery
        Subquery<EmployeeJpaEntity> employeeSubquery = departmentQuery.subquery(EmployeeJpaEntity.class);
        Root<EmployeeJpaEntity> employeeRoot = employeeSubquery.from(EmployeeJpaEntity.class);

        //paths used to match
        Path<DepartmentJpaEntity> childPath = employeeRoot.get(EmployeeJpaEntity_.parentDepartmentJpaEntity);
        Path<Long> parentPath = departmentRoot.get(DepartmentJpaEntity_.departmentKey);

        employeeSubquery.select(employeeRoot)//subquery selection
                .where(criteriaBuilder.equal(childPath, parentPath)); //subquery restriction

        //main query selection
        departmentQuery.select(departmentRoot)
                .where(criteriaBuilder.not(criteriaBuilder.exists(employeeSubquery)));

        TypedQuery<DepartmentJpaEntity> typedQuery = this.entityManager.createQuery(departmentQuery);
        List<DepartmentJpaEntity> resultList = typedQuery.getResultList();

        Collection<DepartmentDto> returnItems = this.deptConverter.convertToDtos(resultList);
        return returnItems;
    }

    @Override
    public Collection<DepartmentDto> findAnyDeepByEmployeeLastName(String empLastName) {

        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();

        //main query
        CriteriaQuery<DepartmentJpaEntity> departmentQuery = criteriaBuilder.createQuery(DepartmentJpaEntity.class);
        Root<DepartmentJpaEntity> departmentRoot = departmentQuery.from(DepartmentJpaEntity.class);

        Join<DepartmentJpaEntity, EmployeeJpaEntity> deptToEmpJoin = departmentRoot.join(DepartmentJpaEntity_.employeeJpaEntities);

        ParameterExpression<String> peStringForLastName = criteriaBuilder.parameter(String.class);
        departmentQuery.where(criteriaBuilder.like(deptToEmpJoin.get(EmployeeJpaEntity_.lastName), peStringForLastName));

        TypedQuery<DepartmentJpaEntity> typedQuery = this.entityManager.createQuery(departmentQuery);
        typedQuery.setParameter(peStringForLastName, empLastName);
        List<DepartmentJpaEntity> resultList = typedQuery.getResultList();

        Collection<DepartmentDto> returnItems = this.deptConverter.convertToDtos(resultList);
        return returnItems;

    }

    @Override
    public Collection<DepartmentDto> findEmAllDeep() {
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();

        //main query
        CriteriaQuery<DepartmentJpaEntity> departmentQuery = criteriaBuilder.createQuery(DepartmentJpaEntity.class);
        Root<DepartmentJpaEntity> departmentRoot = departmentQuery.from(DepartmentJpaEntity.class);

        /* so while the result below is not currently used in this method, the below does do the proper JOIN */
        Join<DepartmentJpaEntity, EmployeeJpaEntity> deptToEmpJoin = departmentRoot.join(DepartmentJpaEntity_.employeeJpaEntities);

        TypedQuery<DepartmentJpaEntity> typedQuery = this.entityManager.createQuery(departmentQuery);
        List<DepartmentJpaEntity> resultList = typedQuery.getResultList();

        Collection<DepartmentDto> returnItems = this.deptConverter.convertToDtos(resultList);
        return returnItems;
    }
}
