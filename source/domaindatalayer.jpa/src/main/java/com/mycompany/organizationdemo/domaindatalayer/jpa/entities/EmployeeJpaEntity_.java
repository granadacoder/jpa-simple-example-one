package com.mycompany.organizationdemo.domaindatalayer.jpa.entities;

import javax.persistence.metamodel.SingularAttribute;

@javax.persistence.metamodel.StaticMetamodel(EmployeeJpaEntity.class)

@SuppressWarnings({"checkstyle:typename", "checkstyle:visibilitymodifier"}) /* Metamodel's violate normal naming rules :( */
public class EmployeeJpaEntity_ {
    public static volatile SingularAttribute<EmployeeJpaEntity, Long> employeeKey;

    public static volatile SingularAttribute<EmployeeJpaEntity, String> lastName;

    public static volatile SingularAttribute<EmployeeJpaEntity, DepartmentJpaEntity> parentDepartmentJpaEntity;

    public static volatile SingularAttribute<EmployeeJpaEntity, Long> parentDepartmentJpaEntityDepartmentKey;
}
