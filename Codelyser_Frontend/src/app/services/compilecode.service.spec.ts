import { TestBed } from '@angular/core/testing';

import { CompilecodeService } from './compilecode.service';

describe('CompilecodeService', () => {
  let service: CompilecodeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CompilecodeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
