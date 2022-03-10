import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChange } from '@angular/core';

@Component({
  selector: 'app-password-meter',
  templateUrl: './password-meter.component.html',
  styleUrls: ['./password-meter.component.css']
})
export class PasswordMeterComponent implements  OnChanges {


  @Input()
  public passwordToCheck!: string;
  @Output() passwordMeter = new EventEmitter<boolean>();
  
   levels = document.getElementsByClassName("point")
  msg = '';
  private colors = ['darkred', 'orangered', 'orange', 'yellowgreen'];

  private static checkStrength(password:string) {
    let force = 0;
    const regex = /[$-/:-?{-~!"^_@`\[\]]/g;

    const lowerLetters = /[a-z]+/.test(password);
    const upperLetters = /[A-Z]+/.test(password);
    const numbers = /[0-9]+/.test(password);
    const symbols = regex.test(password);

    const flags = [lowerLetters, upperLetters, numbers, symbols];

    let passedMatches = 0;
    for (const flag of flags) {
      passedMatches += flag === true ? 1 : 0;
    }

    force += 2 * password.length + ((password.length >= 10) ? 1 : 0);
    force += passedMatches * 10;

    // short password
    force = (password.length <= 6) ? Math.min(force, 10) : force;

    // poor variety of characters
    force = (passedMatches === 1) ? Math.min(force, 10) : force;
    force = (passedMatches === 2) ? Math.min(force, 20) : force;
    force = (passedMatches === 3) ? Math.min(force, 30) : force;
    force = (passedMatches === 4) ? Math.min(force, 40) : force;

    return force;
  }

  ngOnChanges(changes: { [propName: string]: SimpleChange }): void {
    const password = changes.passwordToCheck.currentValue;
    this.setBarColors(4, '#DDD');
    if (password) {
      const c = this.getColor(PasswordMeterComponent.checkStrength(password));
      this.setBarColors(c.index, c.color);

      const pwdStrength = PasswordMeterComponent.checkStrength(password);
      pwdStrength === 40 ? this.passwordMeter.emit(true) : this.passwordMeter.emit(false);

      switch (c.index) {
        case 1:
          this.msg = 'Básica';
          break;
        case 2:
          this.msg = 'Intermedia';
          break;
        case 3:
          this.msg = 'Adecuada';
          break;
        case 4:
          this.msg = 'Segura';
          break;
      }
    } else {
      this.msg = '';
    }
  }


  private getColor(force:number) {
    let idx = 0;
    if (force <= 10) {
        idx = 0;
    } else if (force <= 20) {
        idx = 1;
    } else if (force <= 30) {
        idx = 2;
    } else if (force <= 40) {
        idx = 3;
    } else {
        idx = 4;
    }
    return {
        index: idx + 1,
        color: this.colors[idx]
    };
  }

  private setBarColors(index:number, color:string) {
    for (let n = 0; n < index; n++) {
      this.levels[n].setAttribute("style", `background-color: ${color};`); 
    }
  }

}
