import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { CookieService } from "ngx-cookie-service";
import { Observable } from "rxjs";
import { environment } from "src/environments/environment";
import { Session } from "../model/session/login-response.model";

@Injectable({
    providedIn: 'root'
})
export class SessionService {
    
    apiUrl = environment.apiUrl + "/session"
	

    constructor(
        private router: Router,
        private cookieService: CookieService,
        private http: HttpClient,
    ){}

    refresh(){
        if(!this.cookieService.check("session")) return;

        this.http.post<Session>(`${this.apiUrl}/refresh`, {}, {
            withCredentials: true
            
        }).subscribe(session => this.setUserSession(session) )

    }

    setUserSession(session : Session){
        localStorage.setItem('userdata', JSON.stringify(session.user));

        this.cookieService.set(
            "session",
            session.hash,
            { expires: 1, path:"/"}
        )
    }

    setUserSessionData(userdata: any){
        localStorage.setItem('userdata', JSON.stringify(userdata));        
    }

    getUserSessionData(){
        if(!localStorage.userdata) return;

        return JSON.parse(localStorage.userdata);
    }

    deleteUserSession(){
        localStorage.clear();
        this.cookieService.deleteAll("/");
    }

    getCurrentUserObservable() {
        return new Observable<any>((subscriber) => {
          //Sends value every time route changes
          this.router.events.subscribe((event) => {
            let userSession = this.getUserSessionData();
            subscriber.next(userSession);
          });
          
        });
    }
  
}
  