import { Component } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { FeedbackService } from './services/feedback.service';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  constructor(private router: Router,public feedbackService: FeedbackService) {}
  isLoginRoute(): boolean {
    return this.router.url.includes('/login');
  }
  isUserTestRoute(): boolean {
    return this.router.url.includes('/user-test');
  }

  isUploadRoute(): boolean {
    // Check if the current route is '/upload-file'
    return this.router.url === '/upload-file' || this.router.url === '/user-test'|| this.router.url==='/feedback';
  }
  isAllocateQuestion(): boolean {
    // Check if the current route is '/upload-file'
    return this.router.url === '/allocate-question';
  }
}
