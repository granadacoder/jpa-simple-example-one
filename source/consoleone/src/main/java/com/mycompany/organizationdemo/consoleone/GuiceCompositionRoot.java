package com.mycompany.organizationdemo.consoleone;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.mycompany.organizationdemo.businesslayer.managers.DepartmentManager;
import com.mycompany.organizationdemo.businesslayer.managers.interfaces.IDepartmentManager;
import com.mycompany.organizationdemo.domaindatalayer.inmemory.repositories.DepartmentInMemoryRepository;
import com.mycompany.organizationdemo.domaindatalayer.interfaces.IDepartmentRepository;

public final class GuiceCompositionRoot extends AbstractModule implements Module {

    public GuiceCompositionRoot() {
    }

    @Override
    protected void configure() {
        try {
            bind(IDepartmentManager.class).to(DepartmentManager.class);
            bind(IDepartmentRepository.class).to(DepartmentInMemoryRepository.class);
        } catch (Exception e) {
            addError(e);
        }
    }
}
