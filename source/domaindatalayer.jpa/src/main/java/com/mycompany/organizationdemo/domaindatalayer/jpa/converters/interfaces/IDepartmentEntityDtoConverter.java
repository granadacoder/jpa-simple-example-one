package com.mycompany.organizationdemo.domaindatalayer.jpa.converters.interfaces;

import com.mycompany.organizationdemo.domain.dtos.DepartmentDto;
import com.mycompany.organizationdemo.domaindatalayer.jpa.entities.DepartmentJpaEntity;

import java.util.Collection;

public interface IDepartmentEntityDtoConverter {

    DepartmentDto convertToDto(DepartmentJpaEntity entity);

    Collection<DepartmentDto> convertToDtos(final Collection<DepartmentJpaEntity> departmentJpaEntities);

    DepartmentJpaEntity convertToEntity(DepartmentDto dto);
}
