import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BehaviorSubject } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class CandidatefileexplorerService {
  private baseUrl = 'http://localhost:8080'; // Update this with your server URL
  private selectedNodeNameSource = new BehaviorSubject<string>('');
  selectedNodeName$ = this.selectedNodeNameSource.asObservable();


  constructor(private http: HttpClient) {}

  getProjectStructure(userId: string): Observable<any> {
    const url = `${this.baseUrl}/candidate/project-structure/${userId}`;
    console.log(url);
    return this.http.get(url);
  }
  getFileContent(filePath: string,userId: string|null): Observable<any> {
    const requestData = { filepath: filePath };
    console.log(filePath);
    return this.http.post<any>(`${this.baseUrl}/candidate/project-structure/${userId}/explorer/file-content`, requestData);
  }
  getProjectName(userId: string|null): Observable<any> {
    const url = `${this.baseUrl}/candidate/project-structure/${userId}/user`;
    return this.http.get(url, { responseType: 'text' });
}
  updateSelectedNodeName(name: string): void {
    this.selectedNodeNameSource.next(name);
  }
}
