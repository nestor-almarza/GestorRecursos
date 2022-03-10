import { Pipe, PipeTransform } from "@angular/core";
import * as moment from "moment";

@Pipe({
    name: 'monthToString'
})
export class ExperienceMonth implements PipeTransform {
    
    transform(value: any): string {
        let month = moment(value).locale('es-ES').format("MMMM YYYY");
        month = month[0].toUpperCase() + month.slice(1);
        
        return month;
    }
  
  }