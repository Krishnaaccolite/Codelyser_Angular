import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CandidatedataService {

  constructor() { }
  private selectedNodeNameSource = new BehaviorSubject<string>('');
  selectedNodeName$ = this.selectedNodeNameSource.asObservable();
  updateSelectedNodeName(name: string): void {
    this.selectedNodeNameSource.next(name);
  }
}
