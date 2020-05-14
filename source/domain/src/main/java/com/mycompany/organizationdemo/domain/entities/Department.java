package com.mycompany.organizationdemo.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.OffsetDateTime;

/* #vsnote. unfortunately, there is no "Fluent Mapping"'s in Java.  It is xml based (not shown) or @Annotation based (below)  :(  */

@Entity
@Table(name = "DepartmentTable")
public class Department {

    @Id
    @Column(name = "DepartmentKey", unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long departmentKey;

    @Column(name = "DepartmentName", unique = true)
    private String departmentName;

    @Column(name = "CreateOffsetDateTime", columnDefinition = "TIMESTAMP WITH TIME ZONE" )
    private OffsetDateTime createOffsetDateTime;

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
}
