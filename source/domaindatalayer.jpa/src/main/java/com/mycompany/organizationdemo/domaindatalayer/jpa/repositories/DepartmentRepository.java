package com.mycompany.organizationdemo.domaindatalayer.jpa.repositories;

import com.mycompany.organizationdemo.domain.dtos.DepartmentDto;
import com.mycompany.organizationdemo.domaindatalayer.interfaces.IDepartmentRepository;
import com.mycompany.organizationdemo.domaindatalayer.jpa.converters.interfaces.IDepartmentEntityDtoConverter;
import com.mycompany.organizationdemo.domaindatalayer.jpa.entities.DepartmentJpaEntity;
import com.mycompany.organizationdemo.domaindatalayer.jpa.entities.DepartmentJpaEntity_;
import com.mycompany.organizationdemo.domaindatalayer.jpa.entities.EmployeeJpaEntity;
import com.mycompany.organizationdemo.domaindatalayer.jpa.entities.EmployeeJpaEntity_;
import com.mycompany.organizationdemo.domaindatalayer.jpa.projections.DepartmentLiteProjection;
import com.mycompany.organizationdemo.domaindatalayer.jpa.repositories.internal.InternalDepartmentJpaRepository;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class DepartmentRepository implements IDepartmentRepository {

  private final Logger logger;
  private final IDepartmentEntityDtoConverter deptConverter;
  private final InternalDepartmentJpaRepository internalDepartmentJpaRepository;
  private final EntityManager entityManager;

  /* The Inject annotation marks which constructor to use for IoC when there are multiple constructors */
  @Inject
  public DepartmentRepository(EntityManager em, IDepartmentEntityDtoConverter deptConverter, InternalDepartmentJpaRepository deptRepo) {
    this(LoggerFactory.getLogger(DepartmentRepository.class), em, deptConverter, deptRepo);
  }

  public DepartmentRepository(Logger lgr, EntityManager em, IDepartmentEntityDtoConverter deptConverter, InternalDepartmentJpaRepository internalDepartmentJpaRepository) {
    if (null == lgr) {
      throw new IllegalArgumentException("Logger is null");
    }

    if (null == deptConverter) {
      throw new IllegalArgumentException("IDepartmentEntityDtoConverter is null");
    }

    if (null == internalDepartmentJpaRepository) {
      throw new IllegalArgumentException("InternalDepartmentJpaRepository is null");
    }

    if (null == em) {
      throw new IllegalArgumentException("EntityManager is null");
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
  public Collection<DepartmentDto> findEmAll() {
    List<DepartmentJpaEntity> entities = this.internalDepartmentJpaRepository.findAll();
    /* right here, desperately hoping for each DepartmentJpaEntity in the "entities" to NOT have employees hydrated */
    Collection<DepartmentDto> returnItems = this.deptConverter.convertToDtos(entities);
    return returnItems;
  }

  @Override
  public Collection<DepartmentDto> findEmAllByMyCoolProjectionExample() {
    Pageable topTen = PageRequest.of(0, 25);
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
            .where(criteriaBuilder.equal(childPath, parentPath));//subquery restriction

    //main query selection
    departmentQuery.select(departmentRoot)
            .where(criteriaBuilder.not(criteriaBuilder.exists(employeeSubquery)));

    TypedQuery<DepartmentJpaEntity> typedQuery = this.entityManager.createQuery(departmentQuery);
    List<DepartmentJpaEntity> resultList = typedQuery.getResultList();

    Collection<DepartmentDto> returnItems = this.deptConverter.convertToDtos(resultList);
    return returnItems;
  }
}
