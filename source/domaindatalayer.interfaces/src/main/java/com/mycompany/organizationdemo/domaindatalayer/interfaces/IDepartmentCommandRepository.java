package com.mycompany.organizationdemo.domaindatalayer.interfaces;

import com.mycompany.organizationdemo.domain.dtos.DepartmentDto;

public interface IDepartmentCommandRepository {

    DepartmentDto save(DepartmentDto item);

    int deleteByKey(long departmentKey);

}
