import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Sort } from '@angular/material/sort';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ICandidate } from '../../model/candidate/candidate.interface';
import { ICandidateFilters } from '../../model/candidate/candidateFilters.interface';

@Injectable({
  providedIn: 'root',
})
export class CandidateService {
  private apiUrl = environment.apiUrl + '/candidate';

  constructor(private httpClient: HttpClient) {}

  /** Create a candidate */
  createCandidate(candidate: ICandidate): Observable<ICandidate> {
    return this.httpClient.post<ICandidate>(`${this.apiUrl}/new`, candidate, {withCredentials:true});
  }

  /** Get one candidate */
  getCandidate(candidateId: string): Observable<ICandidate> {
    return this.httpClient.get<ICandidate>(`${this.apiUrl}/${candidateId}`, {withCredentials:true});
  }

  /** Get all candidates */
  getCandidates(): Observable<ICandidate[]> {
    return this.httpClient.get<ICandidate[]>(`${this.apiUrl}/list`, {
      withCredentials: true
    });
  }

  /** Get paginated list of candidates */
  getCandidatesPage(
    page: number,
    size: number,
    filter: ICandidateFilters,
    sort: Sort
  ): Observable<any> {
    return this.httpClient.post<any>(
      `${this.apiUrl}/?page=${page}&size=${size}&sortBy=${sort.active}&direction=${sort.direction}`,
      filter,
      { withCredentials: true }
    );
  }

  /** Delete one candidate physically  */
  deleteCandidate(candidateId: string): Observable<any> {
    return this.httpClient.delete<any>(`${this.apiUrl}/${candidateId}`, {withCredentials:true});
  }

  /** Edit one candidate */
  editCandidate(candidate: ICandidate): Observable<ICandidate> {
    return this.httpClient.put<ICandidate>(
      `${this.apiUrl}/${candidate.id}`,
      candidate,
      {withCredentials:true}
    );
  }

  /** Delete multiple candidates physically */
  deleteMultipleCandidates(listCandidateIds: string[]): Observable<any> {
    return this.httpClient.delete<any>(`${this.apiUrl}/list`, {
      body: listCandidateIds,
      withCredentials: true,
    });
  }

  /** Visualize one candidate Mint */
  visualizeCandidateMint(candidateId: string): Observable<Blob> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      responseType: 'blob',
    });

    return this.httpClient.get<Blob>(`${this.apiUrl}/mint/${candidateId}`, {
      headers: headers,
      responseType: 'blob' as 'json',
      withCredentials: true,
    });
  }

    /** Visualize one candidate Manjaro */
    visualizeCandidateManjaro(candidateId: string): Observable<Blob> {
      const headers = new HttpHeaders({
        'Content-Type': 'application/json',
        responseType: 'blob',
      });
  
      return this.httpClient.get<Blob>(`${this.apiUrl}/manjaro/${candidateId}`, {
        headers: headers,
        responseType: 'blob' as 'json',
        withCredentials: true,
      });
    }

}
