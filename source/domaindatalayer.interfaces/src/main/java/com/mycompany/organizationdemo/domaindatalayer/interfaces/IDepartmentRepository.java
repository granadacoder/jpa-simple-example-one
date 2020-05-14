package com.mycompany.organizationdemo.domaindatalayer.interfaces;

import com.mycompany.organizationdemo.domain.entities.Department;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IDepartmentRepository {

    List<Department> findAll();

    Optional<Department> findById(long key);

    Optional<Department> findDepartmentByDepartmentNameEquals(String departmentName);

    Collection<Department> findByCreateOffsetDateTimeBefore(OffsetDateTime zdt);

    Department save(Department item);
}
