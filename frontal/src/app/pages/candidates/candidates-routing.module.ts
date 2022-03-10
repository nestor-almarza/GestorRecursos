import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Role } from 'src/app/core/model/role/role.enum';
import { UserGuard } from 'src/app/core/guard/user.guard';


const routes: Routes = [
  {
    path: "form",
    loadChildren: () => import("./components/candidate-form/candidate-form.module").then(module => module.CandidateFormModule),
    canActivate: [UserGuard],
    data: {
      role: [Role.CANDIDATE, Role.EMPLOYER]
    }
  },
  {
    path: "list",
    loadChildren: () => import("./components/candidate-list/candidate-list.module").then(module => module.CandidateListModule),
    canActivate: [UserGuard],
    data: {
      role: Role.EMPLOYER
    }
  },
  {
    path: '',
    redirectTo: 'form',
    pathMatch: 'full'
  },
  {
    path: "**",
    redirectTo: "/home",
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CandidatesRoutingModule { }
