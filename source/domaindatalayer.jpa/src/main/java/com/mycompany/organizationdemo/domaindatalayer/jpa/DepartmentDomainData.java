package com.mycompany.organizationdemo.domaindatalayer.jpa;

import com.mycompany.organizationdemo.domain.entities.Department;
import com.mycompany.organizationdemo.domaindatalayer.interfaces.IDepartmentDomainData;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;

public class DepartmentDomainData extends SimpleJpaRepository<Department, Long> implements IDepartmentDomainData {

    @PersistenceContext
    protected /*final*/ EntityManager entityManager;

    public DepartmentDomainData(EntityManager em) {
        super(Department.class, em);
        this.entityManager = em;
    }

    @Override
    public Collection<Department> getByCreateBeforeDate(OffsetDateTime zdt) {
        TypedQuery<Department> query = entityManager.createQuery("SELECT t FROM " + Department.class.getSimpleName() + " t where t.createOffsetDateTime < :value1", Department.class)
                .setParameter("value1", zdt);
        List<Department> returnItems = query.getResultList();
        return returnItems;
    }
}
