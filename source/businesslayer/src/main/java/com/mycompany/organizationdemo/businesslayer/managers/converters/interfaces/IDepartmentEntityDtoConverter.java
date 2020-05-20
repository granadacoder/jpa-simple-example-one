package com.mycompany.organizationdemo.businesslayer.managers.converters.interfaces;

import com.mycompany.organizationdemo.domain.dtos.DepartmentDto;
import com.mycompany.organizationdemo.domain.entities.Department;

import java.util.Collection;

public interface IDepartmentEntityDtoConverter {

    DepartmentDto convertToDto(Department entity);

    Collection<DepartmentDto> convertToDtos(final Collection<Department> departments);
}
