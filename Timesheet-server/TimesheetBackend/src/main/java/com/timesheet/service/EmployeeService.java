package com.timesheet.service;

import com.manage.employeemanagementmodel.entity.Employee;
import com.manage.employeemanagementmodel.exception.EmployeeNotFoundException;
import com.timesheet.dto.employee.BuddyDto;
import com.timesheet.dto.employee.EmployeeFormDto;
import com.timesheet.dto.employee.EmployeeSaveDto;
import com.timesheet.dto.employee.IEmployeeProfileDto;
import com.timesheet.dto.StaffViewDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {


    Integer getEmployeeId(String username);

    Page<StaffViewDto> getStaffListByNativeQuery(Integer buddyId, Integer pageNumber,
                                                 Integer pageSize, String nameSearch, String sortField, String sortOrder);

    Page<IEmployeeProfileDto> listByPageWithIsEnable(Integer pageNum, Integer pageSize, String sortField, String sortDir, String keyword, Boolean isActive, String departmentLevelStatus, String jobDepartment, String department);


    List<Employee> findAll();

    EmployeeFormDto findEmployeeFormById(Integer id) throws EmployeeNotFoundException;

    Boolean save(EmployeeSaveDto employeeSaveDto) throws EmployeeNotFoundException;

    void detete(Integer employeeId) throws EmployeeNotFoundException;

    List<Employee> findByIdNot(Integer id);

    boolean checkEmailUnique(Integer id, String email);

    Employee getEmployeeById(Integer id);

    List<String> getRolesById(Integer id);

    List<BuddyDto> getBuddys();

    Page<IEmployeeProfileDto> listByPage(Integer pageNum, Integer pageSize, String sortField, String sortDir, String keyword, String departmentLevelStatus, String jobDepartment, String department);

    Employee getEmployeeByEmployeeId(Integer employeeId) throws EmployeeNotFoundException;
}
