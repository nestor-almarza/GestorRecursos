import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { Observable } from 'rxjs';
import { SessionService } from '../auth/session.service';

@Injectable({
  providedIn: 'root'
})
export class UserGuard implements CanActivate {

  constructor(
    private cookieService: CookieService,
    private router: Router,
    private sessionService: SessionService
    ){}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      const requiredRoles = route.data.role;

      let cookie = this.cookieService.check("session");
      if(!cookie){
        this.router.navigateByUrl('/users/login');
        return false;
      }


      let userSession = this.sessionService.getUserSessionData();

      if(userSession){
        if(requiredRoles && !requiredRoles.includes(userSession.role)){
          this.router.navigateByUrl("/home")
          return false;
        }

        return true;
      }

      this.router.navigateByUrl('/users/login');

      return false;
  }
}

