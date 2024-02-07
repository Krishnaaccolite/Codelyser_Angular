import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllocateQuestionComponent } from './allocate-question.component';

describe('AllocateQuestionComponent', () => {
  let component: AllocateQuestionComponent;
  let fixture: ComponentFixture<AllocateQuestionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AllocateQuestionComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AllocateQuestionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
