import { Pipe, PipeTransform } from '@angular/core';
import * as moment from 'moment';

@Pipe({
  name: 'dateMiniGestor'
})
export class DateMiniGestorPipe implements PipeTransform {

  transform(value: any): string {
    return moment(value).format("MM/YYYY");
  }

}
