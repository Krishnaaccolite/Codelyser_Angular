import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class FeedbackService {
  showRouterOutlet = false;

  constructor() { }

  toggleRouterOutletVisibility(value: boolean): void {
    this.showRouterOutlet = value;
  }
}
