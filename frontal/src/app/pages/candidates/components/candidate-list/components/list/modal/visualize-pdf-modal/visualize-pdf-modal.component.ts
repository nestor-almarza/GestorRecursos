import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CandidateService } from 'src/app/core/services/candidate/candidate.service';

@Component({
  selector: 'app-visualize-pdf-modal',
  templateUrl: './visualize-pdf-modal.component.html',
  styleUrls: ['./visualize-pdf-modal.component.css']
})
export class VisualizePDFModalComponent implements OnInit {

  constructor(
    private candidateService: CandidateService, 
    private modalRef: MatDialogRef<VisualizePDFModalComponent>,
    private snackBar: MatSnackBar,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

     ngOnInit(): void {}
   
  onAction(format: string){

    if(format === "visualMint" ){
      this.candidateService.visualizeCandidateMint(this.data.data.id!).subscribe({
        next: (data: Blob) => {
          let file = new Blob([data], { type: 'application/pdf' });
          let fileURL = URL.createObjectURL(file);
  
          window.open(fileURL);
          let a = document.createElement('a');
          a.href = fileURL;
          a.target = '_blank';
        },
        error: () => {
          this.showSnackBar(
            'Ha ocurrido un error y no se ha podido visualizar la ficha del candidato.'
          );
        },
      });
    };

    if(format == "visualManjaro" ){
        this.candidateService.visualizeCandidateManjaro(this.data.data.id!).subscribe({
          next: (data: Blob) => {
            let file = new Blob([data], { type: 'application/pdf' });
            let fileURL = URL.createObjectURL(file);
    
            window.open(fileURL);
            let a = document.createElement('a');
            a.href = fileURL;
            a.target = '_blank';
          },
          error: () => {
            this.showSnackBar(
              'Ha ocurrido un error y no se ha podido visualizar la ficha del candidato.'
            );
          },
        });
      }

      if(format == "downloadMint" ){
        this.candidateService.visualizeCandidateMint(this.data.data.id!).subscribe({
          next: (data: Blob) => {
            let file = new Blob([data], { type: 'application/pdf' });
            let fileURL = URL.createObjectURL(file);
    
            let a = document.createElement('a');
            a.href = fileURL;
            a.target = '_blank';
            a.download = `${this.data.data.lastName}-${this.data.data.firstName}.pdf`;
            document.body.appendChild(a);
            a.click();
          },
          error: () => {
            this.showSnackBar(
              'Ha ocurrido un error y no se ha podido descargar la ficha del candidato.'
            );
          },
        });
      }

      if(format == "downloadManjaro" ){
        this.candidateService.visualizeCandidateManjaro(this.data.data.id!).subscribe({
          next: (data: Blob) => {
            let file = new Blob([data], { type: 'application/pdf' });
            let fileURL = URL.createObjectURL(file);
    
            let a = document.createElement('a');
            a.href = fileURL;
            a.target = '_blank';
            a.download = `${this.data.data.lastName}-${this.data.data.firstName}.pdf`;
            document.body.appendChild(a);
            a.click();
          },
          error: () => {
            this.showSnackBar(
              'Ha ocurrido un error y no se ha podido descargar la ficha del candidato.'
            );
          },
        });
      }

    this.modalRef.close(true);
  }

  onClose(){
    this.modalRef.close(false);
  }

  showSnackBar(msg: string) {
    this.snackBar
      .open(msg, 'Aceptar', {
        horizontalPosition: 'center',
        verticalPosition: 'bottom',
        duration: 5000,
      })
      .onAction()
      .subscribe(() => {
        this.snackBar.dismiss();
      });
  }


}
