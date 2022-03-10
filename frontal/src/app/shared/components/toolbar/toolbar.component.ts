import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Role } from 'src/app/core/model/role/role.enum';
import { SessionService } from 'src/app/core/auth/session.service';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.css']
})
export class ToolbarComponent implements OnInit {

  currentUserSession: any;
  
  panels : Array<any> = [
    { title: "Inicio", url: "/home", roles: [Role.CANDIDATE, Role.EMPLOYER]},
    { title: "Crear candidato", url: "/candidates/form", roles: [Role.EMPLOYER]},
    { title: "Tu candidatura", url: "/candidates/form", roles: [Role.CANDIDATE]},
    { title: "Listado de candidatos", url: "/candidates/list", roles: [Role.EMPLOYER]},
    { title: "Generar usuario", url: "/users/register", roles: [Role.EMPLOYER]}
  ]


  constructor(
    private sessionService: SessionService,
    private router: Router,
    ) { }

  ngOnInit(): void {
    this.currentUserSession = this.sessionService.getCurrentUserObservable().subscribe({
      next: value => this.currentUserSession = value
    });

    
  }
  logOut() {
    this.sessionService.deleteUserSession();
    this.router.navigate(['users/login'])
  }

  getPanels(){
    let { role } = this.currentUserSession;

    return this.panels.filter(panel => panel.roles.includes(role));
  }

}
