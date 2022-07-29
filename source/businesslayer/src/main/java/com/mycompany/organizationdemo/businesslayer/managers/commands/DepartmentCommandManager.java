package com.mycompany.organizationdemo.businesslayer.managers.commands;

import com.mycompany.organizationdemo.businesslayer.constants.BusinessLogicToHttpTensionConstants;
import com.mycompany.organizationdemo.businesslayer.managers.commands.interfaces.IDepartmentCommandManager;
import com.mycompany.organizationdemo.businesslayer.validators.DepartmentValidator;
import com.mycompany.organizationdemo.domain.dtos.DepartmentDto;
import com.mycompany.organizationdemo.domaindatalayer.interfaces.commands.IDepartmentCommandRepository;
import com.mycompany.organizationdemo.exceptions.BusinessLayerFinalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public final class DepartmentCommandManager implements IDepartmentCommandManager {

    public static final String ERROR_MSG_LOGGER_IS_NULL = "Logger is null";

    public static final String ERROR_MSG_I_DEPARTMENT_COMMAND_REPOSITORY_IS_NULL = "IDepartmentCommandRepository is null";

    public static final String ERROR_MSG_DEPARTMENT_INPUT_IS_INVALID = "Department input is invalid.";

    public static final String ERROR_MSG_DEPARTMENT_DOES_NOT_EXIST = "Department does not exist. (DepartmentKey=\"%1$s\")";

    public static final String LOG_MSG_METHOD_DELETE_BY_KEY_CALLED_DEPT_NAME = "Method deleteByKey called. (deptName=\"%1$s\")";

    private final Logger logger;

    private final IDepartmentCommandRepository deptCommandRepo;

    /* The Inject annotation marks which constructor to use for IoC when there are multiple constructors */
    @Inject
    public DepartmentCommandManager(IDepartmentCommandRepository deptCommandRepo) {
        this(LoggerFactory.getLogger(DepartmentCommandManager.class), deptCommandRepo);
    }

    public DepartmentCommandManager(Logger lgr, IDepartmentCommandRepository deptCommandRepo) {
        if (null == lgr) {
            throw new IllegalArgumentException(ERROR_MSG_LOGGER_IS_NULL);
        }

        if (null == deptCommandRepo) {
            throw new IllegalArgumentException(ERROR_MSG_I_DEPARTMENT_COMMAND_REPOSITORY_IS_NULL);
        }

        this.logger = lgr;
        this.deptCommandRepo = deptCommandRepo;
    }

    @Override
    public DepartmentDto saveSingle(DepartmentDto item) {
        try {
            new DepartmentValidator().validateSingle(item);
        } catch (IllegalArgumentException illex) {
            throw new BusinessLayerFinalException(illex.getMessage(), BusinessLogicToHttpTensionConstants.HTTP_STATUS_400_BAD_REQUEST, true, ERROR_MSG_DEPARTMENT_INPUT_IS_INVALID);
        }
        DepartmentDto returnItem = this.deptCommandRepo.save(item);
        return returnItem;
    }

    @Override
    public long deleteByKey(long key) {
        this.logger.info(String.format(LOG_MSG_METHOD_DELETE_BY_KEY_CALLED_DEPT_NAME, key));
        long returnValue = this.deptCommandRepo.deleteByKey(key);
        if (returnValue == 0) {
            throw new BusinessLayerFinalException("", BusinessLogicToHttpTensionConstants.HTTP_STATUS_404_NOT_FOUND, false, String.format(ERROR_MSG_DEPARTMENT_DOES_NOT_EXIST, key));
        }
        return returnValue;
    }
}
