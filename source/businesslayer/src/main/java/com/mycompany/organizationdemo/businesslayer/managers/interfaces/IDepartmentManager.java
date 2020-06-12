package com.mycompany.organizationdemo.businesslayer.managers.interfaces;

import com.mycompany.organizationdemo.domain.dtos.DepartmentDto;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface IDepartmentManager {

    Collection<DepartmentDto> getAll();

    Collection<DepartmentDto> getAllNoEmployees();

    Optional<DepartmentDto> getSingle(long key);

    Optional<DepartmentDto> getSingleByName(String deptName);

    DepartmentDto saveSingle(DepartmentDto item);

    Collection<DepartmentDto> getByKeys(Set<Long> departmentKeys);

    Collection<DepartmentDto> getDepartmentsOlderThanDate(OffsetDateTime zdt);

    int deleteByKey(long key);
}
