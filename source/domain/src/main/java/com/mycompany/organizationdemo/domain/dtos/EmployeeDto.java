package com.mycompany.organizationdemo.domain.dtos;

import java.time.OffsetDateTime;

public class EmployeeDto {

    private long employeeKey;

    private String ssn;

    private String lastName;

    private String firstName;

    private OffsetDateTime createOffsetDateTime;

    private long parentDepartmentKey;

    public long getEmployeeKey() {
        return employeeKey;
    }

    public void setEmployeeKey(long employeeKey) {
        this.employeeKey = employeeKey;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public OffsetDateTime getCreateOffsetDateTime() {
        return createOffsetDateTime;
    }

    public void setCreateOffsetDateTime(OffsetDateTime createOffsetDateTime) {
        this.createOffsetDateTime = createOffsetDateTime;
    }

    public long getParentDepartmentKey() {
        return parentDepartmentKey;
    }

    public void setParentDepartmentKey(long parentDepartmentKey) {
        this.parentDepartmentKey = parentDepartmentKey;
    }
}
