import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UploadFileComponent } from './upload-file/upload-file.component';
import { CandidateComponent } from './candidate/candidate.component';
import { FeedbackComponent } from './feedback/feedback.component';
import { LoginComponent } from './login/login.component';
import { AllocateQuestionComponent } from './allocate-question/allocate-question.component';
const routes: Routes = [{ path: 'upload-file', component: UploadFileComponent },
                        { path: 'allocate-question', component: AllocateQuestionComponent },
                        { path: 'feedback', component: FeedbackComponent },
                        { path: 'user-test/:userId', component: CandidateComponent },
                        { path: 'login', component: LoginComponent },
  { path: '', redirectTo: '/login', pathMatch: 'full' }, 
]
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
