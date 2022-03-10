import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CandidateFormComponent } from './candidate-form.component';
import { SuccessComponent } from './components/success/success.component';

const routes: Routes = [
  {
    path: "",
    component: CandidateFormComponent
  },
  {
    path: "success",
    component: SuccessComponent
  },
  {
    path: "**",
    redirectTo: "/candidates/form",
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CandidateFormRoutingModule { }