import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
@Component({
  selector: 'app-logout-dialog',
  templateUrl: './logout-dialog.component.html',
  styleUrls: ['./logout-dialog.component.scss']
})
export class LogoutDialogComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<LogoutDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private cookieService: CookieService,
    private router: Router
  ) {
    dialogRef.disableClose = true;
  }

  isLoading: boolean = false;

  ngOnInit(): void {
    console.log(this.data);
  }

  logout() {
    this.isLoading = true;
    console.log(this.cookieService);
    this.cookieService.delete('TimesheetAppToken', '/');
    this.cookieService.delete('TimesheetAppToken');
    this.cookieService.delete('TimesheetAppRefreshToken', '/');
    this.cookieService.delete('TimesheetAppUsername', '/');
    this.cookieService.delete('TimesheetAppEmployeeId', '/');
    console.log(this.cookieService.get("TimesheetAppToken"));
    this.router.navigate(['login']);
    this.dialogRef.close();
    this.isLoading = false;
  }

  onNoClick() {
    this.dialogRef.close();
  }
}
