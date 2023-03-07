package com.mycompany.organizationdemo.domaindatalayer.interfaces.queries;

import com.mycompany.organizationdemo.domain.dtos.DepartmentDto;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface IDepartmentQueryRepository {

    long getTheAllCount();

    Collection<DepartmentDto> findEmAll();

    Collection<DepartmentDto> findEmAllDeep();

    Collection<DepartmentDto> findAnyDeepByEmployeeLastName(String empLastName);

    Collection<DepartmentDto> findEmAllByMyCoolProjectionExample();

    Optional<DepartmentDto> findById(long key);

    Optional<DepartmentDto> findByDepartmentName(String departmentName);

    Collection<DepartmentDto> findByCreateOffsetDateTimeBefore(OffsetDateTime zdt);

    Collection<DepartmentDto> findBySurrogateKeyIn(Set<Long> departmentKeys);

    Collection<DepartmentDto> findOrphanedDepartments();
}
