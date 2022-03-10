import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { IUser } from '../../model/user/user.interface';
import { IUserReset } from '../../model/userReset/userReset.interface';

import { ActivatedRoute, Router } from '@angular/router';
import { Session } from '../../model/session/login-response.model';
import { SessionService } from '../../auth/session.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl = environment.apiUrl + "/user"

  constructor(
    private httpClient: HttpClient,
    private cookieService: CookieService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private sessionService: SessionService
  ) {}
    
  getUserById(userId: string) {
    return this.httpClient.get<IUser>(`${this.apiUrl}/${userId}`,{
      withCredentials: true,
    });
  }

  login(user : IUser) : Observable<void>{

    return new Observable<void>(subscriber => {
      this.httpClient.post<Session>(`${this.apiUrl}/login`, user).subscribe({
        next: session => {
          this.sessionService.setUserSession(session);
          subscriber.next()
        },
        error: error => subscriber.error(error),
        complete: ()=> subscriber.complete()
      });
    })
  }

 
  resetPassword(userReset : IUserReset) : Observable<IUser>{
    return this.httpClient.post<any>(`${this.apiUrl}/settings/password`,
     userReset,
    {withCredentials:true});
  }

  generateUser(user: IUser) : Observable<IUser>{
    return this.httpClient.post<IUser>(`${this.apiUrl}/new`, user, {
      withCredentials: true
    });
  }

  changeFullName(user: IUser) : Observable<IUser>{
    return this.httpClient.put<IUser>(`${this.apiUrl}/edit`, user, {withCredentials:true});
  }

  getListUserCandidate() : Observable<IUser[]>{
    return this.httpClient.get<IUser[]>(`${this.apiUrl}/list/candidates`, {withCredentials:true});
  }

}
