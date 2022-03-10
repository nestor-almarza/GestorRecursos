import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { ICandidate } from 'src/app/core/model/candidate/candidate.interface';
import { IUser } from 'src/app/core/model/user/user.interface';
import { IUserReset } from 'src/app/core/model/userReset/userReset.interface';
import { SessionService } from 'src/app/core/auth/session.service';
import { UserService } from 'src/app/core/services/user/user.service';
import { ResetPasswordComponent } from './modal/reset-password-modal/reset-password/reset-password.component';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ChangeFullnameModalComponent } from './modal/change-fullname-modal/change-fullname-modal.component';
import { EditAvatarComponent } from './modal/edit-avatar/edit-avatar.component';


@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  user: IUser = {
    id: 'NO ID',
    email: 'NO USER',
    createAt:  new Date(),
  };


  constructor(
    private snackBar: MatSnackBar,
    private modal: MatDialog,
    private userService: UserService,
    private router: Router,
    private sessionService: SessionService,
    ) { }

  ngOnInit(): void {
    let userId = this.sessionService.getUserSessionData().id;
    this.getUserData(userId);
  }

  getUserData(userId: string){
    this.userService.getUserById(userId).subscribe({
      next: user => {
        this.user = user;
      }
    });
  }

  showSnackBar(msg: string) {
    this.snackBar
      .open(msg, 'Aceptar', {
        horizontalPosition: 'center',
        verticalPosition: 'bottom',
        duration: 5000 ,
      })
      .onAction()
      .subscribe(() => {
        this.snackBar.dismiss();
      });
  }

  setPassword() {
    const modal = this.modal.open(ResetPasswordComponent, {
      data: this.user,
      autoFocus: false,
    });
    
    modal.afterClosed().subscribe((result) => {
      if(result){
        this.showSnackBar("La contraseña se ha actualizado correctamente.");
      }
    });
  }

  changeFullName(){
    const modal = this.modal.open(ChangeFullnameModalComponent, {
      data: this.user,
      autoFocus: false
    });

    modal.afterClosed().subscribe((result) =>{
      if(result && result.result){
        this.sessionService.setUserSessionData({
          ...this.sessionService.getUserSessionData(),
          firstName: result.data.firstName,
          lastName: result.data.lastName,
          fullName: result.data.fullName
        });

        this.getUserData(this.sessionService.getUserSessionData().id);
        //Neccesary to use navigateByUrl to this url to refresh the toolbar. This works because
        //app-routing-module has "{onSameUrlNavigation: "reload"}".
        this.router.navigateByUrl(this.router.url);

        this.showSnackBar("El nombre completo se ha actualizado correctamente.");
      }
    });
  }

  changeAvatar(){
    const modal = this.modal.open(EditAvatarComponent, {
      data: this.user,
      autoFocus: false
    });
    
    modal.afterClosed().subscribe(next => {
      this.getUserData(this.user.id!)

      //Neccesary to use navigateByUrl to this url to refresh the toolbar. This works because
      //app-routing-module has "{onSameUrlNavigation: "reload"}".
      this.router.navigateByUrl(this.router.url);
    })
  }
}
