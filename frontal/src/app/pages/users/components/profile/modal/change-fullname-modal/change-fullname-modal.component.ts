import { Component, HostListener, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Subscription } from 'rxjs';
import { IUser } from 'src/app/core/model/user/user.interface';
import { UserService } from 'src/app/core/services/user/user.service';
import { FormValidations } from 'src/app/core/utils/form-validation/form-validation.validation';

@Component({
  selector: 'app-change-fullname-modal',
  templateUrl: './change-fullname-modal.component.html',
  styleUrls: ['./change-fullname-modal.component.css']
})
export class ChangeFullnameModalComponent implements OnInit {

  fullNameFormGroup: FormGroup = new FormGroup({
    firstName: new FormControl('', [
      Validators.minLength(3),
      Validators.maxLength(40),
      Validators.pattern(FormValidations.getFirstNamePattern()),
      FormValidations.validateFormInput
    ]),
    lastName: new FormControl('', [
      Validators.minLength(3),
      Validators.maxLength(40),
      Validators.pattern(FormValidations.getLastNamePattern()),
      FormValidations.validateFormInput
    ]),
    password: new FormControl('', Validators.required)
  })

  hidePassword: boolean = true;

  subscription!: Subscription;

  constructor(
    private userService: UserService,
    private snackBar: MatSnackBar,
    private modalRef: MatDialogRef<ChangeFullnameModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: IUser) { }

  ngOnInit(): void {}

  getEmailErrorMessage(){
    let email = this.fullNameFormGroup.controls['email'];
    if(email.hasError('required')){
      return "El email no puede estar vacío.";
    }
    
    return "El email no tiene un formato correcto.";
  }

  getFirstNameErrorMessage(){
    let name = this.fullNameFormGroup.controls['firstName'];

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
    let name = this.fullNameFormGroup.controls['lastName'];

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

  onClose(){
    this.modalRef.close(false);
  }

  @HostListener("keydown.enter")
  changeFullName(){
    if(this.fullNameFormGroup.valid){
      let user : IUser = {
        ...this.data,
        firstName: this.fullNameFormGroup.controls['firstName'].value,
        lastName: this.fullNameFormGroup.controls['lastName'].value,
        password: this.fullNameFormGroup.controls['password'].value
      }
      
      this.userService.changeFullName(user).subscribe({
        next: (result) =>{
          this.modalRef.close({result: true, data: result});
        },
        error: (error) =>{
          if(error.status === 400){
            this.showSnackBar("La contraseña no coincide.");
          }
          else{
            this.showSnackBar("Ha ocurrido un error y no se ha podido actualizar el nombre completo.");
          }
        }
      });
    }
    else{
      this.fullNameFormGroup.markAllAsTouched();
    }
  }

  showSnackBar(msg: string) {
    this.snackBar.open(msg, 'Aceptar', {
        horizontalPosition: 'center',
        verticalPosition: 'bottom',
        duration: 5000 ,
      }).onAction().subscribe(() => {
        this.snackBar.dismiss();
      });
  }

}
