package com.mycompany.organizationdemo.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.mycompany.organizationdemo.domain.constants.OrmConstants;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

/* #vsnote. unfortunately, there is no "Fluent Mapping"'s in Java.  It is xml based (not shown) or @Annotation based (below)  :(  */

@Entity
@Table(name = "DepartmentTable")
public class Department implements Serializable {

    @Id
    @Column(name = "DepartmentKey", unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long departmentKey;

    @Column(name = "DepartmentName", unique = true)
    private String departmentName;

    @Column(name = "CreateOffsetDateTime", columnDefinition = OrmConstants.OffsetDateTimeColumnDefinition)
    private OffsetDateTime createOffsetDateTime;

    //region Navigation

    ////@JsonBackReference
    @OneToMany(
            mappedBy = "department",
            cascade = CascadeType.ALL,
            orphanRemoval = false,
            fetch = FetchType.LAZY /* Lazy or Eager here */
    )
    ////////@JsonBackReference /* deal with cyclic references.  see https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion */
    private Set<Employee> employees = new LinkedHashSet<>();
    //endregion

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

    //region Navigation
    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
    //endregion


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Department that = (Department) o;

        return new org.apache.commons.lang3.builder.EqualsBuilder()
                .append(departmentKey, that.departmentKey)
                .append(departmentName, that.departmentName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new org.apache.commons.lang3.builder.HashCodeBuilder(17, 37)
                .append(departmentKey)
                .append(departmentName)
                .toHashCode();
    }
}
