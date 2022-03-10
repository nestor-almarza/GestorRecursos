import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BirthDateFormatDirective } from './birthdate.directive';
import { ExperienceDatesFormatDirective } from './experienceDates.directive';
import { RoleDirective } from './role.directive';


@NgModule({
  declarations: [
    BirthDateFormatDirective,
    ExperienceDatesFormatDirective,
    RoleDirective
  ],
  imports: [
    CommonModule
  ],
  exports: [
    BirthDateFormatDirective,
    ExperienceDatesFormatDirective,
    RoleDirective
  ]
})
export class DirectivesModule { }