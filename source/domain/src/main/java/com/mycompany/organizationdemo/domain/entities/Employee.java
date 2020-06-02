package com.mycompany.organizationdemo.domain.entities;

import com.mycompany.organizationdemo.domain.constants.OrmConstants;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.OffsetDateTime;

/* #vsnote. unfortunately, there is no "Fluent Mapping"'s in Java.  It is xml based (not shown) or @Annotation based (below)  :(  */

@Entity
@Table(name = "EmployeeTable")
public class Employee implements Serializable {

    @Id
    @Column(name = "EmployeeKey", unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long employeeKey;

    @Column(name = "Ssn")
    private String ssn;

    @Column(name = "LastName")
    private String lastName;

    @Column(name = "FirstName")
    private String firstName;

    @Column(name = "CreateOffsetDateTime", columnDefinition = OrmConstants.OffsetDateTimeColumnDefinition)
    private OffsetDateTime createOffsetDateTime;

    //region Navigation

    ////@JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Department.class)//, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "DepartmentForeignKey")
    ////////@JsonManagedReference /* deal with cyclic references.  see https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion */
    private Department parentDepartment;
    //endregion


    public long getEmployeeKey() {
        return employeeKey;
    }

    public void setEmployeeKey(long departmentKey) {
        this.employeeKey = departmentKey;
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

    //region Navigation

    public Department getParentDepartment() {
        return parentDepartment;
    }

    public void setParentDepartment(Department parentDepartment) {
        this.parentDepartment = parentDepartment;
    }

    //endregion


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        return new EqualsBuilder()
                .append(employeeKey, employee.employeeKey)
                .append(ssn, employee.ssn)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(employeeKey)
                .append(ssn)
                .toHashCode();
    }
}
