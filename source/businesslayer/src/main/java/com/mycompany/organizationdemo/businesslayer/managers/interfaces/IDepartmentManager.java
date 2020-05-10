package com.mycompany.organizationdemo.businesslayer.managers.interfaces;

import com.mycompany.organizationdemo.domain.entities.Department;

import java.util.Optional;

public interface IDepartmentManager {

    Iterable<Department> getAll();

    Optional<Department> getSingle(long key);

    Department save(Department item);
}
