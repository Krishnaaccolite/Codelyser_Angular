import { Component,ViewChild,ElementRef } from '@angular/core';
import { UploadService } from '../services/upload.service';
import { HttpEventType } from '@angular/common/http';
import {MatSnackBar}  from '@angular/material/snack-bar'

@Component({
  selector: 'app-upload-file',
  templateUrl: './upload-file.component.html',
  styleUrl: './upload-file.component.scss'
})
export class UploadFileComponent {
  selectedFileName: string | null = null;
  selectedFile: File | null = null;
  progress = 0;
   @ViewChild('questionname', { static: false }) questionname!: ElementRef;

  constructor(private uploadService: UploadService,private snackBar:MatSnackBar) {}

  onFileSelected(event: any): void {
    this.selectedFile = event.target.files[0];
    this.selectedFileName = this.selectedFile?.name || null;
  }

  uploadFile(): void {
    if (this.selectedFile) {
     const questionname= this.questionname.nativeElement.value;
      this.uploadService.uploadFile(this.selectedFile,questionname)
        .subscribe(
          (response:any)=>{
            const parsedResponse=response.Response;
            console.log(parsedResponse.typeof);
                if(parsedResponse.typeof===undefined){
                  console.log("True");
                  this.showSnackBar(parsedResponse);
                }
          },
          error => {
            console.error('Error uploading file:', error);
            this.showSnackBar('File Uploaded Successfully');
          }
        );
    }
  }
  showSnackBar(message: any): void {
    this.snackBar.open(message, 'Close', {
      duration: 4000, // Snackbar will be displayed for 3 seconds
      horizontalPosition: 'center',
      verticalPosition: 'top',
    });
  }

}
