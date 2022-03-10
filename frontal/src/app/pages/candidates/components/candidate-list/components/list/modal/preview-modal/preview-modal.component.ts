import { Component, Inject, OnDestroy, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Subscription } from 'rxjs';
import { ICandidate } from 'src/app/core/model/candidate/candidate.interface';
import { CandidateService } from 'src/app/core/services/candidate/candidate.service';

@Component({
  selector: 'app-preview-modal',
  templateUrl: './preview-modal.component.html',
  styleUrls: ['./preview-modal.component.css']
})
export class PreviewModalComponent implements OnInit, OnDestroy {

  candidate!: ICandidate;

  subscription!: Subscription;

  constructor(
    private candidateService: CandidateService,
    private modalRef: MatDialogRef<PreviewModalComponent>,
     @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit(): void {
    this.candidate = <ICandidate>{};
    this.subscription = this.candidateService.getCandidate(this.data).subscribe({
      next: (result) =>{
        this.candidate = result;
      },
      error: () =>{
        this.onClose(true);
      }
    });
  }

  ngOnDestroy(): void {
    if(this.subscription){
      this.subscription.unsubscribe();
    }
  }

  onAction(){
    this.modalRef.close({result: true});
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
