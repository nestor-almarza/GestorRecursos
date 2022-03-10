import { Component, OnInit } from '@angular/core';
import { DateAdapter } from '@angular/material/core';
import { environment } from 'src/environments/environment';
import { SessionService } from './core/auth/session.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {


  version: String = environment.version;

  constructor(
    private adapter: DateAdapter<any>,
    private sessionService: SessionService
    ){
    this.adapter.setLocale('es');
    
  }
  ngOnInit(): void {
    
  }

  
}
