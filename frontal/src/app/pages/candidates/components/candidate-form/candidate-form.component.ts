import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { FormValidations } from 'src/app/core/utils/form-validation/form-validation.validation';

import * as _moment from 'moment';
import { MAT_MOMENT_DATE_ADAPTER_OPTIONS, MAT_MOMENT_DATE_FORMATS, MomentDateAdapter } from '@angular/material-moment-adapter';
import { parseCandidate, parseExperience, parseExperienceDatesToInput } from 'src/app/core/utils/parser/candidate.parser';

import { ExperienceService } from 'src/app/core/services/experience/experience.service';

import { MomentUtils } from 'src/app/core/utils/moment/moment.utils';
import { ActivatedRoute, Router } from '@angular/router';
import { IExperience } from 'src/app/core/model/experience/experience.interface';
import { ICandidate } from 'src/app/core/model/candidate/candidate.interface';
import { CandidateService } from 'src/app/core/services/candidate/candidate.service';
import { MatDialog } from '@angular/material/dialog';
import { DeleteExperienceModalComponent } from './components/delete-experience-modal/delete-experience-modal.component';
import { SessionService } from 'src/app/core/auth/session.service';
import { Role } from 'src/app/core/model/role/role.enum';
import { IUser } from 'src/app/core/model/user/user.interface';
import { MatTabGroup } from '@angular/material/tabs';
import { UserService } from 'src/app/core/services/user/user.service';

import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';

const SECONDS = 1000;

@Component({
  selector: 'app-candidate-form',
  templateUrl: './candidate-form.component.html',
  styleUrls: ['./candidate-form.component.css'],
  providers: [
    {
      provide: DateAdapter,
      useClass: MomentDateAdapter,
      deps: [MAT_DATE_LOCALE, MAT_MOMENT_DATE_ADAPTER_OPTIONS],
    },
    { provide: MAT_MOMENT_DATE_ADAPTER_OPTIONS, useValue: { strict: true } },
    { provide: MAT_DATE_FORMATS, useValue: MAT_MOMENT_DATE_FORMATS },
    { provide: MAT_DATE_LOCALE, useValue: 'es' }
  ],
})
export class CandidateFormComponent implements OnInit {
  @Input()
  candidate!: ICandidate;

  @Output()
  onEditCandidate: EventEmitter<ICandidate> = new EventEmitter();

  @Output()
  onError: EventEmitter<boolean> = new EventEmitter();

  minDate = MomentUtils.getMinDate();
  maxDate = MomentUtils.getCurrentDate();
  isEditingExperience = false;
  isAddingNewExperience = false;
  selectedExperienceIndex!: number;

  experienceToEdit!: IExperience;

  candidateFormGroup = new FormGroup({
    firstName: new FormControl('', [
      Validators.required,
      Validators.minLength(3),
      Validators.maxLength(40),
      Validators.pattern(FormValidations.getFirstNamePattern()),
      FormValidations.validateFormInput
    ]),
    lastName: new FormControl('', [
      Validators.required,
      Validators.minLength(3),
      Validators.maxLength(40),
      Validators.pattern(FormValidations.getLastNamePattern()),
      FormValidations.validateFormInput
    ]),
    birthDate: new FormControl('', Validators.required),
    observation: new FormControl('', [
      Validators.minLength(3),
      Validators.maxLength(300)
    ])
  })

  experienceFormGroup!: FormGroup;

  idCandidate!: string;

  userSession = this.sessionService.getUserSessionData();

  @ViewChild("tabGroup") matTabGroup!: MatTabGroup

  userCandidateForm = new FormControl('', [Validators.required]);

  listUserCandidate: IUser[] = [];

  filteredUserOptions: IUser[] = [];

  noUser = {id: '', fullName: 'Nadie'};

  assigned: boolean = false;

  constructor(
    private candidateService: CandidateService,
    private experienceService: ExperienceService,
    private snackBar: MatSnackBar,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private modal: MatDialog,
    private sessionService: SessionService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.candidate = <ICandidate>{};
    this.candidate.experiences = [];
    this.candidate.user = <IUser>{};

    if(this.isCandidate()){
      this.candidate.user = <IUser>{id: this.userSession.id};
      this.idCandidate = this.userSession.candidate ? this.userSession.candidate.id : ''
    }
    else{
      this.idCandidate = this.activatedRoute.snapshot.queryParamMap.get('id')!

      this.getUsersCandidate();

      this.userCandidateForm.valueChanges.subscribe(response =>{
        this.filterData(response);
      })
    }

    if(this.idCandidate){
      this.candidateService.getCandidate(this.idCandidate).subscribe({
        next: (result) =>{
          if(result){
            this.candidate = result;

            this.candidate.experiences = parseExperienceDatesToInput(
              this.candidate.experiences
            );
            
            this.candidateFormGroup.patchValue(this.candidate);

            if(this.candidate.user && this.isEmployer()){
              this.userCandidateForm.setValue(this.candidate.user);
            }
          }
          else{
            if(this.isCandidate()){
              this.candidate.id = this.idCandidate;
            }
            // else{
            //   this.router.navigateByUrl("/error");
            // }
          }
          
        },
        error: () =>{
          // this.router.navigateByUrl("/error");
        }
      })
    }

  }

