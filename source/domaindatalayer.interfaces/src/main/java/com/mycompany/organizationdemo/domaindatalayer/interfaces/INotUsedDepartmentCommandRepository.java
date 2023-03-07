package com.mycompany.organizationdemo.domaindatalayer.interfaces;

import com.mycompany.organizationdemo.domain.dtos.DepartmentDto;

public interface INotUsedDepartmentCommandRepository {

    DepartmentDto save(DepartmentDto item);

    int deleteByKey(long departmentKey);

}
