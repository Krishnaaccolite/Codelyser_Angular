import { Injectable} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserIDService } from './user-id.service';
@Injectable({
  providedIn: 'root'
})
export class LoginService  {
  private apiUrl = 'http://localhost:8080/api/users'; // Update with your API URL

  constructor(private httpClient: HttpClient) { }

  addUser(user: any): Observable<any> {
    return this.httpClient.post(`${this.apiUrl}/add`, user);
  }
  cloneFile(userId: number): Observable<string> {
    return this.httpClient.get<string>(`${this.apiUrl}/${userId}/makeclone`);
    // Replace 'some-custom-endpoint' with the actual endpoint you want to use for fetching data based on user ID.
  }
}
