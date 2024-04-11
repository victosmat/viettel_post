import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './material/material.module';
import { MyTimesheetComponent } from './my-timesheet/my-timesheet.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { CookieService } from 'ngx-cookie-service';
import { AuthInterceptor } from './auth/auth.interceptor';
import { ForbiddenComponent } from './forbidden/forbidden.component';
import { ConfirmDialogComponent } from './my-timesheet/confirm-dialog/confirm-dialog.component';
import { CommentDialogComponent } from './my-timesheet/comment-dialog/comment-dialog.component';
import { ManagementTimesheetComponent } from './management-timesheet/management-timesheet.component';
import { ComplainDialogComponent } from './my-timesheet/complain-dialog/complain-dialog.component';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { ManagementUserComponent } from './management-user/management-user.component';
import { ManagementProjectComponent } from './management-project/management-project.component';
import { ManagementDepartmentComponent } from './management-department/management-department.component';
import { ManagementJobDepartmentComponent } from './management-job-department/management-job-department.component';
import { EditUserDialogComponent } from './management-user/edit-user-dialog/edit-user-dialog.component';
import { EditRoleDialogComponent } from './management-user/edit-role-dialog/edit-role-dialog.component';
import { DeleteDialogComponent } from './management-user/delete-dialog/delete-dialog.component';
import { ActiveDialogComponent } from './management-user/active-dialog/active-dialog.component';
import { AddUserDialogComponent } from './management-user/add-user-dialog/add-user-dialog.component';
import { DeleteJobDepartmentDialogComponent } from './management-job-department/delete-job-department-dialog/delete-job-department-dialog.component';
import { SaveJobDepartmentDialogComponent } from './management-job-department/save-job-department-dialog/save-job-department-dialog.component';
import { SaveDepartmentComponent } from './management-department/save-department/save-department.component';
import { DeleteDepartmentComponent } from './management-department/delete-department/delete-department.component';
import { SaveProjectComponent } from './management-project/save-project/save-project.component';
import { ViewTaskComponent } from './management-project/view-task/view-task.component';
import { SaveTaskComponent } from './management-project/view-task/save-task/save-task.component';
import { UpdateStatusTaskComponent } from './management-project/view-task/update-status-task/update-status-task.component';
import { DeleteTaskComponent } from './management-project/view-task/delete-task/delete-task.component';
import { CommentNoteComponent } from './management-timesheet/comment-note/comment-note.component';
import { UpdatePasswordDialogComponent } from './user-profile/update-password-dialog/update-password-dialog.component';
import { ChangeInfoDialogComponent } from './user-profile/change-info-dialog/change-info-dialog.component';
import { UpdateStatusDialogComponent } from './management-timesheet/update-status-dialog/update-status-dialog.component';
import { DeleteProjectComponent } from './management-project/delete-project/delete-project.component';
@NgModule({
  declarations: [
    AppComponent,
    MyTimesheetComponent,
    LoginComponent,
    HomeComponent,
    ForbiddenComponent,
    ConfirmDialogComponent,
    CommentDialogComponent,
    ManagementTimesheetComponent,
    ComplainDialogComponent,
    UserProfileComponent,
    ManagementUserComponent,
    ManagementProjectComponent,
    ManagementDepartmentComponent,
    ManagementJobDepartmentComponent,
    EditUserDialogComponent,
    EditRoleDialogComponent,
    DeleteDialogComponent,
    ActiveDialogComponent,
    AddUserDialogComponent,
    DeleteJobDepartmentDialogComponent,
    SaveJobDepartmentDialogComponent,
    SaveDepartmentComponent,
    DeleteDepartmentComponent,
    SaveProjectComponent,
    ViewTaskComponent,
    SaveTaskComponent,
    UpdateStatusTaskComponent,
    DeleteTaskComponent,
    CommentNoteComponent,
    UpdatePasswordDialogComponent,
    ChangeInfoDialogComponent,
    UpdateStatusDialogComponent,
    DeleteProjectComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    NgbModule,
    CalendarModule.forRoot({
      provide: DateAdapter,
      useFactory: adapterFactory,
    }),
    ReactiveFormsModule
  ],
  providers: [
    CookieService,
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi:true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
