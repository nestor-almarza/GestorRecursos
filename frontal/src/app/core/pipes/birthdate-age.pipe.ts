import { Pipe, PipeTransform } from "@angular/core";
import * as moment from 'moment';

@Pipe({
    name: "birthDateToAge"
})
export class BirthDateToAgePipe implements PipeTransform{
    transform(value: any) {
        return moment().diff(moment(value), 'years', false);
    }
    
}