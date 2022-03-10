import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Avataaar } from '../../model/avataaar/avataaar.model';
import { Stats } from '../../model/stats/stats.interface';

@Injectable({
  providedIn: 'root'
})
export class AvatarService {


  private apiUrl = environment.apiUrl + "/avatar"

  constructor(private httpClient: HttpClient) { }

  saveAvatar(avatar: Avataaar): Observable<Avataaar>{
    return this.httpClient.put<Avataaar>(this.apiUrl, avatar, {withCredentials:true});
  }

}
