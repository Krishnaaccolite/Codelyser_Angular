import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LiveserverComponent } from './liveserver.component';

describe('LiveserverComponent', () => {
  let component: LiveserverComponent;
  let fixture: ComponentFixture<LiveserverComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LiveserverComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LiveserverComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
