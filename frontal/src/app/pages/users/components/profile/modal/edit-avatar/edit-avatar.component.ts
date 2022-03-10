import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Avataaar } from 'src/app/core/model/avataaar/avataaar.model';
import { SessionService } from 'src/app/core/auth/session.service';
import { AvatarService } from 'src/app/core/services/avatar/avatar.service';
import { AvatarSelector } from 'src/app/core/utils/avatar-selector/avatar-selector.utils';

@Component({
  selector: 'app-edit-avatar',
  templateUrl: './edit-avatar.component.html',
  styleUrls: ['./edit-avatar.component.css']
})
export class EditAvatarComponent implements OnInit {

  avatar: Avataaar;

  defaultAvatar: Avataaar  =  {
    hairColor: "BrownDark",
    clotheType: "Hoodie",
    skinColor: "Tanned",
    topType: "ShortHairTheCaesar",
    accessoriesType: "Prescription02",
    facialHairType: "Blank",
    eyeType: "Default",
    eyebrowType: "Default",
    mouthType: "ScreamOpen"
  }

  optionList = [
    {name: "Piel", aspectName: 'skinColor'},
    {name: "Cabeza", aspectName: 'topType'},
    {name: "Color de pelo", aspectName: 'hairColor'},
    {name: "Cejas", aspectName: 'eyebrowType'},
    {name: "Ojos", aspectName: 'eyeType'},
    {name: "Gafas", aspectName: 'accessoriesType'},
    {name: "Barba", aspectName: 'facialHairType'},
    {name: "Boca", aspectName: 'mouthType'},
    {name: "Ropa", aspectName: 'clotheType'}
  ]
  
  avatarSelector!: AvatarSelector;

  constructor(
    @Inject(MAT_DIALOG_DATA) public user: any,
    private modalRef: MatDialogRef<EditAvatarComponent>,
    private avatarService: AvatarService,
    private sessionService: SessionService,
    private snackBar: MatSnackBar,
  ) { 
    this.avatar = user.avatar;
    
  }
  
  ngOnInit(): void {

    if(this.avatar == null){
      this.avatar = {
        user: this.user,
        ...this.defaultAvatar,
      };
    }

    this.avatarSelector = new AvatarSelector(this.avatar);
  }

  close(){
    this.modalRef.close();
  }

  next(aspect: string){
    this.avatar[aspect] = this.avatarSelector.next(aspect);
  }

  back(aspect: string){
    this.avatar[aspect] = this.avatarSelector.back(aspect);
  }

  randomizeAvatar(){
    this.avatar = this.avatarSelector.getRandomAvatar();
  }

  saveAvatar(){
    
    this.avatarService.saveAvatar(this.avatar).subscribe({
      next: avatar => {
        this.sessionService.setUserSessionData(this.user);
        this.showSnackBar("El avatar ha sido actualizado correctamente.");
        this.close()
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
}
