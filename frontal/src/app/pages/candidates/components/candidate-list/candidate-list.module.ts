import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MaterialModule } from 'src/app/shared/material/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ListModule } from './components/list/list.module';
import { CandidateListRoutingModule } from './candidate-list-routing.module';

import { CandidateListComponent } from './candidate-list.component';

@NgModule({
  declarations: [
    CandidateListComponent,
  ],
  imports: [
    CommonModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    FlexLayoutModule,
    ListModule,
    CandidateListRoutingModule
  ],
  exports: [
    MaterialModule,
    CandidateListComponent
    
  ]
})
export class CandidateListModule { }
