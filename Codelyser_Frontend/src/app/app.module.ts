import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatTreeModule } from '@angular/material/tree';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { MonacoEditorComponent } from './monaco-editor/monaco-editor.component';
import { LiveserverComponent } from './liveserver/liveserver.component';
import { CandidateComponent } from './candidate/candidate.component';
import { CandidateFileExplorerComponent } from './candidate-file-explorer/candidate-file-explorer.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ConsoleComponent } from './console/console.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { Library, library } from '@fortawesome/fontawesome-svg-core';
import { faCheck, faTimes } from '@fortawesome/free-solid-svg-icons';
import { HeaderComponent } from './header/header.component';
import { UploadFileComponent } from './upload-file/upload-file.component';
import { LoginComponent } from './login/login.component';
import { FormsModule } from '@angular/forms';
import { ConsoleBlockComponent } from './console-block/console-block.component';
import { FeedbackComponent } from './feedback/feedback.component';
import { FeedbackheaderComponent } from './feedbackheader/feedbackheader.component';
import { ConfirmationDialogComponent } from './confirmation-dialog/confirmation-dialog.component';
import { MatDialogModule } from '@angular/material/dialog';
import { TestcaseService } from './services/testcase.service';
import { AllocateQuestionComponent } from './allocate-question/allocate-question.component';
library.add(faCheck,faTimes)
@NgModule({
  declarations: [
    AppComponent,
    MonacoEditorComponent,
    LiveserverComponent,
    CandidateComponent,
    CandidateFileExplorerComponent,
    HeaderComponent,
    UploadFileComponent,
    LoginComponent,
    ConsoleBlockComponent,
    FeedbackComponent,
    FeedbackheaderComponent,
    ConfirmationDialogComponent,
    ConsoleComponent,
    AllocateQuestionComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatListModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    MatToolbarModule,
    MatTreeModule ,
    HttpClientModule,
    FontAwesomeModule ,
    AppRoutingModule,
    FormsModule,
    MatDialogModule
  ],
  providers: [TestcaseService],
  bootstrap: [AppComponent]
})
export class AppModule { }
