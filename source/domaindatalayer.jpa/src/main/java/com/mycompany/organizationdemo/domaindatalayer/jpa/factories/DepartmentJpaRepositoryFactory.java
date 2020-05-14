package com.mycompany.organizationdemo.domaindatalayer.jpa.factories;

import com.mycompany.organizationdemo.domaindatalayer.jpa.repositories.DepartmentJpaRepository;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class DepartmentJpaRepositoryFactory {

    @Inject
    private DepartmentJpaRepository autoinjectedDepartmentJpaRepositoryWorkaround;

    /* this is a workaround for using explicit xml IOC with spring-data.
    * because it is "interface DepartmentJpaRepository" (not a class), you cannot do traditional xml IoC definitions :(
    * this is a workaround.  this factory should NEVER be used by the code base, only by spring-di  */
    public DepartmentJpaRepository getInstanceDepartmentJpaRepository() {
        return this.autoinjectedDepartmentJpaRepositoryWorkaround;
    }

}