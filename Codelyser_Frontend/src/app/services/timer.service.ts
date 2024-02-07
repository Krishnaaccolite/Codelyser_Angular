import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class TimerService {

  private apiUrl = 'http://localhost:8080/timer';

  constructor(private http: HttpClient) {}

  updateTimer(timerValue: number,userId:number): Observable<any> {
    return this.http.post(`${this.apiUrl}/update/${userId}`, { timerValue });
  }
  
  getTimer(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/get`);
  }
}
