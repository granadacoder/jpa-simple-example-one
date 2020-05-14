package com.mycompany.organizationdemo.domaindatalayer.jpa.repositories;

import com.mycompany.organizationdemo.domain.entities.Department;
import com.mycompany.organizationdemo.domaindatalayer.interfaces.IDepartmentRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Optional;

public interface DepartmentJpaRepository extends JpaRepository<Department, Long>, IDepartmentRepository {

    /* #vsnote.  notice that only 2 methods are defined here, but the IDepartmentRepository has more than that.   JpaRepository is satisfying several of the "usual crud" methods */

    /* #vsnote note the below, this is "lookup strategy".  see https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods */
    Optional<Department> findDepartmentByDepartmentNameEquals(String departmentName);

    Collection<Department> findByCreateOffsetDateTimeBefore(OffsetDateTime zdt);
}