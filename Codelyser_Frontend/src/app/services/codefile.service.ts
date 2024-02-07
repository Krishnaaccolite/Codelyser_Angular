import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class CodefileService {
  private fileContentSource = new BehaviorSubject<string>('');
  currentFileContent = this.fileContentSource.asObservable();

  private languageSource = new BehaviorSubject<string>('plaintext');
  currentLanguage = this.languageSource.asObservable();
  private isRunButtonClickedSource = new BehaviorSubject<boolean>(false);
  isRunButtonClicked = this.isRunButtonClickedSource.asObservable();

  setFileContent(content:any) {
    if (this.fileContentSource.value !== content) {
      this.fileContentSource.next(content);
    }
  }

  setLanguage(language: string) {
    this.languageSource.next(language);
  }

  setRunButtonClicked(value: boolean) {
    this.isRunButtonClickedSource.next(value);
  }

  getFileContent(): string {
    // Implement your logic to fetch the file content here.
    // For demonstration, I'm returning a sample content.
    return this.fileContentSource.value;
  }

  // setFileContent(content: string) {
  //   if (this.fileContentSource.value !== content) {
  //     this.fileContentSource.next(content);
  //   }
  // }

  // setLanguage(language: string) {
  //   this.languageSource.next(language);
  // }
  // getFileContent(): string {
  //   // Implement your logic to fetch the file content here.
  //   // For demonstration, I'm returning a sample content.
  //   return this.fileContentSource.value;
  // }
}
