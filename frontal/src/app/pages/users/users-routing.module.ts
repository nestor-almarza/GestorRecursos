import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Role } from 'src/app/core/model/role/role.enum';
import { UserGuard } from 'src/app/core/guard/user.guard';
import { LoginComponent } from './components/login/login.component';
import { ProfileComponent } from './components/profile/profile.component';
import { RegisterComponent } from './components/register/register.component';

const routes: Routes = [
  {
    path: "login",
    component: LoginComponent
  },
  {
    path: "me",
    component: ProfileComponent,
    canActivate: [UserGuard]
  },
  {
    path: "register",
    component: RegisterComponent,
    canActivate: [UserGuard],
    data: {
      role: Role.EMPLOYER
    }
  },
  {
    path: "**",
    redirectTo: "me"
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UsersRoutingModule { }