  addExperienceButton() {
    this.toggleIsEditingExperience();
    this.isAddingNewExperience = true;
  }

  // SNACKBAR METHODS //
  showSnackBar(msg: string) {
    this.snackBar
      .open(msg, 'Aceptar', {
        horizontalPosition: 'center',
        verticalPosition: 'bottom',
        duration: 5 * SECONDS,
      })
      .onAction()
      .subscribe(() => {
        this.snackBar.dismiss();
      });
  }

  toggleIsEditingExperience() {
    this.isEditingExperience = !this.isEditingExperience;
  }

  addExperience(experience: IExperience) {
    experience = parseExperience(experience);
    this.candidate.experiences.push(experience);
    this.toggleIsEditingExperience();
    this.isAddingNewExperience = false;
  }

  saveCandidate() {
    if(this.candidateFormGroup.valid){
      if(this.isEmployer() && !this.assigned){
        this.invalidForms();

        return;
      }

      this.candidate = {
        ...this.candidate,
        ...this.candidateFormGroup.value,
      };
  
      let candidate = parseCandidate(this.candidate);
  
      this.candidateService.createCandidate(candidate).subscribe({
        next: (response) =>{
          if(this.isCandidate()){
            this.userSession.candidate = response;
            this.sessionService.setUserSessionData({
              ...this.userSession
            });
          }
          
          this.router.navigate(['/candidates/form/success']);
          this.resetForms();
        },
        error: () =>{
          this.showSnackBar('Algo ha fallado. No se ha podido guardar el candidato.');
        }
      });
      
    }
    else{
      this.invalidForms();
    }
    
  }

  deleteExperience(experience: IExperience, experienceIndex: number) {
    const modal = this.modal.open(DeleteExperienceModalComponent, {
      autoFocus: false,
    });

    modal.afterClosed().subscribe((result) =>{
      if(result){
        this.candidate.experiences.splice(experienceIndex, 1);

        if (experience.id) {
          this.experienceService.deleteById(experience.id).subscribe({
            next: () => this.showSnackBar('La experiencia se ha eliminado correctamente.'),
            error: () => this.showSnackBar('Algo ha fallado. No se ha podido eliminar la experiencia.'),
          });
        }
      }
    });
  }

  openEditExperience(experience: IExperience, index: number) {
    this.experienceToEdit = experience;
    this.toggleIsEditingExperience();
    this.selectedExperienceIndex = index;
  }

  updateExperience(experience: IExperience) {
    if (experience.id) {
      // Find the experience in the array and update it with the new values from the form
      for (let i = 0; i < this.candidate.experiences.length; i++) {
        if (this.candidate.experiences[i].id === experience.id) {
          this.candidate.experiences[i] = experience;
        }
      }
    } else {
      this.candidate.experiences[this.selectedExperienceIndex] = experience;
    }
    this.toggleIsEditingExperience();
  }

  // FORM METHODS //
  editCandidate() {
    if(this.candidateFormGroup.valid){
      if(this.isEmployer() && !this.assigned){
        this.invalidForms();

        return;
      }

      this.candidate = {
        ...this.candidate,
        ...this.candidateFormGroup.value,
      };
  
      let candidate = parseCandidate(this.candidate);
  
      this.candidateService.editCandidate(candidate).subscribe({
        next: () => {
          if(this.sessionService.getUserSessionData().role == Role.CANDIDATE){
            this.router.navigateByUrl("/candidates/form/success")
          }
          else{
            this.showSnackBar('El candidato se ha editado correctamente.');
            this.router.navigateByUrl('/candidates/list')
          }
        },
        error: () => {
          this.showSnackBar('Algo ha fallado. No se ha podido editar al candidato.');
        }
      });
    }
    else{
      this.invalidForms();
    }
    
  }

  resetForms() {
    //Prevent null values after reset
    this.candidateFormGroup.reset('');

    this.candidate = {
      id: '',
      firstName: '',
      lastName: '',
      birthDate: new Date(''),
      experiences: [],
      observation: ''
    };
  }

  onGetDataError() {
    this.onError.emit();
  }

  onCompleteExperience(){
    this.toggleIsEditingExperience();
    this.experienceToEdit = <IExperience>{};
    this.isAddingNewExperience = false;
  }

  goToErrorTab(){
    let selectedIndex = this.matTabGroup.selectedIndex;
    let errorsFirstTab = this.searchFirstTabErrors();
    let errorsThirdTab = this.searchThirdTabErrors();
    let errorsLastTab = this.searchLastTabErrors();
    
    if(errorsFirstTab){
      if(selectedIndex != 0){
        selectedIndex = 0;
      }
    }
    else if(errorsThirdTab){
      if(selectedIndex != 2){
        selectedIndex = 2;
      }
    }
    else{
      if(this.isEmployer() && errorsLastTab){
        if(selectedIndex != 3){
          selectedIndex = 3;
        }
      }
    } 

    this.matTabGroup.selectedIndex = selectedIndex;
  }

