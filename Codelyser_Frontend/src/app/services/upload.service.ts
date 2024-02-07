import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HttpEvent } from '@angular/common/http';
@Injectable({
  providedIn: 'root'
})
export class UploadService {
  private apiUrl ='http://localhost:8080/upload'; // Update with your Spring Boot backend URL

  constructor(private http: HttpClient) {}

  uploadFile(file: File, questionname: string): Observable<any> {
    const formData: FormData = new FormData();
    console.log(questionname);
    formData.append('file', file);
    formData.append('questionname', questionname.toString());
    return this.http.post(this.apiUrl, formData);
  }
}
