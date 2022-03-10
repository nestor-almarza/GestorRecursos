import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import {
  AbstractControl,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { FormValidations } from 'src/app/core/utils/form-validation/form-validation.validation';
import * as _moment from 'moment';
import { ICandidate } from 'src/app/core/model/candidate/candidate.interface';
import { parseExperience } from 'src/app/core/utils/parser/candidate.parser';
import { MatDatepicker } from '@angular/material/datepicker';
import { IExperience } from 'src/app/core/model/experience/experience.interface';
import { MomentUtils } from 'src/app/core/utils/moment/moment.utils';

const moment = _moment;

@Component({
  selector: 'app-experience-form',
  templateUrl: './experience-form.component.html',
  styleUrls: ['./experience-form.component.css'],
})
export class ExperienceFormComponent implements OnInit {
  @Output()
  onEditedExperience: EventEmitter<IExperience> = new EventEmitter();

  @Output()
  onAddedExperience: EventEmitter<IExperience> = new EventEmitter();

  @Output()
  completed: EventEmitter<null> = new EventEmitter<null>();

  @Input()
  experience!: IExperience;

  @Input()
  isNewExperience!: Boolean;

  minDate = MomentUtils.getMinDate();
  maxDate = MomentUtils.getCurrentDate();

  experienceFormGroup = new FormGroup(
    {
      company: new FormControl('', [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(50),
        Validators.pattern(FormValidations.getCompanyPattern()),
        FormValidations.validateFormInput
      ]),
      title: new FormControl('', [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(50),
        Validators.pattern(FormValidations.getTitlePattern()),
        FormValidations.validateFormInput
      ]),
      description: new FormControl('', [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(300),
        FormValidations.validateFormInput
      ]),
      currentlyWorking: new FormControl(false),
      startDate: new FormControl('', Validators.required),
      endDate: new FormControl(''),
    },
    [FormValidations.validateDates]
  );

  constructor() {}

  ngOnInit(): void {
    this.experienceFormGroup.patchValue(this.experience);
  }

  addExperience() {
    if(this.experienceFormGroup.valid){
      let experience = this.experienceFormGroup.value;

      experience = parseExperience(experience);
  
      this.onAddedExperience.emit(experience);
      this.experienceFormGroup.reset();
    }
    else{
      this.experienceFormGroup.markAllAsTouched();
    }
    
  }

  editExperience() {
    if(this.experienceFormGroup.valid){
      let experience = this.experienceFormGroup.value;
      experience['id'] = this.experience.id;
  
      experience = parseExperience(experience);
  
      this.onEditedExperience.emit(experience);
      this.experienceFormGroup.reset();
    }
    else{
      this.experienceFormGroup.markAllAsTouched();
    }
    
  }

  closeExperience() {
    this.completed.emit();
    this.experienceFormGroup.reset();
  }

  chosenMonthHandler(
    normalizedMonth: _moment.Moment,
    datepicker: MatDatepicker<_moment.Moment>,
    control: AbstractControl
  ) {
    return MomentUtils.chosenMonthHandler(
      normalizedMonth,
      datepicker,
      control,
      this.experienceFormGroup
    );
  }

  //Form errors
  getTitleErrorMessage(){
    let title = this.experienceFormGroup.controls['title'];
    if(title.hasError('required')){
      return "El título no puede estar vacío.";
    }
    else if(title.hasError('minlength')){
      return "El título no puede ser inferior a 3 caracteres."
    }
    else if(title.hasError('maxlength')){
      return "El título no puede ser superior a 50 caracteres."
    }

    return "El título contiene caracteres inválidos."
  }

  getCompanyErrorMessage(){
    let company = this.experienceFormGroup.controls['company'];
    if(company.hasError('required')){
      return "La empresa no puede estar vacía.";
    }
    else if(company.hasError('minlength')){
      return "La empresa no puede ser inferior a 3 caracteres."
    }
    else if(company.hasError('maxlength')){
      return "La empresa no puede ser superior a 50 caracteres."
    }

    return "La empresa contiene caracteres inválidos."
  }

  getStartErrorMessage(){
    let startDate = this.experienceFormGroup.controls['startDate'];

    if(startDate.hasError('required')){
      return "La fecha de inicio no puede estar vacía.";
    }
    else if(startDate.hasError('startToEnd')){
      return "La fecha de inicio no puede superior a la fecha de fin.";
    }
    else if(startDate.hasError('matDatepickerParse')){
      return "La fecha de inicio debe tener el formato MM/YYYY."
    }
    else if(startDate.hasError('matDatepickerMin')){
      return `La fecha de inicio no puede ser anterior a ${this.minDate.year()}.`
    }

    return "La fecha de inicio no puede ser futura."
  }

  getEndErrorMessage(){
    let endDate = this.experienceFormGroup.controls['endDate'];

    if(endDate.hasError('required')){
      return "La fecha de fin no puede estar vacía.";
    }
    else if(endDate.hasError('matDatepickerParse')){
      return "La fecha de fin debe tener el formato MM/YYYY."
    }
    else if(endDate.hasError('matDatepickerMin')){
      return `La fecha de fin no puede ser anterior a ${this.minDate.year()}.`
    }

    return "La fecha de fin no puede ser futura."
  }

  getDescriptionErrorMessage(){
    let description = this.experienceFormGroup.controls['description'];
    if(description.hasError('required')){
      return "La empresa no puede estar vacía.";
    }
    else if(description.hasError('minlength')){
      return "La empresa no puede ser inferior a 3 caracteres."
    }
    
    return "La empresa no puede ser superior a 50 caracteres."
  }
}
