import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UsersRoutingModule } from './users-routing.module';
import { LoginComponent } from './components/login/login.component';
import { MaterialModule } from 'src/app/shared/material/material.module';
import { ReactiveFormsModule } from '@angular/forms';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ProfileComponent } from './components/profile/profile.component';
import { ResetPasswordComponent } from './components/profile/modal/reset-password-modal/reset-password/reset-password.component';
import { RegisterComponent } from './components/register/register.component';
import { ChangeFullnameModalComponent } from './components/profile/modal/change-fullname-modal/change-fullname-modal.component';
import { PasswordMeterComponent } from './components/profile/profile-component/password-meter/password-meter.component';
import { SharedComponentsModule } from 'src/app/shared/shared-components.module';
import { EditAvatarComponent } from './components/profile/modal/edit-avatar/edit-avatar.component';


@NgModule({
  declarations: [
    LoginComponent,
    ProfileComponent,
    ResetPasswordComponent,
    RegisterComponent,
    ChangeFullnameModalComponent,
    PasswordMeterComponent,
    EditAvatarComponent,
  ],
  imports: [
    CommonModule,
    UsersRoutingModule,
    MaterialModule,
    ReactiveFormsModule,
    FlexLayoutModule,
    SharedComponentsModule,
  ]
})
export class UsersModule { }
