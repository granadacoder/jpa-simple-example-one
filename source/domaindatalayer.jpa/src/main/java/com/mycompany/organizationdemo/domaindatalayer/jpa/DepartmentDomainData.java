package com.mycompany.organizationdemo.domaindatalayer.jpa;

import com.mycompany.organizationdemo.domain.entities.Department;
import com.mycompany.organizationdemo.domaindatalayer.interfaces.IDepartmentDomainData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.ZonedDateTime;
import java.util.Collection;

//import org.springframework.data.repository.CrudRepository;
//
//public interface UserRepository extends CrudRepository<User, Long>{
//
//    User findByUsername(String username);
//
//}


public class DepartmentDomainData extends SimpleJpaRepository<Department, Long> implements IDepartmentDomainData {

    @PersistenceContext
    protected /*final*/ EntityManager entityManager;

    public DepartmentDomainData(EntityManager em) {
        super(Department.class, em);
        this.entityManager = em;
    }

    @Override
    public Collection<Department> GetByCreateDate(ZonedDateTime zdt) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

////public class DepartmentDomainData implements IDepartmentDomainData {
//    @Override
//    public Collection<Department> GetAll() {
//        throw new UnsupportedOperationException("Not implemented yet");
//    }
//
//    @Override
//    public Department GetSingle(long key) {
//
//        Department returnItem = new Department();
//        returnItem.setDepartmentKey(key);
//        return returnItem;
//    }
}
