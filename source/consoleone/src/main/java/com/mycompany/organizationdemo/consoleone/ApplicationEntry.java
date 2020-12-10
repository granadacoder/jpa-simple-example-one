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
        showDepartments("seed data", depts);

        final long deptKey999 = 999;

        DepartmentDto newDept = new DepartmentDto();
        newDept.setDepartmentKey(deptKey999);
        newDept.setDepartmentName("Department 999");

        dm.saveSingle(newDept);

        depts = dm.getAll();
        showDepartments("after saveSingle", depts);

        int rowCount = dm.deleteByKey(deptKey999);
        depts = dm.getAll();
        showDepartments("after deleteByKey", depts);
    }

    private static void showDepartments(String label, Collection<DepartmentDto> depts) {
        System.out.println(label);
        if (null != depts) {
            for (DepartmentDto currentDto : depts) {
                System.out.println(String.format("Key : %1s , DepartmentName : %2s", currentDto.getDepartmentKey(), currentDto.getDepartmentName()));
            }
        }
    }

}
