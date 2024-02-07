import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CandidateFileExplorerComponent } from './candidate-file-explorer.component';

describe('CandidateFileExplorerComponent', () => {
  let component: CandidateFileExplorerComponent;
  let fixture: ComponentFixture<CandidateFileExplorerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CandidateFileExplorerComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CandidateFileExplorerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
