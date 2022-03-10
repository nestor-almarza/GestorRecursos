import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-delete-experience-modal',
  templateUrl: './delete-experience-modal.component.html',
  styleUrls: ['./delete-experience-modal.component.css']
})
export class DeleteExperienceModalComponent implements OnInit {

  constructor(
    private modalRef: MatDialogRef<DeleteExperienceModalComponent>,
     @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit(): void {}

  onAction(){
    this.modalRef.close(true);
  }

  onClose(){
    this.modalRef.close(false);
  }

}