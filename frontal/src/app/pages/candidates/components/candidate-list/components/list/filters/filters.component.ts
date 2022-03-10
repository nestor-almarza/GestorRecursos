import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MAT_MOMENT_DATE_ADAPTER_OPTIONS, MAT_MOMENT_DATE_FORMATS, MomentDateAdapter } from '@angular/material-moment-adapter';
import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material/core';
import { ICandidateFilters } from 'src/app/core/model/candidate/candidateFilters.interface';

@Component({
  selector: 'app-filters',
  templateUrl: './filters.component.html',
  styleUrls: ['./filters.component.css'],
  providers: [
    {
      provide: DateAdapter,
      useClass: MomentDateAdapter,
      deps: [MAT_DATE_LOCALE, MAT_MOMENT_DATE_ADAPTER_OPTIONS],
    },
    { provide: MAT_MOMENT_DATE_ADAPTER_OPTIONS, useValue: { strict: false } },
    { provide: MAT_DATE_FORMATS, useValue: MAT_MOMENT_DATE_FORMATS },
    { provide: MAT_DATE_LOCALE, useValue: 'es' }
  ],
})
export class FiltersComponent implements OnInit {

  @Input()
  error!: boolean;

  isFiltering: boolean = false;

  @Output()
  onFilter: EventEmitter<any> = new EventEmitter<any>();

  @Output()
  getInitData: EventEmitter<any> = new EventEmitter<any>();

  candidateFilters : ICandidateFilters = {
    partname: "",
    updatedAtStartRange: null,
    updatedAtEndRange: null
  };

  candidateFiltersFormGroup = new FormGroup({
    partname: new FormControl(""),
    updatedAtStartRange: new FormControl(""),
    updatedAtEndRange: new FormControl("")
  });
  
  constructor() {}

  ngOnInit(): void {
    this.sendInitData();
  }

  /** Send initialization data to parent */
  sendInitData(){
    this.getInitData.emit({
      isFiltering: this.isFiltering,
      candidateFilters: this.candidateFilters
    })
  }

  /** Filter function */
  searchWithFilters(){
    if(this.candidateFiltersFormGroup.valid){
      this.candidateFilters = {
        partname: "",
        updatedAtStartRange: null,
        updatedAtEndRange: null,
      };

      this.candidateFilters = {
        ...this.candidateFiltersFormGroup.value
      };

      this.isFiltering = true;
      this.sendFilterData();
    }
  }

  /** Reset filters */
  clearFilters() {
    this.candidateFilters = {
      partname: "",
      updatedAtStartRange: null,
      updatedAtEndRange: null,
    };

    this.candidateFiltersFormGroup.reset("");

    this.resetFilterData();
  }

  /** Disable reset button if filters are empty and isFiltering is false */
  isEmptyForClear(){
    return this.checkFields() && !this.isFiltering;
  }

  /** Disable search button if filters are empty or if there is an error */
  isEmptyForSearch(){
    return this.checkFields() || (this.candidateFiltersFormGroup.invalid || this.error);
  }

  /** Check if any field has a value */
  private checkFields(){
    return Object.keys(this.candidateFiltersFormGroup.controls).every(key =>{
      return (this.candidateFiltersFormGroup.controls[key].value === "" || 
      this.candidateFiltersFormGroup.controls[key].value === null)
    });
  }

  /** Send filter data to parent (list) */
  private sendFilterData(){
    this.onFilter.emit({
      candidateFilters: this.candidateFilters,
      isFiltering: this.isFiltering
    });
  }

  /** Send filter data to parent (list) after reset fields */
  private resetFilterData(){
    if(this.isFiltering){
      this.isFiltering = false;
      this.sendFilterData();
    }
  }
}
