import { SelectionModel } from '@angular/cdk/collections';
import { Component, EventEmitter, Input,OnInit, Output, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { ICandidate } from 'src/app/core/model/candidate/candidate.interface';
import { Pagination } from 'src/app/core/model/pagination/pagination.interface';
import { CandidateService } from 'src/app/core/services/candidate/candidate.service';
import { AssignUserModalComponent } from '../modal/assign-user-modal/assign-user-modal.component';
import { DeleteModalComponent } from '../modal/delete-modal/delete-modal.component';
import { PreviewModalComponent } from '../modal/preview-modal/preview-modal.component';
import { VisualizePDFModalComponent } from '../modal/visualize-pdf-modal/visualize-pdf-modal.component';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.css'],
})
export class TableComponent implements OnInit {

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  @Input()
  candidatesDataSource!: MatTableDataSource<ICandidate>;

  displayedColumns: string[] = [
    'select',
    'lastName',
    'firstName',
    'updatedAt',
    'actions',
  ];
  selection: SelectionModel<string> = new SelectionModel<string>(true, []);

  pagination: Pagination = {
    totalElements: 0,
    pageIndex: 0,
    pageSizeOptions: [10, 20, 50],
    pageSize: 10,
  };

  candidatesSort: Sort = {
    active: 'lastName',
    direction: 'asc',
  };

  @Output()
  getCandidates: EventEmitter<any> = new EventEmitter<any>();

  @Output()
  showSnackBar: EventEmitter<string> = new EventEmitter<string>();

  @Output()
  getInitData: EventEmitter<any> = new EventEmitter<any>();

  @Output()
  getIsDeletingAll: EventEmitter<boolean> = new EventEmitter<boolean>();

  constructor(
    private candidateService: CandidateService,
    private modal: MatDialog,
    private router: Router
  ) {}
  
  ngOnInit(): void {
    this.getInitData.emit({
      candidatesSort: this.candidatesSort,
      pagination: this.pagination,
      selection: this.selection
    });
  }

  /** Pagination page change */
  onPageChange(event: PageEvent){
    this.pagination.pageSize = event.pageSize;
    this.pagination.pageIndex = event.pageIndex;
    this.getCandidates.emit();
  }

  /** Whether the number of selected elements matches the total number of rows. */
  isAllSelected() {
    return this.candidatesDataSource.data.every((row) =>
      this.selection.isSelected(row.id!)
    );
  }

  /** Selects all rows if they are not all selected; otherwise clear selection. */
  masterToggle() {
    if (this.isAllSelected()) {
      this.selection.clear();
    } else {
      this.candidatesDataSource.data.forEach((row) =>
        this.selection.select(row.id!)
      );
    }
  }

  /** Edit candidate */
  editCandidate(candidateId: string) {
    const modal = this.modal.open(PreviewModalComponent, {
      data: candidateId,
      autoFocus: false,
    });

    modal.afterClosed().subscribe((result) => {
      //Result can be undefined when user clicks outside the modal
      if(result){
        if(result.result){
          this.router.navigateByUrl(`/candidates/form?id=${candidateId}`);
        }
        else{
          if(result.error){
            this.showSnackBar.emit("Ha ocurrido un error al obtener los datos del candidato.");
          }
        }
      }
    });
  }

  /** Delete candidate */
  deleteCandidate(candidate: ICandidate) {
    let fullname = candidate.firstName + ' ' + candidate.lastName;

    const modal = this.modal.open(DeleteModalComponent, {
      data: {
        data: fullname,
        action: 'delete',
      },
      autoFocus: false,
    });

    modal.afterClosed().subscribe((result) => {
      if (result) {
        this.candidateService.deleteCandidate(candidate.id!).subscribe(
          {
          next: () => {
            this.showSnackBar.emit('Candidato eliminado correctamente.');
          },
          error: () => {
            this.showSnackBar.emit(
              'Ha ocurrido un error y no se ha podido eliminar al candidato.'
            );
          },
          complete: () => {
            this.getCandidates.emit();
            if(this.selection.isSelected(candidate.id!)){
              this.selection.deselect(candidate.id!);
            }
          },
        });
      }
    });
  }

  /** Delete multiple candidates */
  deleteMultipleCandidates() {
    let listCandidateId : string[] = this.selection.selected.map((candidateId) => {
      return candidateId!;
    });

    const modal = this.modal.open(DeleteModalComponent, {
      data: {
        action: 'multiple',
      },
      autoFocus: false,
    });

    modal.afterClosed().subscribe((result) => {
      if (result) {
        this.getIsDeletingAll.emit(true);
        this.candidateService
          .deleteMultipleCandidates(listCandidateId)
          .subscribe({
            next: () => {
              this.showSnackBar.emit('Candidatos eliminados correctamente.');
            },
            error: () => {
              this.showSnackBar.emit(
                'Ha ocurrido un error y no se han podido eliminar los candidatos seleccionados.'
              );
              this.getIsDeletingAll.emit(false);
            },
            complete: () => {
              this.getCandidates.emit();
              this.selection.clear();
              this.getIsDeletingAll.emit(false);
            },
          });
      }
    });
  }

  /** Assign user to candidate */
  assignUser(candidateId: string){
    const modal = this.modal.open(AssignUserModalComponent, {
      data: candidateId,
      autoFocus: false,
      width: "450px"
    });

    modal.afterClosed().subscribe((result) =>{
      if(result){
        if(result.result){
          this.candidateService.editCandidate(result.data).subscribe({
            next: () =>{
              this.showSnackBar.emit("Se ha asignado la candidatura al usuario correctamente.");
              this.getCandidates.emit();
            },
            error: () =>{
              this.showSnackBar.emit("Ha ocurrido un error al asignar al candidato.");
            }
          })
        }
        else{
          if(result.error){
            this.showSnackBar.emit("Ha ocurrido un error al obtener los datos del candidato.");
          }
        }
      }
    })
  }

  /** VISUALIZE candidate */
  visualizeCandidate(candidate: ICandidate) {

    const modal = this.modal.open(VisualizePDFModalComponent, {
    data:{
      action: 'visual',
      data: candidate
    } ,
    autoFocus: false,
    width: "450px"
    });

    modal.afterClosed().subscribe((result) => result);
  }

  /** DOWNLOAD candidate */
  downloadCandidate(candidate: ICandidate) {

    const modal = this.modal.open(VisualizePDFModalComponent, {
    data:{
      action: 'download',
      data: candidate
    } ,
    autoFocus: false,
    width: "450px"
    });

    modal.afterClosed().subscribe((result) => result);
  }

  addSort(sort: Sort) {
    this.candidatesSort.direction = sort.direction;
    this.candidatesSort.active = sort.active;
    this.getCandidates.emit();
  }

}

