package com.mycompany.organizationdemo.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DepartmentTable")
public class Department {

    @Id
    @Column(name = "DepartmentKey", unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long departmentKey;

    @Column(name = "DepartmentName", unique = true)
    private String departmentName;

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

}
