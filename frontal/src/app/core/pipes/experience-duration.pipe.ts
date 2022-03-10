import { Pipe, PipeTransform } from '@angular/core';
import * as moment from 'moment';

@Pipe({
  name: 'experienceDuration',
})
export class experienceDurationPipe implements PipeTransform {
  transform(value: number): string {
    let years = Math.floor(value / 12);
    let remainingMonths = value % 12;

    function getYearsStr(n:number = 0){
       if( n < 1 ){return ""}
       if(n === 1){return `${n} año` };
       return `${n} años`
    }

    function getMonthStr(n:number = 0, str:string = ""){
      if(str === ""){
      if( n < 1 ){return `sin experiencia`};
      if(n === 1){return `${n} mes ` };
      return `${n} meses `
      }
      
      if( n < 1 ){return `${str}`}
      if(n === 1){return `${str} y ${n} mes ` };
      return `${str} y ${n} meses `
   }

    let result = getMonthStr(remainingMonths, getYearsStr(years))
    return result ;
  }
}
