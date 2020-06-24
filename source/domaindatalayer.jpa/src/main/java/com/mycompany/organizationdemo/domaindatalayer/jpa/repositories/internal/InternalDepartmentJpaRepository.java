package com.mycompany.organizationdemo.domaindatalayer.jpa.repositories.internal;

import com.mycompany.organizationdemo.domaindatalayer.jpa.entities.DepartmentJpaEntity;
import com.mycompany.organizationdemo.domaindatalayer.jpa.projections.DepartmentLiteProjection;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/* java does not have "internal class, internal interface" like C#  :(  */
public interface InternalDepartmentJpaRepository extends JpaRepository<DepartmentJpaEntity, Long> {

  //@Query(value = "SELECT d FROM DepartmentJpaEntity d") /* this still suffers from N+1 number of queries issue */
  //@Query(value = "SELECT d FROM DepartmentJpaEntity d LEFT JOIN FETCH d.employees") /* without this  custom @Query, you will get N+1 queries.  one SELECT for all the deparments, and then for EACH department, get the employees.  this forces a single query.  spring-data-voodoo */
  //@EntityGraph(attributePaths = {"employees"})

  //The below does NOT work :(
  //@EntityGraph(value = "departmentJustScalarsEntityGraphName", type = EntityGraph.EntityGraphType.FETCH)

  /* the below is projection by 'select new' .. reusing the primary object with overloaded constructor - https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#projections */
  @Query("select new DepartmentJpaEntity(d.departmentKey, d.departmentName) from DepartmentJpaEntity d")
  List<DepartmentJpaEntity> findAll();

  /* projection by interface */
  List<DepartmentLiteProjection> findAllProjectedBy(Pageable pageable);


  @EntityGraph(attributePaths = {"employeeJpaEntities"})
  Optional<DepartmentJpaEntity> findById(Long key);


  /* #vsnote.  notice that only 4 methods are defined here, but the IDepartmentRepository has more than that.   JpaRepository is satisfying several of the "usual crud" methods */

  //@EntityGraph(attributePaths = {"employees"})
  @Query("SELECT d FROM DepartmentJpaEntity d LEFT JOIN FETCH d.employeeJpaEntities WHERE d.departmentName = :departmentName") /* this works because departmentName is a UNIQUE constraint...otherwise it might give back duplicate parents (Departments) */
  Optional<DepartmentJpaEntity> findDepartmentByDepartmentNameEquals(@Param("departmentName") String departmentName);

  /* #vsnote note the below, this is "lookup strategy".  see https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods */
  @EntityGraph(attributePaths = {"employeeJpaEntities"})
  Collection<DepartmentJpaEntity> findByCreateOffsetDateTimeBefore(OffsetDateTime zdt);

  //@Query("SELECT d FROM DepartmentJpaEntity d LEFT JOIN FETCH d.employees WHERE d.departmentKey IN ?1")  /* here a Query will bring back repeat parent (DepartmentJpaEntity) rows */
  @EntityGraph(attributePaths = {"employeeJpaEntities"})
  Collection<DepartmentJpaEntity> findDepartmentByDepartmentKeyIn(Set<Long> departmentKeys);

  @Modifying
  @Transactional
    ////@Query("DELETE FROM DepartmentJpaEntity d WHERE d.departmentKey.id = ?1") /* won't handle the children */
  int deleteDepartmentByDepartmentKey(long departmentKey); /* suffers from N+1 problem */

  @Override
  @org.springframework.transaction.annotation.Transactional
  DepartmentJpaEntity save(DepartmentJpaEntity entity);

  @Override
  <S extends DepartmentJpaEntity> List<S> saveAll(Iterable<S> entities);
}