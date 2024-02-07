import { Component } from '@angular/core';
import { Subscription } from 'rxjs';
import { CompilecodeService } from '../services/compilecode.service';
import { DomSanitizer,SafeResourceUrl } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { UserIDService } from '../services/user-id.service';
@Component({
  selector: 'app-liveserver',
  templateUrl: './liveserver.component.html',
  styleUrl: './liveserver.component.scss'
})
export class LiveserverComponent {

  public userCode!:string;
  public filename!:string;
  private fileContentSubscription!: Subscription;
  code!:String;
  error:string="";
  errorflag:boolean=false;
  url!:string
  port!:SafeResourceUrl;
  portflag:boolean=false;
  livegif:boolean=false;
  livephoto:boolean=true;
  responsefromserver!:string;
  userId!:any;
  constructor(private useridservice: UserIDService , private compilecodeService:CompilecodeService,private sanitizer:DomSanitizer,private route: ActivatedRoute)
  {
  }
  ngOnInit() {
    this.useridservice.userId$.subscribe((id)=>{
      this.userId=id;
    })
    console.log("UserId from LiveServer" + this.userId);
    this.compilecodeService.runCodeObservable$.subscribe((myMap) => {
      this.livegif=true;
      this.errorflag=false;
      this.portflag=false;
      this.livephoto=false;
    //  Map.forEach((value: FormDataEntryValue, key: string) => {
       this.onRunCodeClicked({myMap});
    // });
    });
  }
  onRunCodeClicked(event: {myMap:FormData}) {
    // this.userCode=event.livecode;
    // this.filename=event.filename;
    // filename:key,livecode:value?.toString() || ''
    let requestBody: { [key: string]: string } = {};
   event.myMap.forEach((value: FormDataEntryValue, key: string) => {
      requestBody[key] = value.toString();
    });
    
    this.port =this.sanitizer.bypassSecurityTrustResourceUrl("");
    fetch(`http://localhost:8080/live-output/${this.userId}/index.html`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(requestBody),
    })
    .then(response => {
      console.log(response);
      this.url=response.url;
      return response.json();
    })
    .then(data => {
      console.log(data.response);
      if (data.response === "okkh") {
  this.port =this.sanitizer.bypassSecurityTrustResourceUrl(this.url);
  this.livegif=false;
  this.portflag=true;
  this.errorflag=false;
  console.log(this.portflag);
  }
  else{
    this.error=data.response;
    this.portflag=false;
    this.livegif=false;
    this.errorflag=true;
  }
})
    .catch(error => {
      console.error('Error sending data to server:', error);
    });
}

}