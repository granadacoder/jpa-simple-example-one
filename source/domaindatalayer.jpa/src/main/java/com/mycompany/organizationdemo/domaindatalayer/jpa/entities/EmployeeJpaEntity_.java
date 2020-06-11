package com.mycompany.organizationdemo.domaindatalayer.jpa.entities;

import javax.persistence.metamodel.SingularAttribute;

@javax.persistence.metamodel.StaticMetamodel(EmployeeJpaEntity.class)

public class EmployeeJpaEntity_ {
  public static volatile SingularAttribute<EmployeeJpaEntity, Long> employeeKey;
  public static volatile SingularAttribute<EmployeeJpaEntity, DepartmentJpaEntity> parentDepartmentJpaEntity;
  public static volatile SingularAttribute<EmployeeJpaEntity, Long> parentDepartmentJpaEntityDepartmentKey;
}
