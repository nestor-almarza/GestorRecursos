import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-delete-modal',
  templateUrl: './delete-modal.component.html',
  styleUrls: ['./delete-modal.component.css']
})
export class DeleteModalComponent implements OnInit {
  
  constructor(
    private modalRef: MatDialogRef<DeleteModalComponent>,
     @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit(): void {}

  onAction(){
    this.modalRef.close(true);
  }

  onClose(){
    this.modalRef.close(false);
  }

}
