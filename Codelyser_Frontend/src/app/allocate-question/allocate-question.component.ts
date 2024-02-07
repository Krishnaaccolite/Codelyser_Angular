import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AllocateQuestionService } from '../services/allocate-question.service';
import { MatSnackBar } from '@angular/material/snack-bar';
@Component({
  selector: 'app-allocate-question',
  templateUrl: './allocate-question.component.html',
  styleUrl: './allocate-question.component.scss'
})
export class AllocateQuestionComponent {
  userId: number | null = null;
  questionId: number | null = null;
  constructor(
    private route: ActivatedRoute,
    private allocateQuestionService: AllocateQuestionService,
    private snackBar:MatSnackBar
  ){
    this.route.params.subscribe(params => {
      this.userId = params['userId'];
      this.questionId = params['qId'];
    });
  }
  assignQuestionToUser(): void {
    if (this.userId && this.questionId) {
      this.allocateQuestionService.assignQuestionToUser(this.userId, this.questionId).subscribe(
        response => {
          console.log(response);
          this.showSnackBar('Question Assigned Successfully');
        },
        error => {
          console.error(error);
          this.showSnackBar('Question Assigned Successfully');
        }
      );
    }
  }
  showSnackBar(message: any): void {
    this.snackBar.open(message, 'Close', {
      duration: 4000, // Snackbar will be displayed for 3 seconds
      horizontalPosition: 'center',
      verticalPosition: 'top',
    });
  }
}