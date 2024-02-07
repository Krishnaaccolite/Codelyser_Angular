import { Component } from '@angular/core';

@Component({
  selector: 'app-feedback',
  templateUrl: './feedback.component.html',
  styleUrl: './feedback.component.scss'
})
export class FeedbackComponent {
  stars: number[] = [1, 2, 3, 4, 5];
  selectedRating: number = 0; // Initialize to a default value
  feedbackComments: string = '';

  ngOnInit() {
    // Any additional initialization logic can go here
  }

  updateRating(rating: number): void {
    this.selectedRating = rating;
  }

  submitFeedback(): void {
    // Handle the submission logic (e.g., send feedback to the server)
    console.log('Rating:', this.selectedRating);
    console.log('Comments:', this.feedbackComments);
  }

}
