import { AbstractControl, FormGroup } from '@angular/forms';
import { MatDatepicker } from '@angular/material/datepicker';
import * as moment from 'moment';

export class MomentUtils {
  static currentDate = moment();

  static getCurrentDate(): moment.Moment {
    return moment();
  }

  static getMinDate(): moment.Moment {
    return moment([this.currentDate.year() - 100]);
  }

  static chosenMonthHandler(
    normalizedMonth: moment.Moment,
    datepicker: MatDatepicker<moment.Moment>,
    control: AbstractControl,
    experienceFormGroup: FormGroup
  ): void {
    let ctrlValue = normalizedMonth;
    if (control == experienceFormGroup.controls['startDate']) {
      experienceFormGroup.controls['startDate'].setValue(ctrlValue);
    } else {
      experienceFormGroup.controls['endDate'].setValue(ctrlValue);
    }
    datepicker.close();
  }
}