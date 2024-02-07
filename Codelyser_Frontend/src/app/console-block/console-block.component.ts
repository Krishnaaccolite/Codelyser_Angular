import { Component, OnInit } from '@angular/core';
import { TestcaseService } from '../services/testcase.service';
import { UserIDService } from '../services/user-id.service';

@Component({
  selector: 'app-console-block',
  templateUrl: './console-block.component.html',
  styleUrl: './console-block.component.scss'
})
export class ConsoleBlockComponent implements OnInit {
  isConsoleOpen: boolean = false;
  isTestCasesOpen: boolean = false;
  responseArray!: string[];
  testOutput!:string;
  testBooleanArray!:boolean[];
  totalFailed!:number;
  totalSuccess!:number;
  testCaseKeys: string[] = ["testcase1", "testcase2","testcase3","testcase4","testcase5","testcase6"];
  userId:string|null='';
  testkeyValuePairs: { [key: string]: boolean }={};
  constructor(private testCaseService:TestcaseService,private userIdService:UserIDService){}
  ngOnInit(): void {
    console.log("hooks start");
    this.testCaseService.getTriggerMethodObservable().subscribe(() => {
      this.userIdService.userId$.subscribe((userId) => {
           console.log("User ID received:", userId);
            this.userId = userId;
      });
      if (this.userId) {
          this.OntestCaseClicked(this.userId);
        } else {
          console.log("User ID is null or empty.");
        }
    }); 
  }
  OntestCaseClicked(userId:String|null):void
  {
    console.log("on testcase");
      fetch(`http://localhost:8080/runtests/${userId}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
      })
      .then(response => response.json())
  .then(data => {
    this.responseArray=data.testResponse;
    this.testOutput=this.responseArray[this.responseArray.length-1];
    console.log(this.testOutput); 
    const regex = /TOTAL: (\d+) (SUCCESS|FAILED)(?:, (\d+) (SUCCESS|FAILED))?/;

    const match = this.testOutput.match(regex);

    if (match) {
      this.totalSuccess = 0;
      this.totalFailed = 0;

      for (let i = 1; i < match.length; i += 2) {
        const count = parseInt(match[i], 10);
        const status = match[i + 1];

        if (status === 'SUCCESS') {
          this.totalSuccess += count;
        } else if (status === 'FAILED') {
          this.totalFailed += count;
        }
      }
    }

        // const regex = /TOTAL: (\d+) FAILED(?:, (\d+) SUCCESS)?/; ;
        // const match = this.testOutput.match(regex);
        // if (match) {
        //   this.totalFailed= match[1] || "0";
        //   this.totalSuccess = match[2] || "0";

        //   console.log(`Success Variable: ${this.totalSuccess}`);
        //   console.log(`Failed Variable: ${this.totalFailed}`);
        // } else {
        //   console.log("No match found");
        // }

    // const totalFailed = parseInt(this.totalFailed || '0', 10);
    // const totalSuccess = parseInt(this.totalSuccess || '0', 10);

    // Create a boolean array based on the counts
    this.testBooleanArray = Array(this.totalFailed).fill(false).concat(Array(this.totalSuccess).fill(true));
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
  toggleConsole() {
    this.isConsoleOpen = !this.isConsoleOpen;
    this.isTestCasesOpen = false;
  }
  toggleTestCases() {
    this.isTestCasesOpen = !this.isTestCasesOpen;
    this.isConsoleOpen = false;
  }
  toggleHeaderVisibility() {
    this.isConsoleOpen = false;
    this.isTestCasesOpen = false;
  }

}
