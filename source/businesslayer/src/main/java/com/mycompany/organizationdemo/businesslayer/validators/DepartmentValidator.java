package com.mycompany.organizationdemo.businesslayer.validators;

import com.mycompany.organizationdemo.businesslayer.validators.constants.ValidationMsgConstant;
import com.mycompany.organizationdemo.domain.dtos.DepartmentDto;
import com.mycompany.organizationdemo.domaindatalayer.interfaces.constants.stringlengths.DepartmentValidationStringLengthConstants;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public final class DepartmentValidator extends ValidatorBase<DepartmentDto> {
    public static final String MESSAGE_ITEM_TYPE = "Department";

    public static final String MESSAGE_COLLECTION_TYPE = "Collection<Department>";

    /* PropertyNames */
    public static final String MESSAGE_DEPARTMENT_PROPERTY_NAME_DEPARTMENT_KEY = "Department.DepartmentKey";

    public static final String MESSAGE_DEPARTMENT_PROPERTY_NAME_DEPARTMENT_NAME = "Department.DepartmentName";

    private static void validateStringLengths(DepartmentDto item, Collection<String> errors) {
        if (!StringUtils.isBlank(item.getDepartmentName()) && item.getDepartmentName().length() > DepartmentValidationStringLengthConstants.DEPARTMENT_NAME_MAX_LENGTH) {
            errors.add(
                    String.format(
                            ValidationMsgConstant.LENGTH_TOO_MANY,
                            MESSAGE_DEPARTMENT_PROPERTY_NAME_DEPARTMENT_NAME,
                            item.getDepartmentName(),
                            item.getDepartmentName().length(),
                            DepartmentValidationStringLengthConstants.DEPARTMENT_NAME_MAX_LENGTH));
        }
    }

    private static void validateNullables(DepartmentDto item, Collection<String> errors) {

        if (StringUtils.isBlank(item.getDepartmentName())) {
            errors.add(
                    String.format(
                            ValidationMsgConstant.PROPERTY_VALUE_NULL_OR_EMPTY,
                            MESSAGE_DEPARTMENT_PROPERTY_NAME_DEPARTMENT_NAME));
        }
    }

    private static void validateKeys(DepartmentDto item, Collection<String> errors) {

        if (item.getDepartmentKey() < 0) {
            errors.add(
                    String.format(
                            ValidationMsgConstant.PROPERTY_VALUE_LESS_THAN_ZERO,
                            MESSAGE_DEPARTMENT_PROPERTY_NAME_DEPARTMENT_KEY));
        }
    }

    private static void validateEmployeeToDepartmentKeys(DepartmentDto item, Collection<String> errors) {

        if (null != item.getEmployees() && !item.getEmployees().isEmpty()) {

            /* ensure that if any child emps are specified, that the "Employee.ParentDepartmentKey" matches the Departement.DepartmentKey */

            Collection<Long> distinctParentDeptKeys = item.getEmployees().stream()
                    //operators to remove duplicates based on single property
                    .collect(Collectors.groupingBy(p -> p.getParentDepartmentKey()))
                    .values()
                    .stream()
                    //cut short the groups to size of 1
                    .flatMap(group -> group.stream().limit(1))
                    /* map to only the germane property */
                    .map(emp -> emp.getParentDepartmentKey())
                    /* filter out the "ok" value */
                    .filter(dk -> dk != item.getDepartmentKey())
                    //collect distinct entries as a collection-list
                    .collect(Collectors.toList());

            if (distinctParentDeptKeys.size() > 0
                    && distinctParentDeptKeys.stream().anyMatch(dk -> dk != item.getDepartmentKey())) {

                String csv = StringUtils.join(distinctParentDeptKeys, ',');

                errors.add(
                        String.format(
                                ValidationMsgConstant.PARENT_CHILD_SCALAR_MISMATCH,
                                MESSAGE_ITEM_TYPE,
                                item.getDepartmentKey(),
                                MESSAGE_DEPARTMENT_PROPERTY_NAME_DEPARTMENT_KEY,
                                item.getDepartmentKey(),
                                EmployeeValidator.MESSAGE_ITEM_TYPE,
                                EmployeeValidator.MESSAGE_EMPLOYEE_PROPERTY_NAME_PARENT_DEPARTMENT_KEY,
                                csv
                        ));
            }
        }
    }

    @Override
    public void validateSingle(DepartmentDto item) {
        if (null == item) {
            throw new NullPointerException(
                    String.format(ValidationMsgConstant.IS_NULL_ITEM, MESSAGE_ITEM_TYPE));
        }

        Collection<DepartmentDto> singleItemCollection = Collections.singleton(item);
        this.validateCollection(singleItemCollection);
    }

    @Override
    public void validateCollection(Collection<DepartmentDto> items) {
        Collection<String> errors = new ArrayList<>();
        if (null == items) {
            errors.add(String.format(ValidationMsgConstant.COLLECTION_IS_NULL_COLLECTION_NAME, MESSAGE_COLLECTION_TYPE));
        } else {
            for (DepartmentDto item : items) {
                if (null == item) {
                    errors.add(String.format(ValidationMsgConstant.IS_NULL_ITEM, MESSAGE_ITEM_TYPE));
                } else {
                    /* Nullables */
                    validateNullables(item, errors);

                    /* String Length */
                    validateStringLengths(item, errors);

                    //validateKeys(item, errors);

                    validateEmployeeToDepartmentKeys(item, errors);
                }
            }
        }

        if (!errors.isEmpty()) {
            String flattenedErrors = StringUtils.join(errors, ',');
            throw new IllegalArgumentException(flattenedErrors);
        }
    }
}
