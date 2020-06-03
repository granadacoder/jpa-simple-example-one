package com.mycompany.organizationdemo.businesslayer.managers.converters;

import com.mycompany.organizationdemo.businesslayer.managers.converters.interfaces.IDepartmentEntityDtoConverter;
import com.mycompany.organizationdemo.domain.dtos.DepartmentDto;
import com.mycompany.organizationdemo.domain.dtos.EmployeeDto;
import com.mycompany.organizationdemo.domain.entities.Department;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Collection;
import java.util.stream.Collectors;

public class DepartmentEntityDtoConverter implements IDepartmentEntityDtoConverter {

    private final Logger logger;
    private final ModelMapper modelMapper;

    /* The Inject annotation marks which constructor to use for IoC when there are multiple constructors */
    @Inject
    public DepartmentEntityDtoConverter(ModelMapper mm) {
        this(LoggerFactory.getLogger(DepartmentEntityDtoConverter.class), mm);
    }

    public DepartmentEntityDtoConverter(Logger lgr, ModelMapper mm) {
        if (null == lgr) {
            throw new IllegalArgumentException("Logger is null");
        }

        if (null == mm) {
            throw new IllegalArgumentException("ModelMapper is null");
        }

        this.logger = lgr;
        this.modelMapper = mm;

//        this.modelMapper.addMappings(new PropertyMap<DepartmentDto, Department>() {
//            @Override
//            protected void configure() {
//                skip(destination.getEmployees());
//            }
//        });

    }

    @Override
    public DepartmentDto convertToDto(final Department entity) {




        DepartmentDto returnItem = modelMapper.map(entity, DepartmentDto.class);

        if (null != returnItem && null != returnItem.getEmployees()) {
            for (EmployeeDto currentEmp : returnItem.getEmployees()
            ) {
                currentEmp.setParentDepartmentKey(returnItem.getDepartmentKey());
            }
        }

        return returnItem;
    }

    public Collection<DepartmentDto> convertToDtos(final Collection<Department> departments) {
        Collection<DepartmentDto> returnItems = departments.stream()
                .map(entity -> convertToDto(entity))
                .collect(Collectors.toList());
        return returnItems;
    }
}