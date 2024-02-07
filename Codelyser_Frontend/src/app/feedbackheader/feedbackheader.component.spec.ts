import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FeedbackheaderComponent } from './feedbackheader.component';

describe('FeedbackheaderComponent', () => {
  let component: FeedbackheaderComponent;
  let fixture: ComponentFixture<FeedbackheaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [FeedbackheaderComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FeedbackheaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
