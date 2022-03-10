import { Component, OnInit } from '@angular/core';
import { Role } from 'src/app/core/model/role/role.enum';
import { Stats } from 'src/app/core/model/stats/stats.interface';
import { SessionService } from 'src/app/core/auth/session.service';
import { StatsService } from 'src/app/core/services/stats/stats.service';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.css']
})
export class WelcomeComponent implements OnInit {
  modal: any;

  // panels : Array<any> = [
  //   { title: "Formulario candidatos", url: "/candidates/form", roles: [Role.CANDIDATE, Role.EMPLOYER]},
  //   { title: "Lista candidatos", url: "/candidates/list", roles: [Role.EMPLOYER]}
  // ]

  stats: Stats = {};
  
  isEmployer!: boolean;

  constructor(
    private statsService: StatsService,
    private sessionService: SessionService) { }

  ngOnInit(): void {
    this.isEmployer = this.sessionService.getUserSessionData().role == Role.EMPLOYER;
    this.statsService.getGeneralStats()
                    .subscribe(stats => this.stats = stats)
  }

}
