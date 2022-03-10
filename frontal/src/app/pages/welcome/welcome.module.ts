import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { WelcomeRoutingModule } from './welcome-routing.module';
import { DirectivesModule } from 'src/app/core/utils/directives/directives.module';


@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    WelcomeRoutingModule,
    DirectivesModule
  ]
})
export class WelcomeModule { }
