import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class TestcaseService {
  private triggerMethodSubject = new Subject<void>();

  triggerMethod(): void {
    console.log("triggered");
    this.triggerMethodSubject.next();
  }

  getTriggerMethodObservable(): Observable<void> {
    console.log("happyyy");
    return this.triggerMethodSubject.asObservable();
  }
}
