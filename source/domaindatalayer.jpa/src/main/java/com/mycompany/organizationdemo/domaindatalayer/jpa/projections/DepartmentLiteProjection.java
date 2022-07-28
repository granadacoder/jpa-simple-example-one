package com.mycompany.organizationdemo.domaindatalayer.jpa.projections;

/* https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#projections.interfaces */
public interface DepartmentLiteProjection {
    long getDepartmentKey();

    String getDepartmentName();
}
