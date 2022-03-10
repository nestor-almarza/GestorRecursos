import { Component, Inject, OnDestroy, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Subscription } from 'rxjs';
import { ICandidate } from 'src/app/core/model/candidate/candidate.interface';
import { IUser } from 'src/app/core/model/user/user.interface';
import { CandidateService } from 'src/app/core/services/candidate/candidate.service';
import { UserService } from 'src/app/core/services/user/user.service';

@Component({
  selector: 'app-assign-user-modal',
  templateUrl: './assign-user-modal.component.html',
  styleUrls: ['./assign-user-modal.component.css']
})
export class AssignUserModalComponent implements OnInit, OnDestroy {

  userCandidateForm = new FormControl('', [Validators.required]);

  listUserCandidate: IUser[] = [];

  filteredUserOptions: IUser[] = [];

  noUser = {id: '', fullName: 'Nadie'};

  candidate!: ICandidate;

  subscription!: Subscription;

  assigned: boolean = false;

  constructor(
    private userService: UserService,
    private candidateService: CandidateService,
    @Inject(MAT_DIALOG_DATA) public data: string,
    private modalRef: MatDialogRef<AssignUserModalComponent>) { }

  ngOnInit(): void {
    this.candidate = <ICandidate>{};
    this.subscription = this.candidateService.getCandidate(this.data).subscribe({
      next: (result) =>{
        this.candidate = result;
        if(this.candidate.user){
          this.userCandidateForm.setValue(this.candidate.user);
        }
      },
      error: () =>{
        this.onClose(true);
      }
    });

    this.getUsersCandidate();

    this.userCandidateForm.valueChanges.subscribe(response =>{
      this.filterData(response);
    });
  }

  ngOnDestroy(): void {
    if(this.subscription){
      this.subscription.unsubscribe();
    }
  }

  getUsersCandidate(){
    this.userService.getListUserCandidate().subscribe({
      next: (response) => {
        if(response){
          this.listUserCandidate = response;
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

  onAction(){
    if(this.userCandidateForm.valid && this.assigned){
      this.modalRef.close({result: true, data: this.candidate});
    }
    else{
      this.userCandidateForm.markAllAsTouched();
      this.userCandidateForm.setErrors({required: true});
    }
    
  }

  onClose(error?: boolean){
    if(!error){
      this.modalRef.close({result: false});
    }
    else{
      this.modalRef.close({result:false, error: true});
    }
    
  }
}
