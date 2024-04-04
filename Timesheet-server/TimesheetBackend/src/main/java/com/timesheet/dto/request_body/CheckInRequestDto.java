package com.timesheet.dto.request_body;

public class CheckInRequestDto {
    private Integer month;
    private Integer year;
    private Integer employeeId;

    public CheckInRequestDto() {
    }

    public CheckInRequestDto(Integer month, Integer year, Integer employeeId) {
        this.month = month;
        this.year = year;
        this.employeeId = employeeId;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }


}
