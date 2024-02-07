import { Component } from '@angular/core';
import { Subscription } from 'rxjs';
import { CompilecodeService } from '../services/compilecode.service';
import { DomSanitizer,SafeResourceUrl } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
@Component({
  selector: 'app-liveserver',
  templateUrl: './liveserver.component.html',
  styleUrl: './liveserver.component.scss'
})
export class LiveserverComponent {
  // public userCode!:string;
  // private fileContentSubscription!: Subscription;
  // triggerRerender = false;
  // link!:String
  // port!:string;
  // code!:String;
  // flag!:boolean;
  public userCode!:string;
  private fileContentSubscription!: Subscription;
  code!:String;
  error:string="";
  errorflag:boolean=false;
  url!:string
  port!:SafeResourceUrl;
  portflag:boolean=false;
  livegif:boolean=true;
  responsefromserver!:string;
  userId!:any;
  constructor(private compilecodeService:CompilecodeService,private sanitizer:DomSanitizer,private route: ActivatedRoute)
  {

  }
  ngOnInit() {
    this.route.paramMap.subscribe((params: { get: (arg0: string) => string | null; }) => {
      this.userId = params.get('userId');
      console.log('User ID:', this.userId);
    });
    this.compilecodeService.runCodeObservable$.subscribe((code) => {
      this.onRunCodeClicked({ livecode: code });
    });
  }
  onRunCodeClicked(event: { livecode: string }) {
    this.userCode=event.livecode;
    this.port =this.sanitizer.bypassSecurityTrustResourceUrl("");
    fetch(`http://localhost:8080/live-output/${this.userId}/index.html`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ userCode: this.userCode }),
    })
.then(response => {
  console.log(response);
  this.url=response.url;
  response.json()
.then(data=>{
  console.log(data);
  this.responsefromserver=data.response
if(this.responsefromserver==="okkh"){
  console.log(this.responsefromserver);
  console.log(this.url);
  this.port =this.sanitizer.bypassSecurityTrustResourceUrl(this.url);
  this.livegif=false;  
  this.portflag=true;
    this.errorflag=false;
    console.log(this.portflag);
    
  }
  else{
    this.error=this.responsefromserver;
    this.portflag=false;
    this.livegif=false;
    this.errorflag=true;
  }
})
})
    .catch(error => {
      console.error('Error sending data to server:', error);
    });

}
  // onRunCodeClicked(event: { livecode: string }) {
  //     this.userCode=event.livecode;
  //     fetch('http://localhost:8080/compilecode', {
  //       method: 'POST',
  //       headers: {
  //         'Content-Type': 'application/json',
  //       },
  //       body: JSON.stringify({ userCode: this.userCode }),
  //     })
  //     .then(response => response.json())
  // .then(data => {
  //   console.log('Response from server:', data);

  //   // Assuming there is a field named 'yourFieldName' in the response
  //   this.link = data.response;
  //   // Use yourValue as needed
  //   console.log('Extracted value:', this.link);
  // })
  //     .catch(error => {
  //       console.error('Error sending data to server:', error);
  //     });
  //   // this.websocketservice._send(event.livecode);
  //   // setTimeout(()=>{
  //   //   if(this.backendresponse){
  //   //     this.flag=true;}
  //   // },20000);
  // }
}
