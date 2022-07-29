package com.mycompany.organizationdemo.businesslayer.managers.commands.interfaces;

import com.mycompany.organizationdemo.domain.dtos.DepartmentDto;

public interface IDepartmentCommandManager {

    DepartmentDto saveSingle(DepartmentDto item);

    long deleteByKey(long key);
}
