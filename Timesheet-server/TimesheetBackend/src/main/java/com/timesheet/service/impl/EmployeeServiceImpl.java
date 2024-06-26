package com.timesheet.service.impl;

import com.manage.employeemanagementmodel.entity.*;
import com.manage.employeemanagementmodel.exception.EmployeeNotFoundException;
import com.timesheet.dto.employee.BuddyDto;
import com.timesheet.dto.employee.EmployeeFormDto;
import com.timesheet.dto.employee.EmployeeSaveDto;
import com.timesheet.dto.employee.IEmployeeProfileDto;
import com.timesheet.dto.StaffViewDto;
import com.timesheet.dto.mapper.employee.EmployeeFormMapper;
import com.timesheet.repository.DepartmentRepository;
import com.timesheet.repository.EmployeeRepository;
import com.timesheet.repository.JobDepartmentRepository;
import com.timesheet.repository.RoleRepository;
import com.timesheet.service.EmployeeService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeFormMapper employeeFormMapper;
    private final DepartmentRepository departmentRepository;
    private final JobDepartmentRepository jobDepartmentRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeFormMapper employeeFormMapper, DepartmentRepository departmentRepository, JobDepartmentRepository jobDepartmentRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.employeeFormMapper = employeeFormMapper;
        this.departmentRepository = departmentRepository;
        this.jobDepartmentRepository = jobDepartmentRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Integer getEmployeeId(String username) {
        return employeeRepository.getEmployeeId(username);
    }

    @Override
    public Page<StaffViewDto> getStaffListByNativeQuery(Integer buddyId, Integer pageNumber, Integer pageSize, String nameSearch, String sortField, String sortOrder) {
        Sort sort = switch (sortField) {
            case "jobDepartment" -> Sort.by("jobDepartment.name");
            case "department" -> Sort.by("department.name");
            case "fullName" -> Sort.by("first_name").and(Sort.by("last_name"));
            default -> Sort.by("id");
        };

        sort = (sortOrder.equals("asc")) ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return employeeRepository.getAllStaffByBuddyId(buddyId, pageable);
    }

    @Override
    public Page<IEmployeeProfileDto> listByPageWithIsEnable(Integer pageNum, Integer pageSize, String sortField, String sortDir, String keyword, Boolean isActive, String departmentLevelStatus, String jobDepartment, String department) {
        Sort sort = Sort.by(sortField);
        sort = (sortDir.equals("asc")) ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
        return employeeRepository.findAllWithIsActiveAndLevel(keyword, pageable, isActive, departmentLevelStatus, jobDepartment, department);
    }

    @Override
    public Page<IEmployeeProfileDto> listByPage(Integer pageNum, Integer pageSize, String sortField, String sortDir, String keyword, String departmentLevelStatus, String jobDepartment, String department) {
        Sort sort = Sort.by(sortField);
        sort = (sortDir.equals("asc")) ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
        return employeeRepository.findAllWithLevel(keyword, pageable, departmentLevelStatus, jobDepartment, department);
    }

    @Override
    public Employee getEmployeeByEmployeeId(Integer employeeId) throws EmployeeNotFoundException {
        return employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + employeeId));
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public EmployeeFormDto findEmployeeFormById(Integer id) throws EmployeeNotFoundException {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
        return employeeFormMapper.employeeToEmployeeFormDto(employee);
    }

    @Override
    @Transactional
    public Boolean save(EmployeeSaveDto employeeSaveDto) throws EmployeeNotFoundException {
        if (Objects.isNull(employeeSaveDto.getId())) {
            Bank bank = new Bank(null, employeeSaveDto.getBankName(), employeeSaveDto.getBankNumber());
            Employee buddy = null;
            if (employeeSaveDto.getBuddyId() != 0)
                buddy = employeeRepository.findById(employeeSaveDto.getBuddyId()).orElseThrow();
            Department department = departmentRepository.findById(employeeSaveDto.getDepartmentId()).orElseThrow();
            Role role = roleRepository.findById(employeeSaveDto.getJobDepartmentId()).orElseThrow();
            JobDepartment jobDepartment = jobDepartmentRepository.findByJobDepartment(role.getName()).orElseThrow();
            String password = employeeSaveDto.getPassword();
            Account account = new Account(employeeSaveDto.getUsername(), passwordEncoder.encode(employeeSaveDto.getPassword()), List.of(role));
            Employee employee = new Employee(employeeSaveDto.getFirstName(), employeeSaveDto.getLastName(), employeeSaveDto.getGender(), employeeSaveDto.getBirthDate(), employeeSaveDto.getHiringDate(), employeeSaveDto.getEmail(), true, buddy, department, account, jobDepartment, employeeSaveDto.getLevel(), bank);
            if (employeeSaveDto.getId() != null) employee.setId(employeeSaveDto.getId());

            try {
                employeeRepository.save(employee);
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            Employee employee = employeeRepository.findById(employeeSaveDto.getId()).orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + employeeSaveDto.getId()));
            if (Objects.nonNull(employeeSaveDto.getFirstName())) employee.setFirstName(employeeSaveDto.getFirstName());
            if (Objects.nonNull(employeeSaveDto.getLastName())) employee.setLastName(employeeSaveDto.getLastName());
            if (Objects.nonNull(employeeSaveDto.getBirthDate())) employee.setBirthDate(employeeSaveDto.getBirthDate());
            if (Objects.nonNull(employeeSaveDto.getEmail())) employee.setEmail(employeeSaveDto.getEmail());
            Bank bank = employee.getBank();
            if (Objects.nonNull(employeeSaveDto.getBankName())) bank.setName(employeeSaveDto.getBankName());
            if (Objects.nonNull(employeeSaveDto.getBankNumber())) bank.setNumber(employeeSaveDto.getBankNumber());
            employee.setBank(bank);
            if (Objects.nonNull(employeeSaveDto.getGender())) employee.setGender(employeeSaveDto.getGender());
            if (Objects.nonNull(employeeSaveDto.getBuddyId()) && employeeSaveDto.getBuddyId() != 0)
                employee.setBuddy(employeeRepository.findById(employeeSaveDto.getBuddyId()).orElseThrow(() -> new EmployeeNotFoundException("Buddy not found with id: " + employeeSaveDto.getBuddyId())));
            if (Objects.nonNull(employeeSaveDto.getDepartmentId()))
                employee.setDepartment(departmentRepository.findById(employeeSaveDto.getDepartmentId()).orElseThrow(() -> new EmployeeNotFoundException("Department not found with id: " + employeeSaveDto.getDepartmentId())));
            if (Objects.nonNull(employeeSaveDto.getHiringDate()))
                employee.setHiringDate(employeeSaveDto.getHiringDate());
            if (Objects.nonNull(employeeSaveDto.getLevel()))
                employee.setEmployeeLevelStatus(employeeSaveDto.getLevel());

            try {
                employeeRepository.save(employee);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }

    @Override
    public void detete(Integer employeeId) throws EmployeeNotFoundException {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + employeeId));
        if (employee.getBuddy() != null) {
            Set<Employee> employees = getSubEmployee(employee.getBuddy().getId());
            System.out.println(employees);
            employees.remove(employee);
            employee.setBuddy(null);
        }
        for (Employee associatedEmployee : getSubEmployee(employeeId)) {
            associatedEmployee.setBuddy(null);
            employeeRepository.save(associatedEmployee);
        }
        employeeRepository.save(employee);

        employeeRepository.delete(employee);
    }

    private Set<Employee> getSubEmployee(Integer parentEmployeeId) {
        return employeeRepository.findSubEmployeeByParentEmployee(parentEmployeeId);
    }

    @Override
    public List<Employee> findByIdNot(Integer id) {
        return employeeRepository.findByIdNot(id);
    }

    @Override
    public boolean checkEmailUnique(Integer id, String email) {
        Employee employee = employeeRepository.getEmployeeByEmail(email);
        if (employee == null) return true;
        boolean isCreatingNew = (id == null);
        if (isCreatingNew) {
            return false;
        } else {
            return Objects.equals(employee.getId(), id);
        }
    }

    @Override
    public Employee getEmployeeById(Integer id) {
        return employeeRepository.findById(id).get();
    }

    @Override
    public List<String> getRolesById(Integer id) {
        return employeeRepository.getRolesById(id);
    }

    @Override
    public List<BuddyDto> getBuddys() {
        return employeeRepository.getBuddys();
    }
}
