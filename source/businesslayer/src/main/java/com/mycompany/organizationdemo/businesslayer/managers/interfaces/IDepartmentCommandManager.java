package com.mycompany.organizationdemo.businesslayer.managers.interfaces;

import com.mycompany.organizationdemo.domain.dtos.DepartmentDto;

public interface IDepartmentCommandManager {

    DepartmentDto saveSingle(DepartmentDto item);

    int deleteByKey(long key);
}
