import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CandidateFormComponent } from './candidate-form.component';
import { ExperienceFormComponent } from './components/experience-form/experience-form.component';
import { SuccessComponent } from './components/success/success.component';
import { MaterialModule } from 'src/app/shared/material/material.module';
import { ReactiveFormsModule } from '@angular/forms';
import { FlexLayoutModule } from '@angular/flex-layout';
import { SuccessTickComponent } from 'src/app/shared/components/success-tick/success-tick.component';
import { CandidateFormRoutingModule } from './candidate-form-routing.module';
import { PipesModule } from 'src/app/core/pipes/pipes.module';
import { DirectivesModule } from 'src/app/core/utils/directives/directives.module';
import { DeleteExperienceModalComponent } from './components/delete-experience-modal/delete-experience-modal.component';

@NgModule({
  declarations: [
    CandidateFormComponent,
    ExperienceFormComponent,
    SuccessComponent,
    SuccessTickComponent,
    DeleteExperienceModalComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    FlexLayoutModule,
    CandidateFormRoutingModule,
    PipesModule,
    DirectivesModule
  ],
  exports: [
    CandidateFormComponent
  ]
})
export class CandidateFormModule { }
