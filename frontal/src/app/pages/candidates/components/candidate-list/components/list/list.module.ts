import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ReactiveFormsModule } from '@angular/forms';
import { FlexLayoutModule } from '@angular/flex-layout';

import { MaterialModule } from 'src/app/shared/material/material.module';

import { FiltersComponent } from './filters/filters.component';
import { TableComponent } from './table/table.component';
import { DeleteModalComponent } from './modal/delete-modal/delete-modal.component';
import { VisualizePDFModalComponent } from './modal/visualize-pdf-modal/visualize-pdf-modal.component';
import { PreviewModalComponent } from './modal/preview-modal/preview-modal.component';
import { PipesModule } from 'src/app/core/pipes/pipes.module';
import { DirectivesModule } from 'src/app/core/utils/directives/directives.module';
import { AssignUserModalComponent } from './modal/assign-user-modal/assign-user-modal.component';
// import { experienceDurationPipe } from 'src/app/core/pipes/experience-duration.pipe';

@NgModule({
  declarations: [
    FiltersComponent,
    TableComponent,
    DeleteModalComponent,
    VisualizePDFModalComponent,
    PreviewModalComponent,
    AssignUserModalComponent,
  ],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    FlexLayoutModule,
    PipesModule,
    DirectivesModule
  ],
  exports: [
    MaterialModule,
    FiltersComponent,
    TableComponent,

  ]
})
export class ListModule { }
