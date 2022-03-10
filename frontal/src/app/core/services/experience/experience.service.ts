import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ExperienceService {
  private apiUrl = environment.apiUrl + '/experience';

  constructor(private httpClient: HttpClient) {}

  deleteById(experienceId: string) {
    return this.httpClient.delete(`${this.apiUrl}/${experienceId}`,
    {withCredentials:true}
    );
  }
}
