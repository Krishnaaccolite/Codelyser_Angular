import { Component, OnDestroy, OnInit } from '@angular/core';
import { CodefileService } from '../services/codefile.service';
import { TestcaseService } from '../services/testcase.service';
import { UserIDService } from '../services/user-id.service';

@Component({
  selector: 'app-console',
  templateUrl: './console.component.html',
  styleUrl: './console.component.scss'
})
export class ConsoleComponent implements OnInit {
  testCode!:string;
  responseArray!: string[];
  testOutput!:string;
  testBooleanArray!:boolean[];
  testCaseKeys: string[] = ["testcase1", "testcase2","testcase3","testcase4","testcase5","testcase6"];
  userId:string|null='';
  testkeyValuePairs: { [key: string]: boolean }={};
  constructor(private codefileService:CodefileService,private testCaseService:TestcaseService,private userIdService:UserIDService)
  {
    console.log("Constructor called");
  }
  ngOnInit(): void {
    console.log("hooks start");
    this.testCaseService.getTriggerMethodObservable().subscribe(() => {
      console.log("enterd")
      // this.OntestCaseClicked(); // Call another method when the observable emits a value
    });
    // this.testCaseService.getTriggerMethodObservable().subscribe(()=>
  
    //   this.userIdService.userId$.subscribe((userId) => {
    //     console.log("User ID received:", userId);
    //     this.userId = userId;
  
    //     // Ensure that userId is not null or empty before calling OntestCaseClicked
    //     if (userId) {
    //       this.OntestCaseClicked(this.userId);
    //     } else {
    //       console.log("User ID is null or empty.");
    //     }
    //   });
    // });
  }
  OntestCaseClicked(userId:String|null):void
  {
    console.log("on testcase");
      fetch(`http://localhost:8080/runtests/${userId}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ testCode: this.testCode }),
      })
      .then(response => response.json())
  .then(data => {
    this.responseArray=data.testResponse;
    // console.log(data.testResponse);
    // console.log('Response from server:', this.responseArray);
    this.testOutput=this.responseArray[this.responseArray.length-1];
    console.log(this.testOutput);
    const matchResult = this.testOutput.match(/TOTAL: (\d+) FAILED, (\d+) SUCCESS/);
    const [, failedCount, successCount] = matchResult || [];

    console.log('failedCount:', failedCount);
    console.log('successCount:', successCount);

    const totalFailed = parseInt(failedCount || '0', 10);
    const totalSuccess = parseInt(successCount || '0', 10);

    // Create a boolean array based on the counts
    this.testBooleanArray = Array(totalFailed).fill(false).concat(Array(totalSuccess).fill(true));
    console.log(this.testBooleanArray);
    this.testCaseKeys.forEach((key, index) => {
      this.testkeyValuePairs[key] = this.testBooleanArray[index];
      console.log(this.testkeyValuePairs);
    });
  })
  .catch(error => {
    console.error('Error sending data to server:', error);
  });
  }
  fileContent:string="";
  testcases: string[] = ['Test Case 1', 'Test Case 2', 'Test Case 3','Test Case 4', 'Test Case 5', 'Test Case 6'];
  // 'Test Case 1' = "pass";
  
}
