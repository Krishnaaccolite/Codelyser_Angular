import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { TrackchangefileService } from '../trackchangefile.service';
@Injectable({
  providedIn: 'root'
})
export class CompilecodeService {
constructor(private trackchangefile:TrackchangefileService){
}

  private runCodeSubject = new Subject<FormData>();
  runCodeObservable$ = this.runCodeSubject.asObservable();
  triggerRunCode(code: string,filename:string) {
    this.trackchangefile.trackmap.append(filename,code);
    // const codeandfilename: FormData = new FormData();
    // codeandfilename.append(filename,code);
    this.runCodeSubject.next(this.trackchangefile.trackmap);
  }
}