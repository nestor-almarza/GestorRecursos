import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CandidateListComponent } from './candidate-list.component';

const routes: Routes = [
  {
    path: "",
    component: CandidateListComponent
  },
  {
    path: "**",
    redirectTo: "/candidates/list",
    pathMatch: "full"
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CandidateListRoutingModule { }