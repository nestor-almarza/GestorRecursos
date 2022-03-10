import { AfterContentChecked, AfterContentInit, AfterViewInit, Component, Input, OnInit } from '@angular/core';
import { Avataaar } from 'src/app/core/model/avataaar/avataaar.model';

@Component({
  selector: 'app-avataaar',
  template: '<img [src]="avatarUrl" />',
  styleUrls: ['./avataaar.component.css']
})
export class AvataaarComponent implements OnInit, AfterContentChecked {

  /** More info about Avataaar in https://getavataaars.com/ */

  avatarUrl!: string;

  @Input()
  avatar?: Avataaar;
  
  defaultAvatar: any = {}

  constructor() { }

  ngOnInit(): void {
  }
  
  ngAfterContentChecked(): void {

    if(this.avatar)
      this.setAvatarUrl(this.avatar)
    else
      this.setAvatarUrl(this.defaultAvatar)
      
  }

  setAvatarUrl(avatar: Avataaar){
    this.avatarUrl = 
    "https://avataaars.io/" 
    + `?avatarStyle=Circle`
    + `&hairColor=${avatar.hairColor}`
    + `&clotheType=${avatar.clotheType}`
    + `&skinColor=${avatar.skinColor}`
    + `&topType=${avatar.topType}`
    + `&accessoriesType=${avatar.accessoriesType}`
    + `&facialHairType=${avatar.facialHairType}`
    + `&eyeType=${avatar.eyeType}`
    + `&eyebrowType=${avatar.eyebrowType}`
    + `&mouthType=${avatar.mouthType}`;
  }

}
