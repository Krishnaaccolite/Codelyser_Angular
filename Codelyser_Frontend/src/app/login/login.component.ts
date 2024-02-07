import { Component, importProvidersFrom } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../services/login.service';
import { UserIDService } from '../services/user-id.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  userEmail!: string;
  flagloading:boolean=false;
  constructor(private router: Router, private loginService: LoginService,private userIdService:UserIDService) {
  }

  login(): void {
    this.flagloading=true;
    const user = { userEmail: this.userEmail };
    this.loginService.addUser(user).subscribe(
        (response) => {
        const userId = response.userId;
        console.log(`User added successfully with userId: ${userId}`);
        this.userIdService.setUserId(userId);
        this.loginService.cloneFile(userId).subscribe(
          (cloneResponse) => {
            console.log(`File cloned successfully: ${cloneResponse}`);
            // Additional handling for cloned file success
          },
          (cloneError) => {
            console.error('Failed to clone file:', cloneError);
            this.router.navigate(['/user-test',userId]);
            // Handle the error for cloning the file
          }
          );
      },
      (error) => {
        console.error('Failed to add user:', error);
      }
    );
  }
}
