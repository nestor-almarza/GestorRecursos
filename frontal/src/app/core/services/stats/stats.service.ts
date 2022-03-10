import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Stats } from '../../model/stats/stats.interface';

@Injectable({
  providedIn: 'root'
})
export class StatsService {


  private apiUrl = environment.apiUrl + "/stats"

  constructor(private httpClient: HttpClient) { }

  getGeneralStats(): Observable<Stats>{
    return this.httpClient.get<Stats>(this.apiUrl, {withCredentials:true});
  }

}
