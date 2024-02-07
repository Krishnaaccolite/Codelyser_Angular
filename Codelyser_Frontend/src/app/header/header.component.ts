import { Component } from '@angular/core';
import { TimerService } from '../services/timer.service';
import { Subscription,interval,takeUntil } from 'rxjs';
import { MatDialog } from '@angular/material/dialog'; 
import { ConfirmationDialogComponent } from '../confirmation-dialog/confirmation-dialog.component';
import { Router } from '@angular/router'; 
import { FeedbackService } from '../services/feedback.service';
import { UserIDService } from '../services/user-id.service';
import { SubmitService } from '../services/submit.service';
@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {
  timer: number = 7200; // 2 hours in seconds
  private timerSubscription!: Subscription;
  showRouterOutlet: boolean = false;
  userId!:number;
  constructor(private timerService: TimerService,private dialog: MatDialog, private router: Router, private feedbackService: FeedbackService,private userIdService:UserIDService,private submissionService:SubmitService) {}

  ngOnInit() {
    // Fetch initial timer value from the backend
    this.userIdService.userId$.subscribe((userId) => {
      console.log("User ID received:", userId);
      if (userId) {
        const numuserId = +userId;
       this.userId = numuserId;
      }
 });
    this.timerService.getTimer().subscribe(
      (initialValue) => {
        this.timer = initialValue; // Set the timer to the value received from the backend
        this.startTimer();
      },
      (error) => {
        console.error('Failed to fetch initial timer value from the server:', error);
      }
    );
  }

  startTimer() {
    // Start the timer interval
    console.log("start timer called"+this.timer);
    this.timerSubscription = interval(1000)
      .pipe(takeUntil(interval(this.timer * 1000))) // Unsubscribe when the timer reaches 0
      .subscribe(() => {
        this.timer--;
        this.updateBackendTimer(this.timer);
      });
  }

  updateBackendTimer(timerValue: number) {
    // Call the service to send the timer value to the backend
    this.timerService.updateTimer(timerValue,this.userId).subscribe(
      (response) => {
        console.log('Timer updated successfully on the server.');
      },
      (error) => {
        console.error('Failed to update timer on the server:', error);
      }
    );
  }
  ngOnDestroy() {
    // Clean up the subscription when the component is destroyed
    if (this.timerSubscription) {
      this.timerSubscription.unsubscribe();
    }
  }
  formatTimer(): string {
    const hours = Math.floor(this.timer / 3600);
    const minutes = Math.floor((this.timer % 3600) / 60);
    const seconds = this.timer % 60;
  
    const formattedHours = this.padZero(hours);
    const formattedMinutes = this.padZero(minutes);
    const formattedSeconds = this.padZero(seconds);
  
    return `${formattedHours}:${formattedMinutes}:${formattedSeconds}`;
  }
  
  private padZero(value: number): string {
    return value < 10 ? `0${value}` : `${value}`;
  }
  openConfirmationDialog(): void {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      width: '300px',
      data: {
        title: 'Submit Confirmation',
        message: 'Are you sure you want to submit your answers?',
      },
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        if (result == true) {
          console.log(result);
          this.submissionService.submitProject(this.userId).subscribe(
            (response) => {
              console.log('Submission successful:', response);
              this.feedbackService.toggleRouterOutletVisibility(true);
            },
            (error) => {
              this.router.navigate(['/feedback'], { replaceUrl: true });
              console.error('Failed to submit project:', error);
            }
          );
        }
      } else {
        this.feedbackService.toggleRouterOutletVisibility(true);
        this.router.navigate(['/feedback'], { replaceUrl: true });
      }
    });    
  }

  //   dialogRef.afterClosed().subscribe((result) => {
  //     if (result) {
  //       if (result==true) {
  //         console.log(result);
  //         this.submissionService.submitProject(this.userId).subscribe(
  //           (response) => {
  //             console.log('Submission successful:', response);
  //             this.feedbackService.toggleRouterOutletVisibility(true);
  //             this.router.navigate(['/feedback'], { replaceUrl: true });
  //           },
  //           (error) => {
  //             console.error('Failed to submit project:', error);
  //           }
  //         );
  //          }
  //        });
  //       } 
  //       }
  //       this.feedbackService.toggleRouterOutletVisibility(true);
  //       this.router.navigate(['/feedback'], { replaceUrl: true });
  //     }
      
  //   });
  // }

}
