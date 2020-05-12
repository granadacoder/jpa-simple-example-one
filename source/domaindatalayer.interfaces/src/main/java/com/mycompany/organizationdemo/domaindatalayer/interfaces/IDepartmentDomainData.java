package com.mycompany.organizationdemo.domaindatalayer.interfaces;

import com.mycompany.organizationdemo.domain.entities.Department;
import org.springframework.data.repository.CrudRepository;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Collection;

public interface IDepartmentDomainData extends CrudRepository<Department, Long> {

    Collection<Department> getByCreateBeforeDate(OffsetDateTime zdt);
//
//    Department GetSingle(long key);
}


