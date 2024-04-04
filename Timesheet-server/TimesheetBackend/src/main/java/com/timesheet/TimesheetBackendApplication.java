package com.timesheet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EntityScan({"com.manage.employeemanagementmodel.entity"})
@EnableScheduling
public class TimesheetBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimesheetBackendApplication.class, args);
    }
}
