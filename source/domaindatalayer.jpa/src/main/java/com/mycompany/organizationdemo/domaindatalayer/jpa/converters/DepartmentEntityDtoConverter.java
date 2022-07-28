package com.mycompany.organizationdemo.domaindatalayer.jpa.converters;

import com.mycompany.organizationdemo.domain.dtos.DepartmentDto;
import com.mycompany.organizationdemo.domain.dtos.EmployeeDto;
import com.mycompany.organizationdemo.domaindatalayer.jpa.converters.interfaces.IDepartmentEntityDtoConverter;
import com.mycompany.organizationdemo.domaindatalayer.jpa.entities.DepartmentJpaEntity;
import com.mycompany.organizationdemo.domaindatalayer.jpa.entities.EmployeeJpaEntity;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Collection;
import java.util.stream.Collectors;

/* see the philosophy behind this .. at : https://www.baeldung.com/entity-to-and-from-dto-for-a-java-spring-application
 */

public final class DepartmentEntityDtoConverter implements IDepartmentEntityDtoConverter {

    public static final String ERROR_MSG_LOGGER_IS_NULL = "Logger is null";

    private final Logger logger;

    private final ModelMapper modelMapper;

    /* The Inject annotation marks which constructor to use for IoC when there are multiple constructors */
    @Inject
    public DepartmentEntityDtoConverter(ModelMapper mm) {
        this(LoggerFactory.getLogger(DepartmentEntityDtoConverter.class), mm);
    }

    public DepartmentEntityDtoConverter(Logger lgr, ModelMapper mm) {

        if (null == lgr) {
            throw new IllegalArgumentException(ERROR_MSG_LOGGER_IS_NULL);
        }

        if (null == mm) {
            throw new IllegalArgumentException("ModelMapper is null");
        }

        this.logger = lgr;
        this.modelMapper = mm;

    /*
    this.modelMapper.addMappings(new PropertyMap<DepartmentDto, DepartmentJpaEntity>() {
      @Override
      protected void configure() {
        skip(destination.getEmployeeJpaEntities());
      }
    });
     */

        this.modelMapper.typeMap(DepartmentJpaEntity.class, DepartmentDto.class)
                .addMappings(mapper -> mapper.map(DepartmentJpaEntity::getEmployeeJpaEntities, DepartmentDto::setEmployees));

        this.modelMapper.typeMap(DepartmentDto.class, DepartmentJpaEntity.class)
                .addMappings(mapper -> mapper.map(DepartmentDto::getEmployees, DepartmentJpaEntity::setEmployeeJpaEntities));
    }

    @Override
    public DepartmentDto convertToDto(final DepartmentJpaEntity entity) {
        DepartmentDto returnItem = modelMapper.map(entity, DepartmentDto.class);

        if (null != returnItem && null != returnItem.getEmployees()) {
            for (EmployeeDto currentEmp : returnItem.getEmployees()
            ) {
                currentEmp.setParentDepartmentKey(returnItem.getDepartmentKey());
            }
        }

        return returnItem;
    }

    public Collection<DepartmentDto> convertToDtos(final Collection<DepartmentJpaEntity> departmentJpaEntities) {
        Collection<DepartmentDto> returnItems = departmentJpaEntities.stream()
                .map(entity -> convertToDto(entity))
                .collect(Collectors.toList());
        return returnItems;
    }

    @Override
    public DepartmentJpaEntity convertToEntity(DepartmentDto dto) {
        DepartmentJpaEntity returnItem = modelMapper.map(dto, DepartmentJpaEntity.class);

        if (null != returnItem && null != returnItem.getEmployeeJpaEntities()) {
            for (EmployeeJpaEntity currentEmp : returnItem.getEmployeeJpaEntities()
            ) {
                currentEmp.setParentDepartmentJpaEntity(returnItem);
            }
        }

        return returnItem;
    }
}
