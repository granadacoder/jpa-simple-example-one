package com.mycompany.organizationdemo.domaindatalayer.jpa.entities;

import com.mycompany.organizationdemo.domaindatalayer.interfaces.constants.stringlengths.EmployeeValidationStringLengthConstants;
import com.mycompany.organizationdemo.domaindatalayer.jpa.constants.OrmConstants;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.CascadeType;
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
@SuppressWarnings("checkstyle:DesignForExtension") /* jpa-entities cannot be final */
public class EmployeeJpaEntity implements Serializable {

    public static final int HASH_CODE_INITIAL_ODD_NUMBER = 17;

    public static final int HASH_CODE_MULTIPLIER_ODD_NUMBER = 37;

    @Id
    @Column(name = "EmployeeKey", unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long employeeKey;

    @Column(name = "Ssn", length = EmployeeValidationStringLengthConstants.EMPLOYEE_SSN_MAX_LENGTH)
    private String ssn;

    @Column(name = "LastName", length = EmployeeValidationStringLengthConstants.EMPLOYEE_LAST_NAME_MAX_LENGTH)
    private String lastName;

    @Column(name = "FirstName", length = EmployeeValidationStringLengthConstants.EMPLOYEE_FIRST_NAME_MAX_LENGTH)
    private String firstName;

    @Column(name = "CreateOffsetDateTime", columnDefinition = OrmConstants.OFFSET_DATE_TIME_COLUMN_DEFINITION)
    private OffsetDateTime createOffsetDateTime;

    //region Navigation

    ////@JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = DepartmentJpaEntity.class, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "DepartmentForeignKey")
    ////////@JsonManagedReference /* deal with cyclic references.  see https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion */
    private DepartmentJpaEntity parentDepartmentJpaEntity;
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

    public DepartmentJpaEntity getParentDepartmentJpaEntity() {
        return parentDepartmentJpaEntity;
    }

    public void setParentDepartmentJpaEntity(DepartmentJpaEntity parentDepartmentJpaEntity) {
        this.parentDepartmentJpaEntity = parentDepartmentJpaEntity;
    }

    //endregion

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmployeeJpaEntity employeeJpaEntity = (EmployeeJpaEntity) o;

        return new EqualsBuilder()
                .append(employeeKey, employeeJpaEntity.employeeKey)
                .append(ssn, employeeJpaEntity.ssn)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(HASH_CODE_INITIAL_ODD_NUMBER, HASH_CODE_MULTIPLIER_ODD_NUMBER)
                .append(employeeKey)
                .append(ssn)
                .toHashCode();
    }
}
