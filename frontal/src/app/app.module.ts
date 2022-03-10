import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './shared/material/material.module';

import { FlexLayoutModule } from '@angular/flex-layout';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { ToolbarComponent } from './shared/components/toolbar/toolbar.component';
import { WelcomeComponent } from './pages/welcome/welcome.component';
import { ErrorComponent } from './shared/components/error/error.component';
import { CookieService } from 'ngx-cookie-service';
import { DirectivesModule } from './core/utils/directives/directives.module';
import { SharedComponentsModule } from './shared/shared-components.module';

@NgModule({
  declarations: [
    AppComponent,
    ToolbarComponent,
    WelcomeComponent,
    ErrorComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    FlexLayoutModule,
    DirectivesModule,
    SharedComponentsModule,
  ],
  providers: [CookieService],
  bootstrap: [AppComponent],
})
export class AppModule {}