  private searchFirstTabErrors(){
    if(this.candidateFormGroup.controls['firstName'].invalid || this.candidateFormGroup.controls['lastName'].invalid
      || this.candidateFormGroup.controls['birthDate'].invalid){

        return true;
      }

    return false;
  }

  private searchThirdTabErrors(){
    if(this.candidateFormGroup.controls['observation'].invalid){
      return true;
    }

    return false;
  }

  private searchLastTabErrors(){
    if(this.userCandidateForm.invalid){
      return true;
    }

    return false;
  }

  private invalidForms(){
    this.candidateFormGroup.markAllAsTouched();
    if(this.isEmployer()){
      this.userCandidateForm.markAllAsTouched();
      this.userCandidateForm.setErrors({required: true});
    }

    this.goToErrorTab();
  }

  isEmployer(){
    return this.userSession.role == Role.EMPLOYER;
  }

  isCandidate(){
    return this.userSession.role == Role.CANDIDATE;
  }

  getUsersCandidate(){
    this.userService.getListUserCandidate().subscribe({
      next: (response) => {
       if(response){ this.listUserCandidate = response;
        this.filteredUserOptions = response.filter(item => !item.candidate)
       }
      }
    });
  }

  displayFullName(user: IUser) : string{
    return user && user.fullName ? user.fullName : '';
  }

  selectedOption(event: MatAutocompleteSelectedEvent){
    if(event.option.value == this.noUser){
      if(this.candidate.user){
        delete this.candidate.user;
      }
    }
    else{
      if(!this.candidate.user){
        this.candidate.user = <IUser>{};
      }
     
      this.candidate.user!.id = event.option.value.id;
    }

    this.assigned = true;
  }

  private filterData(data : any){
    if(data instanceof Object){
      return;
    }

    let dataString = data;
    
    this.assigned = false;

    if(dataString == ""){
      if(this.candidate.id){
        this.filteredUserOptions = this.listUserCandidate.filter(item => !item.candidate || this.candidate.id == item.candidate.id)
      }
      else{
        this.filteredUserOptions = this.listUserCandidate.filter(item => !item.candidate)
      }  
    }
    else{
      if(this.candidate.id){
        this.filteredUserOptions = this.listUserCandidate.filter(item =>{
          if(!item.candidate){
            return item.fullName!.toLowerCase().indexOf(dataString.toLowerCase()) > -1
          }
          else{
            return item.candidate.id == this.candidate.id && item.fullName!.toLowerCase().indexOf(dataString.toLowerCase()) > -1;
          }
        });
      }
      else{
        this.filteredUserOptions = this.listUserCandidate.filter(item =>{
          return !item.candidate && item.fullName!.toLowerCase().indexOf(dataString.toLowerCase()) > -1
        });
      }
    }
  }

  //Form errors
  getFirstNameErrorMessage(){
    let firstName = this.candidateFormGroup.controls['firstName'];

    if(firstName.hasError('required')){
      return "El nombre no puede estar vacío.";
    }
    else if(firstName.hasError('minlength')){
      return "El nombre no puede ser inferior a 3 caracteres."
    }
    else if(firstName.hasError('maxlength')){
      return "El nombre no puede ser superior a 40 caracteres."
    }

    return "El nombre solo puede contener caracteres alfabéticos."
  }

  getLastNameErrorMessage(){
    let lastName = this.candidateFormGroup.controls['lastName'];

    if(lastName.hasError('required')){
      return "Los apellidos no pueden estar vacíos.";
    }
    else if(lastName.hasError('minlength')){
      return "Los apellidos no pueden ser inferiores a 3 caracteres."
    }
    else if(lastName.hasError('maxlength')){
      return "Los apellidos no pueden ser superiores a 40 caracteres."
    }

    return "Los apellidos solo pueden contener caracteres alfabéticos."
  }

  getBirthDateErrorMessage(){
    let birthDate = this.candidateFormGroup.controls['birthDate'];

    if(birthDate.hasError('required')){
      return "La fecha de nacimiento no puede estar vacía.";
    }
    else if(birthDate.hasError('matDatepickerParse')){
      return "La fecha de nacimiento debe tener el formato DD/MM/YYYY."
    }
    else if(birthDate.hasError('matDatepickerMin')){
      return `La fecha de nacimiento no puede ser anterior a ${this.minDate.year()}.`
    }

    return "La fecha de nacimiento no puede ser futura."
  }

  getObservationsErrorMessage(){
    let observation = this.candidateFormGroup.controls['observation'];

    if(observation.hasError('maxlength')){
      return "Las observaciones no pueden ser superiores a 300 caracteres.";
    }

    return "Las observaciones deben tener más de 3 caracteres.";
  }

}
