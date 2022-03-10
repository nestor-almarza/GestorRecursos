import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { experienceDurationPipe } from './experience-duration.pipe';
import { DateMiniGestorPipe } from './date-json.pipe';
import { BirthDateToAgePipe } from './birthdate-age.pipe';
import { ExperienceMonth } from './experience-month.pipe';


@NgModule({
  declarations: [
    experienceDurationPipe,
    DateMiniGestorPipe,
    BirthDateToAgePipe,
    ExperienceMonth
  ],
  imports: [
    CommonModule
  ],
  exports: [
    experienceDurationPipe,
    DateMiniGestorPipe,
    BirthDateToAgePipe,
    ExperienceMonth
  ]
})
export class PipesModule { }