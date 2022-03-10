import { animate, style, transition, trigger } from '@angular/animations';
import { SelectionModel } from '@angular/cdk/collections';
import { AfterViewInit, Component, OnDestroy, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Subscription } from 'rxjs';
import { ICandidate } from 'src/app/core/model/candidate/candidate.interface';
import { ICandidateFilters } from 'src/app/core/model/candidate/candidateFilters.interface';
import { Pagination } from 'src/app/core/model/pagination/pagination.interface';
import { CandidateService } from 'src/app/core/services/candidate/candidate.service';

@Component({
  selector: 'app-candidate-list',
  templateUrl: './candidate-list.component.html',
  styleUrls: ['./candidate-list.component.css'],
  animations: [
    trigger('fadeInOut', [
      transition(':enter', [   // :enter is alias to 'void => *'
        style({opacity:0}),
        animate(500, style({opacity:1})) 
      ]),
      transition(':leave', [   // :leave is alias to '* => void'
        animate(500, style({opacity:0})) 
      ])
    ])
  ]
})
export class CandidateListComponent implements OnInit, AfterViewInit, OnDestroy{
  loading: boolean = true;

  error: boolean = false;

  isDeletingAll: boolean = false;

  isFiltering!: boolean;
  
  candidateFilters!: ICandidateFilters;

  candidatesSort!: Sort;

  pagination!: Pagination;

  candidatesDataSource: MatTableDataSource<ICandidate> = new MatTableDataSource<ICandidate>();

  selection!: SelectionModel<string>;

  subscribe!: Subscription;

  constructor(
    private candidateService: CandidateService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {}

  ngAfterViewInit(): void {
    this.getCandidates();
  }

  ngOnDestroy(): void {
    this.subscribe.unsubscribe();
    this.snackBar.dismiss();
  }

  /** Shows the snackBar in the center-top when there is an error getting the list  */
  showListErrorSnackBar() {
    this.snackBar
      .open('No se ha podido recuperar la lista de candidatos.', 'Reintentar', {
        horizontalPosition: 'center',
        verticalPosition: 'bottom',
      })
      .onAction()
      .subscribe(() => {
        this.snackBar.dismiss();
        this.getCandidates();
      });
  }

  /** Shows the snackBar in the center-top after action result or when candidate list is empty */
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

  getCandidates() {
    this.loading = true;

    let { pageIndex, pageSize } = this.pagination;

    if (this.isFiltering || this.isDeletingAll) {
      pageIndex = 0;
    }

    this.subscribe = this.candidateService
      .getCandidatesPage(
        pageIndex,
        pageSize,
        this.candidateFilters,
        this.candidatesSort
      )
      .subscribe({
        next: (candidatesPage) => {
          this.candidatesDataSource.data = candidatesPage.content;
          this.pagination.totalElements = candidatesPage.totalElements;
          this.pagination.pageIndex = pageIndex;

          this.loading = false;
          this.error = false;

          if (this.isFiltering) {
            this.isFiltering = false;
          }
        },
        error: () => {
          this.loading = false;
          this.error = true;
          this.showListErrorSnackBar();
        },
    });
  }
  
  /** Get initialization from table */
  initTableData(initData: any){
    this.candidatesSort = initData.candidatesSort;
    this.pagination = initData.pagination;
    this.selection = initData.selection;
  }

  /** Get initialization from filters */
  initFiltersData(initData: any){
    this.candidateFilters = initData.candidateFilters;
    this.isFiltering = initData.isFiltering;
  }

  /** Filter when the button is pressed */
  getFilters(filterData : any){
    this.candidateFilters = filterData.candidateFilters;
    this.isFiltering = filterData.isFiltering;
    this.selection.clear();
    this.getCandidates();
  }

  /** Get isDeletingAll from table */
  setIsDeletingAll(isDeletingAll : boolean){
    this.isDeletingAll = isDeletingAll;
  }

}

