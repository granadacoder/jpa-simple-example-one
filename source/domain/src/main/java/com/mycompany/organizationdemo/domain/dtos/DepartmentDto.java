package com.mycompany.organizationdemo.domain.dtos;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@SuppressWarnings("checkstyle:DesignForExtension") /* "mapper" does not like final objects */
public class DepartmentDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private long departmentKey;

    private String departmentName;

    private OffsetDateTime createOffsetDateTime;

    private Set<EmployeeDto> employees = new LinkedHashSet<>();

    public long getDepartmentKey() {
        return departmentKey;
    }

    public void setDepartmentKey(long departmentKey) {
        this.departmentKey = departmentKey;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public OffsetDateTime getCreateOffsetDateTime() {
        return createOffsetDateTime;
    }

    public void setCreateOffsetDateTime(OffsetDateTime createOffsetDateTime) {
        this.createOffsetDateTime = createOffsetDateTime;
    }

    public Set<EmployeeDto> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<EmployeeDto> employees) {
        this.employees = employees;
    }
}
