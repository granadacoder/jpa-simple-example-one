package com.mycompany.organizationdemo.domaindatalayer.interfaces.commands;

import com.mycompany.organizationdemo.domain.dtos.DepartmentDto;

public interface IDepartmentCommandRepository {

    DepartmentDto save(DepartmentDto item);

    long deleteByKey(long departmentKey);

}
