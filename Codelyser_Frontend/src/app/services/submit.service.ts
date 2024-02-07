import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class SubmitService {
  private apiUrl = 'http://localhost:8080/api'; 

  constructor(private http: HttpClient) {}

  submitProject(userId: number): Observable<any> {
    const url = `${this.apiUrl}/submit/${userId}`;
    return this.http.post(url, {});
  }

}
