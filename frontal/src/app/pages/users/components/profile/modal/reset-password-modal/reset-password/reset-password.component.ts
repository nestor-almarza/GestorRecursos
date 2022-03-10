import { Component, HostListener, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { IUserReset } from 'src/app/core/model/userReset/userReset.interface';
import { IUser } from 'src/app/core/model/user/user.interface';
import { UserService } from 'src/app/core/services/user/user.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { PasswordGenerator } from 'src/app/core/utils/password-generator/password-generator.utils';
import { FormValidations } from 'src/app/core/utils/form-validation/form-validation.validation';
//import {zxcvbn} from "zxcvbn";

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css'] ,
})
export class ResetPasswordComponent implements OnInit {

  resetFormGroup: FormGroup = new FormGroup({
    password: new FormControl('', Validators.required),
    newPassword: new FormControl('', [
      Validators.required,
      Validators.minLength(8),
      Validators.maxLength(64),
      Validators.pattern(FormValidations.getPasswordPattern())
    ])
  })

   user: IUser = this.data.data; 
   hidePassword: boolean = true;
   hideNewPassword: boolean = true;

  constructor(
    private modalRef: MatDialogRef<ResetPasswordComponent>,
    private userService: UserService,
    private snackBar: MatSnackBar,
     @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit(): void {
  }

  @HostListener("keydown.enter")
  onAction(){
    if(this.resetFormGroup.valid){
      let userReset : IUserReset = {
        id: this.data.id ,
        email: this.data.email ,
        password: this.resetFormGroup.controls['password'].value.trim(),
        newPassword: this.resetFormGroup.controls['newPassword'].value.trim(),
      }
  
      this.userService.resetPassword(userReset).subscribe({
        next: () => {
          this.modalRef.close(true);
        },
        error: (err) => {
          if(err.status === 400){
            this.showSnackBar("La contraseña actual no coincide.");
          }
          else{
            this.showSnackBar("Ha ocurrido un error y no se ha podido cambiar la contraseña.");
          }
        }
      });
    }
    else{
      this.resetFormGroup.markAllAsTouched();
    }
  }

  onClose(){
    this.modalRef.close(false);
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

  generatePassword(){
    let password = PasswordGenerator.generatePassword();

    this.resetFormGroup.controls['newPassword'].setValue(password);
  }

  //Form errors
  getPasswordErrorMessage(){
    let newPassword = this.resetFormGroup.controls['newPassword'];

    if( newPassword.hasError('required')){
      return "La contraseña no puede estar vacía.";
    }
    else if( newPassword.hasError('maxlength')){
      return "La contraseña no puede ser superior a 64 caracteres.";
    } 
    else if( newPassword.hasError('minlength')){
      return "La contraseña no puede ser inferior a 8 caracteres.";
    }

    return "La contraseña debe tener una letrea minúscula, otra mayúscula y un número.";
  }
 
}



