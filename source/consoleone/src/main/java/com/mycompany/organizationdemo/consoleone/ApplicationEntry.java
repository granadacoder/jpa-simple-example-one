package com.mycompany.organizationdemo.consoleone;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mycompany.organizationdemo.businesslayer.managers.interfaces.IDepartmentManager;
import com.mycompany.organizationdemo.domain.dtos.DepartmentDto;

import java.util.Collection;

public class ApplicationEntry {

    public static void main(String[] args) {
        runGuice();
    }

    private static void runGuice() {
        GuiceCompositionRoot pm = new GuiceCompositionRoot();
        Injector injector = Guice.createInjector(pm);

        IDepartmentManager dm = injector.getInstance(IDepartmentManager.class);
        Collection<DepartmentDto> depts = dm.getAll();

        if (null != depts) {
            for (DepartmentDto currentDto : depts) {
                System.out.println(String.format("DepartmentName : %1s", currentDto.getDepartmentName()));
            }
        }
    }

}
