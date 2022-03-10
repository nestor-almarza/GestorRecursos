import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Role } from './core/model/role/role.enum';
import { UserGuard } from './core/guard/user.guard';
import { ErrorComponent } from './shared/components/error/error.component';

const routes: Routes = [
  {
    path: "users",
    loadChildren: () => import("./pages/users/users.module").then(module => module.UsersModule)
  },
  {
    path: "home",
    loadChildren: () => import("./pages/welcome/welcome.module").then(module => module.WelcomeModule),
    canActivate:[UserGuard],
    data: {
      role: [Role.EMPLOYER, Role.CANDIDATE]
    }
  },
  {
    path: "candidates",
    loadChildren: () => import("./pages/candidates/candidates.module").then(module => module.CandidatesModule),
    canActivate:[UserGuard]
  },
  {
    path: "",
    redirectTo: "home",
    pathMatch: "full"
  },
  {
    path: "**",
    redirectTo: "error",
    pathMatch: "full"
  },
  {
    path: "error",
    component: ErrorComponent
  }
];
@NgModule({
  imports: [RouterModule.forRoot(routes, {onSameUrlNavigation: "reload"})],
  exports: [RouterModule]
})
export class AppRoutingModule { }