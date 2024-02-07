import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TrackchangefileService {
  trackmap: FormData = new FormData();
  constructor() { }
}
