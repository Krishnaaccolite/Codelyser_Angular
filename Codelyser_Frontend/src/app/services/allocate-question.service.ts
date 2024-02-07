import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class AllocateQuestionService {
  private apiUrl = 'http://localhost:8080/api/users'; // Update with your backend base URL
  constructor(private http: HttpClient) { }
  assignQuestionToUser(userId: number, questionId: number): Observable<any> {
    const url = `${this.apiUrl}/${userId}/assign-question/${questionId}`;
    return this.http.post(url, null);
  }
}