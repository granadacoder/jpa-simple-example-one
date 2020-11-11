package com.mycompany.organizationdemo.domaindatalayer.jpa.entities;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;

@javax.persistence.metamodel.StaticMetamodel(DepartmentJpaEntity.class)

public class DepartmentJpaEntity_ {
    public static volatile SingularAttribute<DepartmentJpaEntity, Long> departmentKey;

    public static volatile SetAttribute<DepartmentJpaEntity, EmployeeJpaEntity> employeeJpaEntities;
}

