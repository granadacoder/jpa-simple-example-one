package com.mycompany.organizationdemo.businesslayer.managers.interfaces;

import com.mycompany.organizationdemo.domain.entities.Department;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Optional;

public interface IDepartmentManager {

    Collection<Department> getAll();

    Optional<Department> getSingle(long key);

    Optional<Department> getSingleByName(String deptName);

    Department saveSingle(Department item);

    Collection<Department> getDepartmentsOlderThanDate(OffsetDateTime zdt);
}
