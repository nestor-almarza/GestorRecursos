import { Component, HostListener, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Subscription } from 'rxjs';
import { IUser } from 'src/app/core/model/user/user.interface';
import { UserService } from 'src/app/core/services/user/user.service';
import { FormValidations } from 'src/app/core/utils/form-validation/form-validation.validation';
import { PasswordGenerator } from 'src/app/core/utils/password-generator/password-generator.utils';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit, OnDestroy {

  registerFormGroup: FormGroup = new FormGroup({
    firstName: new FormControl('', [
      Validators.minLength(3),
      Validators.maxLength(40),
      Validators.pattern(FormValidations.getFirstNamePattern())
    ]),
    lastName: new FormControl('', [
      Validators.minLength(3),
      Validators.maxLength(40),
      Validators.pattern(FormValidations.getLastNamePattern())
    ]),
    email: new FormControl('', [
      Validators.required,
      Validators.pattern(FormValidations.getEmailPattern())
    ]),
    password: new FormControl('', [
      Validators.required,
      Validators.minLength(8),
      Validators.maxLength(64),
      Validators.pattern(FormValidations.getPasswordPattern())
    ])
  });

  subscription!: Subscription;

  hidePassword: boolean = true;

  constructor(
    private userService: UserService,
    private snackBar: MatSnackBar,
  ) { }

  ngOnInit(): void {}

  ngOnDestroy(): void {
    if(this.subscription){
      this.subscription.unsubscribe();
    }
      
    this.snackBar.dismiss();
  }

  getEmailErrorMessage(){
    let email = this.registerFormGroup.controls['email'];
    if(email.hasError('required')){
      return "El email no puede estar vacío.";
    }
    
    return "El email no tiene un formato correcto.";
  }

  getFirstNameErrorMessage(){
    let name = this.registerFormGroup.controls['firstName'];

    if(name.hasError('required')){
      return "El nombre no puede estar vacío.";
    }
    else if(name.hasError('minlength')){
      return "El nombre no puede ser inferior a 3 caracteres."
    }
    else if(name.hasError('maxlength')){
      return "El nombre no puede ser superior a 40 caracteres."
    }

    return "El nombre solo puede contener caracteres alfabéticos."
  }

  getLastNameErrorMessage(){
    let name = this.registerFormGroup.controls['lastName'];

    if(name.hasError('required')){
      return "Los apellidos no pueden estar vacíos.";
    }
    else if(name.hasError('minlength')){
      return "Los apellidos no pueden ser inferiores a 3 caracteres."
    }
    else if(name.hasError('maxlength')){
      return "Los apellidos no pueden ser superiores a 40 caracteres."
    }

    return "Los apellidos solo pueden contener caracteres alfabéticos."
  }

  getPasswordErrorMessage(){
    let password = this.registerFormGroup.controls['password'];

    if(password.hasError('required')){
      return "La contraseña no puede estar vacía.";
    }
    else if(password.hasError('maxlength')){
      return "La contraseña no puede ser superior a 64 caracteres.";
    } 
    else if(password.hasError('minlength')){
      return "La contraseña no puede ser inferior a 8 caracteres.";
    }

    return "La contraseña debe tener una letrea minúscula, otra mayúscula y un número.";
  }

  @HostListener('keydown.enter')
  register(){
    if(this.registerFormGroup.valid){
      
      let user : IUser = {
        firstName: this.registerFormGroup.controls['firstName'].value,
        lastName: this.registerFormGroup.controls['lastName'].value,
        email: this.registerFormGroup.controls['email'].value,
        password: this.registerFormGroup.controls['password'].value
      }

      this.subscription = this.userService.generateUser(user).subscribe({
        next: () =>{
          this.registerFormGroup.reset();
          this.showSnackbar("Usuario registrado correctamente.", 15000);
        },
        error: (error) =>{
          if(error.status === 400){
            this.showSnackbar('Ese email ya está en uso.');
          }
          else if(error.status === 412){
            this.showSnackbar("Los datos no tienen un formato correcto.");
          }
          else{
            this.showSnackbar('No se ha podido registrar al usuario.');
          }
        }
      });
    }
    else{
      this.registerFormGroup.markAllAsTouched();
    }
  }

  generatePassword(){
    let password = PasswordGenerator.generatePassword();

    this.registerFormGroup.controls['password'].setValue(password);
  }

  private showSnackbar(msg: string, duration?: number){
    this.snackBar.open(msg, 'Aceptar', {
      horizontalPosition: 'center',
      verticalPosition: 'bottom',
      duration: duration ? duration : 5000
    });
  }

}
