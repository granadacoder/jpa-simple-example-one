package com.mycompany.organizationdemo.domaindatalayer.jpa.repositories;

import com.mycompany.organizationdemo.domain.entities.Department;
import com.mycompany.organizationdemo.domaindatalayer.interfaces.IDepartmentRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface DepartmentJpaRepository extends JpaRepository<Department, Long>, IDepartmentRepository {

    //@Query(value = "SELECT d FROM Department d") /* this still suffers from N+1 number of queries issue */
    //@Query(value = "SELECT d FROM Department d LEFT JOIN FETCH d.employees") /* without this  custom @Query, you will get N+1 queries.  one SELECT for all the deparments, and then for EACH department, get the employees.  this forces a single query.  spring-data-voodoo */
    //@EntityGraph(attributePaths = {"employees"})

    //The below does NOT work :(
    //@EntityGraph(value = "departmentJustScalarsEntityGraphName", type = EntityGraph.EntityGraphType.FETCH)

    /* the below 'select new' ..  is called a "Projection" - https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#projections */
    @Query("select new Department(d.departmentKey, d.departmentName, d.createOffsetDateTime) from Department d")
    List<Department> findAll();

    @EntityGraph(attributePaths = {"employees"})
    Optional<Department> findById(long key);

    /* #vsnote.  notice that only 4 methods are defined here, but the IDepartmentRepository has more than that.   JpaRepository is satisfying several of the "usual crud" methods */

    //@EntityGraph(attributePaths = {"employees"})
    @Query("SELECT d FROM Department d LEFT JOIN FETCH d.employees WHERE d.departmentName = :departmentName") /* this works because departmentName is a UNIQUE constraint...otherwise it might give back duplicate parents (Departments) */
    Optional<Department> findDepartmentByDepartmentNameEquals(@Param("departmentName") String departmentName);

    /* #vsnote note the below, this is "lookup strategy".  see https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods */
    Collection<Department> findByCreateOffsetDateTimeBefore(OffsetDateTime zdt);

    //@Query("SELECT d FROM Department d LEFT JOIN FETCH d.employees WHERE d.departmentKey IN ?1")  /* here a Query will bring back repeat parent (Department) rows */
    @EntityGraph(attributePaths = {"employees"})
    Collection<Department> findDepartmentByDepartmentKeyIn(Set<Long> departmentKeys);

    @Modifying
    @Transactional
        ////@Query("DELETE FROM Department d WHERE d.departmentKey.id = ?1") /* won't handle the children */
    int deleteDepartmentByDepartmentKey(long departmentKey); /* suffers from N+1 problem */
}