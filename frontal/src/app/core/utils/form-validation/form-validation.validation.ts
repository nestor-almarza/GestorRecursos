import { AbstractControl, ValidationErrors, ValidatorFn } from "@angular/forms";
import { ICandidate } from "../../model/candidate/candidate.interface";


export class FormValidations{

    static getFirstNamePattern(){
        return /^[A-Z\u00C0-\u017F\s]+$/i;
    }

    static getLastNamePattern(){
        return /^[A-Z\u00C0-\u017F\s-]+$/i;
    }

    static getCompanyPattern(){
        return /^[A-Z0-9\u00C0-\u017F\s-]+$/i;
    }

    static getTitlePattern(){
        return /^[A-Z0-9\u00C0-\u017F\u00AA\u00BA\s-]+$/i;
    }

    static getEmailPattern(){
        return /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,6}$/i;
    }

    static getPasswordPattern(){
        return /^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,64}$/;
    }

    static validateDates(group : AbstractControl) : null {

        if(group.touched || group.dirty){
            let currentlyWorking = group.get('currentlyWorking')!;
            let startDate = group.get('startDate')!;
            let endDate = group.get('endDate')!;
    
            //Init the state of errors on datepickers when user interact with them for the first time.
            initStateDatepicker(startDate, endDate);
    
            if(!currentlyWorking!.value){
                if(startDate.dirty || startDate.touched || endDate.dirty || endDate.touched){
                    if(startDate.value && !endDate.value){
                        if(!endDate.hasError('matDatepickerParse')){
                            endDate.setErrors({'required': true});
                        }
                        
                        deleteControlError(startDate, 'startToEnd');
                    }
                    else if(startDate.value && endDate.value){
                        if(endDate.hasError('matDatepickerMax')){
                            deleteControlError(startDate, 'startToEnd');
                        }
                        else{
                            if(!startDate.hasError('matDatepickerMax')){
                                if(startDate.value > endDate.value){
                                    startDate.setErrors({'startToEnd': true});
                                }
                                else{
                                    deleteControlError(startDate, 'startToEnd');
                                }
                            }
                        }
                    }
                }
            }
            else{
                deleteControlError(startDate!, 'startToEnd');
                if(!endDate?.value || endDate.hasError('matDatepickerMax')){
                    endDate!.setValue('', {onlySelf: true});
                }
            }
        }

        return null;
    }
    
// verify that new passwords' fields match
    static checkPasswords(group: AbstractControl): null {
        let pass = group.get('newPassword1')?.value;
          let confirmPass = group.get('newPassword2');
         if( pass === confirmPass?.value ){
              confirmPass?.setErrors(null);
         }else{
            confirmPass?.setErrors({mismatch: true});
         }
          return null
    }

    //Check if the input has some value
    static validateFormInput(group: AbstractControl) : ValidationErrors | null {
        if(group.value){
            if(group.value.trim().length == 0){
                return {required: true};
            }
            else{
                return null;
            }
        }
        
        return null;
    }

  }

function initStateDatepicker(startDate: AbstractControl, endDate: AbstractControl){
    //Input startDate: The first time user writes a date
    if((startDate.value || startDate.value == null) && (!startDate.dirty || !startDate.touched)){
        startDate.markAsDirty();
        startDate.markAsTouched();
    }
    
    //Input endDate: The first time user writes a date
    if((endDate.value || endDate.value == null) && (!endDate.dirty || !endDate.touched)){
        endDate?.markAsDirty();
        endDate?.markAsTouched();
    }

    //Calendar startDate: The first time user open the calendar to choose a date or writes a complete date
    if(startDate.value && (!endDate.dirty || !endDate.touched)){
        endDate.markAsDirty();
        endDate.markAsTouched();
        endDate.setErrors({'required':true})
    }

    //Calendar endDate: The first time user open the calendar to choose a date or writes a complete date
    if(endDate.value && (!startDate.dirty || !startDate.touched)){
        startDate.markAsDirty();
        startDate.markAsTouched();
        startDate.setErrors({'required':true})
    }
}

function deleteControlError(control: AbstractControl, error: string) : void{
    if(control != null && control.errors && control.hasError(error)){
        delete control.errors![error];
        control.updateValueAndValidity();
    }
}
