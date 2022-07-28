package com.mycompany.organizationdemo.domaindatalayer.jpa.entities;

import com.mycompany.organizationdemo.domaindatalayer.jpa.constants.OrmConstants;

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
//@NamedEntityGraphs({
//        @NamedEntityGraph(name = "departmentJustScalarsEntityGraphName", attributeNodes = {
//                @NamedAttributeNode("departmentKey"),
//                @NamedAttributeNode("departmentName")})
//})
@Table(name = "DepartmentTable")
@SuppressWarnings("checkstyle:DesignForExtension") /* jpa-entities cannot be final */
public class DepartmentJpaEntity implements Serializable {

    public static final int HASH_CODE_INITIAL_ODD_NUMBER = 17;

    public static final int HASH_CODE_MULTIPLIER_ODD_NUMBER = 37;

    @Id
    @Column(name = "DepartmentKey", unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long departmentKey;

    @Column(name = "DepartmentName", unique = true)
    private String departmentName;

    @Column(name = "CreateOffsetDateTime", columnDefinition = OrmConstants.OFFSET_DATE_TIME_COLUMN_DEFINITION)
    private OffsetDateTime createOffsetDateTime;

    ////@JsonBackReference
    @OneToMany(
            mappedBy = "parentDepartmentJpaEntity",
            cascade = CascadeType.PERSIST,
            orphanRemoval = true,
            fetch = FetchType.LAZY /* Lazy or Eager here */
    )
    ////////@JsonBackReference /* deal with cyclic references.  see https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion */
    private Set<EmployeeJpaEntity> employeeJpaEntities = new LinkedHashSet<>();

    public DepartmentJpaEntity() {
    }

    //region Navigation

    public DepartmentJpaEntity(long departmentKey, String departmentName) {
        this.departmentKey = departmentKey;
        this.departmentName = departmentName;
    }
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
    public Set<EmployeeJpaEntity> getEmployeeJpaEntities() {
        return employeeJpaEntities;
    }

    public void setEmployeeJpaEntities(Set<EmployeeJpaEntity> employeeJpaEntities) {
        this.employeeJpaEntities = employeeJpaEntities;
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

        DepartmentJpaEntity that = (DepartmentJpaEntity) o;

        return new org.apache.commons.lang3.builder.EqualsBuilder()
                .append(departmentKey, that.departmentKey)
                .append(departmentName, that.departmentName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new org.apache.commons.lang3.builder.HashCodeBuilder(HASH_CODE_INITIAL_ODD_NUMBER, HASH_CODE_MULTIPLIER_ODD_NUMBER)
                .append(departmentKey)
                .append(departmentName)
                .toHashCode();
    }
}
