import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { EmployeeService } from '../service/employee/employee.service';
import { AuthService } from '../service/auth/auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {
  panelOpenState = false;
  fullname!: string;
  email!: string;
  roles: string[] = [];
  image : any;
  employeeId = Number(this.cookieService.get('TimesheetAppEmployeeId'));

  constructor(
    private employeeService: EmployeeService,
    private cookieService: CookieService,
    public authService: AuthService,
    private router: Router) { }

  ngOnInit(): void {

    this.employeeService.getProfile(Number(this.employeeId)).subscribe({
      next: (response: any) => {
        this.fullname = response.lastName + " " + response.firstName;
        this.email = response.email;
      },
      error: (error: any) => {
        console.log(error.status);
      },
      complete: () => { },
    });
  }

  getTimesheetToken() {
    return this.cookieService.get('TimesheetAppToken');
  }

  logout() {
    this.cookieService.delete('TimesheetAppToken');
    this.cookieService.delete('TimesheetAppRefreshToken');
    this.cookieService.delete('TimesheetAppUsername');
    this.cookieService.delete('TimesheetAppEmployeeId');
    this.router.navigate(['login']);
  }
}
