import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsoleBlockComponent } from './console-block.component';

describe('ConsoleBlockComponent', () => {
  let component: ConsoleBlockComponent;
  let fixture: ComponentFixture<ConsoleBlockComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ConsoleBlockComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ConsoleBlockComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
