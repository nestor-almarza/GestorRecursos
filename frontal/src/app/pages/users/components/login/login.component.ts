import { Component, HostListener, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { Subscription } from 'rxjs';
import { Role } from 'src/app/core/model/role/role.enum';
import { IUser } from 'src/app/core/model/user/user.interface';
import { UserService } from 'src/app/core/services/user/user.service';
import { SessionService } from 'src/app/core/auth/session.service';
import { Session } from 'src/app/core/model/session/login-response.model';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy {

  loginFormGroup: FormGroup = new FormGroup({
    email: new FormControl('', [
      Validators.required,
      Validators.email
    ]),
    password: new FormControl('', Validators.required)
  })

  hidePassword: boolean = true;

  constructor(
    private cookieService: CookieService,
    private userService: UserService,
    private snackBar: MatSnackBar,
    private router: Router,
    private sessionService: SessionService
    ) { }

  ngOnInit(): void {
    //if(localStorage.getItem('token_access')){
    if(this.sessionService.getUserSessionData()){
      this.router.navigateByUrl("/home");
    }
  }

  ngOnDestroy(): void {
    this.snackBar.dismiss();
  }

  getEmailErrorMessage(){
    if(this.loginFormGroup.controls['email'].hasError('required')){
      return "El email no puede estar vacío.";
    }
    
    return "El email no tiene un formato correcto.";
  }

  @HostListener('keydown.enter')
  login(){
    if(this.loginFormGroup.valid){
      let user : IUser = {
        email: this.loginFormGroup.controls['email'].value,
        password: this.loginFormGroup.controls['password'].value
      }
      
      this.userService.login(user).subscribe({
        next: () =>{
          this.loginFormGroup.reset();
          this.router.navigateByUrl("/home");
        },
        error: () =>{
          this.snackBar.open('Usuario y/o contraseña incorrectos.', 'Aceptar', {
            horizontalPosition: 'center',
            verticalPosition: 'bottom',
            duration: 5000
          })
        }
      });
    }
    else{
      this.loginFormGroup.markAllAsTouched();
    }
  }
}
