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
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int deleteByKey(final long departmentKey) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Collection<DepartmentDto> findOrphanedDepartments() {
        Collection<DepartmentDto> returnItems = this.getInMemoryDepartments();
        return returnItems;
    }

    private Collection<DepartmentDto> getInMemoryDepartments() {
        Collection<DepartmentDto> returnItems = new ArrayList<>();

        DepartmentDto dto1 = new DepartmentDto();
        dto1.setDepartmentName("Department One");
        returnItems.add(dto1);

        DepartmentDto dto2 = new DepartmentDto();
        dto2.setDepartmentName("Department Two");
        returnItems.add(dto2);

        return returnItems;

    }
}
