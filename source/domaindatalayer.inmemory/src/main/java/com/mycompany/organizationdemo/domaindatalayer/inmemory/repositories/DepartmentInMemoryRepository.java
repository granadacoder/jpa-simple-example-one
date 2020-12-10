package com.mycompany.organizationdemo.domaindatalayer.inmemory.repositories;

import com.mycompany.organizationdemo.domain.dtos.DepartmentDto;
import com.mycompany.organizationdemo.domaindatalayer.interfaces.IDepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public final class DepartmentInMemoryRepository implements IDepartmentRepository {

    private static Collection<DepartmentDto> allDepartments = new ArrayList<>();

    private final Logger logger;

    /* The Inject annotation marks which constructor to use for IoC when there are multiple constructors */
    @Inject
    public DepartmentInMemoryRepository() {
        this(LoggerFactory.getLogger(DepartmentInMemoryRepository.class));
    }

    public DepartmentInMemoryRepository(Logger lgr) {
        if (null == lgr) {
            throw new IllegalArgumentException("Logger is null");
        }
        this.logger = lgr;
    }

    @Override
    public Collection<DepartmentDto> findEmAll() {
        Collection<DepartmentDto> returnItems = this.getInMemoryDepartments();
        return returnItems;
    }

    @Override
    public Collection<DepartmentDto> findEmAllByMyCoolProjectionExample() {
        Collection<DepartmentDto> returnItems = this.getInMemoryDepartments();
        return returnItems;
    }

    @Override
    public Optional<DepartmentDto> findById(final long key) {
        Collection<DepartmentDto> items = this.getInMemoryDepartments();
        return items.stream().findFirst();
    }

    @Override
    public Optional<DepartmentDto> findByDepartmentName(final String departmentName) {
        Collection<DepartmentDto> items = this.getInMemoryDepartments();
        return items.stream().findFirst();
    }

    @Override
    public Collection<DepartmentDto> findByCreateOffsetDateTimeBefore(final OffsetDateTime zdt) {
        Collection<DepartmentDto> returnItems = this.getInMemoryDepartments();
        return returnItems;
    }

    @Override
    public Collection<DepartmentDto> findBySurrogateKeyIn(final Set<Long> departmentKeys) {
        Collection<DepartmentDto> returnItems = this.getInMemoryDepartments();
        return returnItems;
    }

    @Override
    public DepartmentDto save(final DepartmentDto item) {
        if (null != allDepartments) {
            allDepartments.add(item);
        }

        return item;
    }

    @Override
    public int deleteByKey(final long departmentKey) {
        int returnValue = 0;
        if (null != allDepartments) {
            Optional<DepartmentDto> optionalFoundDept = allDepartments.stream().filter(d -> d.getDepartmentKey() == departmentKey).findFirst();
            if (optionalFoundDept.isPresent()) {
                returnValue = 1; /* record count */
                allDepartments.remove(optionalFoundDept.get());
            }
        }
        return returnValue;
    }

    @Override
    public Collection<DepartmentDto> findOrphanedDepartments() {
        Collection<DepartmentDto> returnItems = this.getInMemoryDepartments();
        return returnItems;
    }

    private Collection<DepartmentDto> getInMemoryDepartments() {

        if (null == allDepartments || allDepartments.isEmpty()) {
            Collection<DepartmentDto> lazyCollection = new ArrayList<>();

            final int deptKey101 = 101;
            DepartmentDto dto1 = new DepartmentDto();
            dto1.setDepartmentKey(deptKey101);
            dto1.setDepartmentName("Department One");
            lazyCollection.add(dto1);

            final int deptKey102 = 102;
            DepartmentDto dto2 = new DepartmentDto();
            dto2.setDepartmentKey(deptKey102);
            dto2.setDepartmentName("Department Two");
            lazyCollection.add(dto2);

            allDepartments = lazyCollection;
        }

        return allDepartments;
    }
}
