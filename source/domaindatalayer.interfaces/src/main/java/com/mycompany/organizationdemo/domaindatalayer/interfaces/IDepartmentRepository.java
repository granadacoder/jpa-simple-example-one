package com.mycompany.organizationdemo.domaindatalayer.interfaces;

import com.mycompany.organizationdemo.domain.entities.Department;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IDepartmentRepository {

    List<Department> findAll();

    Optional<Department> findById(long key);

    Optional<Department> findDepartmentByDepartmentNameEquals(String departmentName);

    Collection<Department> findByCreateOffsetDateTimeBefore(OffsetDateTime zdt);

    Collection<Department> findDepartmentByDepartmentKeyIn(Set<Long> departmentKeys);

    Department save(Department item);
}
