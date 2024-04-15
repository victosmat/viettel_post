import { Component, OnInit } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { EmployeeService } from '../service/employee/employee.service';
import { AuthService } from '../service/auth/auth.service';
import { MatDialog } from '@angular/material/dialog';
import { LogoutDialogComponent } from './logout-dialog/logout-dialog.component';
import { MatSnackBar } from '@angular/material/snack-bar';

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
  image: any;
  employeeId = Number(this.cookieService.get('TimesheetAppEmployeeId'));

  constructor(
    private employeeService: EmployeeService,
    private cookieService: CookieService,
    public authService: AuthService,
    private snackBar: MatSnackBar,
    public dialog: MatDialog) { }

  ngOnInit(): void {
    console.log(this.cookieService.get("TimesheetAppToken"));
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
    this.dialog.open(LogoutDialogComponent, {
      width: '500px',
    }).afterClosed().subscribe(() => {
      this.snackBar.open('Logout successfully', 'OK', {
        duration: 2000,
      });
    });
  }
}
